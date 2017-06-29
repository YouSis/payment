package com.wfj.pay.service.impl;

import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.cache.PayCacheHandle;
import com.wfj.pay.constant.PayLogConstant;
import com.wfj.pay.constant.PayOrderStatus;
import com.wfj.pay.constant.PayTypeEnum;
import com.wfj.pay.dto.OrderResponseDTO;
import com.wfj.pay.dto.PayNotifyInfoDTO;
import com.wfj.pay.dto.PayTradeDTO;
import com.wfj.pay.po.PayPartnerAccountPO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.po.PayWeChatNotifyInfoPO;
import com.wfj.pay.sdk.wxpay.MyWxPayConfig;
import com.wfj.pay.sdk.wxpay.WXPay;
import com.wfj.pay.sdk.wxpay.WXPayConstants;
import com.wfj.pay.sdk.wxpay.WxPayTradeStateConstants;
import com.wfj.pay.service.IPayLogService;
import com.wfj.pay.service.IPayStrategyService;
import com.wfj.pay.service.IPayTradeService;
import com.wfj.pay.service.IPayWeChatPayNotifyService;
import com.wfj.pay.utils.CalendarUtil;
import com.wfj.pay.utils.DistributedLock;
import com.wfj.pay.utils.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Created by wjg on 2017/6/26.
 */
@Service
public class WeChatPayOfflineStrategyImpl implements IPayStrategyService {
    private Logger logger = LoggerFactory.getLogger(WeChatPayOfflineStrategyImpl.class);
    private static final String SPBILL_CREATE_IP = "8.8.8.8";
    @Autowired
    private IPayWeChatPayNotifyService weChatPayNotifyService;
    @Autowired
    private IPayTradeService payTradeService;
    @Autowired
    private IPayLogService payLogService;
    @Value("${distributed.lock.zk.address}")
    private String distributedLockZKUrl;
    private final ExecutorService executorService = Executors.newFixedThreadPool(30, r->{
        Thread t = new Thread(r);
        t.setName("wechatpay-query-thread-"+t.getId());
        return t;
    });

    @Override
    public boolean match(PayTypeEnum payTypeEnum) {
        return payTypeEnum.equals(PayTypeEnum.WECHATPAY_OFFLINE);
    }

    @Override
    public OrderResponseDTO toPay(PayTradeDTO payTradeDTO) {
        OrderResponseDTO responseDTO;
        MyWxPayConfig wxPayConfig = getMyWxPayConfig(payTradeDTO.getPayPartner());
        //1、组织报文
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("body", payTradeDTO.getGoodsName());
        data.put("out_trade_no", payTradeDTO.getOrderTradeNo());
        data.put("total_fee", new BigDecimal(String.valueOf(payTradeDTO.getTotalFee())).multiply(
                new BigDecimal("100")).toString());
        data.put("spbill_create_ip", SPBILL_CREATE_IP);
        data.put("auth_code", payTradeDTO.getAuthCode());
        //2、调用微信sdk发起支付
        long beginTime = System.currentTimeMillis();
        Map<String, String> resultMap;
        try {
            WXPay wxPay = new WXPay(wxPayConfig);
            resultMap = wxPay.unifiedOrder(data);
        } catch (Exception e) {
            //调用微信失败
            //此处应该抛出异常给监控平台
            logger.error("调用微信支付失败:" + e.toString(), e);
            responseDTO = new OrderResponseDTO("1", "false", "请求微信支付失败，请重试");
            return responseDTO;
        }
        long endTime = System.currentTimeMillis();
        logger.info("--->订单号为{}的订单请求微信下单的时间为{}毫秒", payTradeDTO.getOrderTradeNo(), endTime - beginTime);
        //3、处理微信返回的结果
        responseDTO = processResponse(resultMap);
        return responseDTO;
    }

    @Override
    public OrderResponseDTO queryOrder(PayTradePO payTradePO) {
        OrderResponseDTO responseDTO;
        MyWxPayConfig wxPayConfig = getMyWxPayConfig(payTradePO.getPayPartner());
        //1、组织查询报文
        Map<String, String> data = new HashMap<>();
        data.put("out_trade_no", payTradePO.getOrderTradeNo());
        //2、调用sdk发起查询
        long beginTime = System.currentTimeMillis();
        Map<String, String> resultMap;
        try {
            WXPay wxPay = new WXPay(wxPayConfig);
            resultMap = wxPay.orderQuery(data);
        } catch (Exception e) {
            //调用微信失败
            //此处应该抛出异常给监控平台
            logger.error("调用微信查询订单失败:" + e.toString(), e);
            responseDTO = new OrderResponseDTO("1", "false", "请求微信查询失败，请重试");
            return responseDTO;
        }
        long endTime = System.currentTimeMillis();
        logger.info("--->订单号为{}的订单单笔查询微信的时间为{}毫秒", payTradePO.getOrderTradeNo(), endTime - beginTime);
        //3、处理微信返回的结果
        responseDTO = processQueryResponse(resultMap);
        return responseDTO;
    }

    private OrderResponseDTO processQueryResponse(Map<String, String> resultMap) {
        OrderResponseDTO orderResponseDTO;
        String returnCode = resultMap.get("return_code");//报文格式是否正确
        String resultCode = resultMap.get("result_code");//业务状态，是否支付成功
        //1、先判断请求是否成功
        if (WXPayConstants.FAIL.equals(returnCode)) {
            orderResponseDTO = new OrderResponseDTO("1", "false", "请求微信查询失败，参数错误，请重试");
            return orderResponseDTO;
        }
        //2、判断业务状态
        if (WXPayConstants.SUCCESS.equals(resultCode)) {
            String tradeState = resultMap.get("trade_state");
            switch (tradeState) {
                case WxPayTradeStateConstants.SUCCESS:
                    //支付成功
                    DistributedLock zkLock = new DistributedLock(distributedLockZKUrl, resultMap.get("out_trade_no"));
                    zkLock.lock();
                    try {
                        PayTradePO payTradePO = doAfterWeChatSuccess(resultMap);
                        orderResponseDTO = payTradeService.transfer(payTradePO);
                    } finally {
                        zkLock.unlock();
                    }
                    break;
                case WxPayTradeStateConstants.REFUND:
                    orderResponseDTO = new OrderResponseDTO("1", "false", "订单已退款");
                    break;
                default:
                    orderResponseDTO = new OrderResponseDTO("1", "false", "订单未支付");
            }

        } else {
            //支付失败
            String errCode = resultMap.get("err_code");//支付失败的代码
            String errCodeDes = resultMap.get("err_code_des");//支付失败的描述
            orderResponseDTO = new OrderResponseDTO("1", "false", "请求微信查询失败，" + errCodeDes);
        }
        return orderResponseDTO;
    }

    /**
     * 处理微信支付返回的结果
     *
     * @param resultMap
     * @return
     */
    private OrderResponseDTO processResponse(Map<String, String> resultMap) {
        OrderResponseDTO orderResponseDTO;
        String returnCode = resultMap.get("return_code");//报文格式是否正确
        String resultCode = resultMap.get("result_code");//业务状态，是否支付成功
        //1、先判断请求是否成功
        if (WXPayConstants.FAIL.equals(returnCode)) {
            orderResponseDTO = new OrderResponseDTO("1", "false", "请求微信支付失败，参数错误，请重试");
            return orderResponseDTO;
        }
        //2、判断业务状态
        if (WXPayConstants.SUCCESS.equals(resultCode)) {
            //支付成功
            DistributedLock zkLock = new DistributedLock(distributedLockZKUrl, resultMap.get("out_trade_no"));
            zkLock.lock();
            try {
                PayTradePO payTradePO = doAfterWeChatSuccess(resultMap);
                orderResponseDTO = payTradeService.transfer(payTradePO);
            } finally {
                zkLock.unlock();
            }
        } else {
            //支付失败
            String errCode = resultMap.get("err_code");//支付失败的代码
            String errCodeDes = resultMap.get("err_code_des");//支付失败的描述
            //用户正在输入密码
            if (WXPayConstants.ERROR_CODE_USERPAYING.equals(errCode)) {
                orderResponseDTO = new OrderResponseDTO("0", "false", errCodeDes);
                //开启线程进行轮训微信
                executorService.submit(() -> {
                    IntStream.range(0,6).forEach(value -> {
                        // 睡眠一定时间后再查询 总共查6次，中间间隔5s
                        try {
                            Thread.sleep(Long.valueOf(5000));
                        } catch (InterruptedException e) {
                           logger.error(e.toString(),e);
                        }
                        queryOrder(payTradeService.findByOrderTradeNo(resultMap.get("out_trade_no")));
                    });
                });
            } else {
                orderResponseDTO = new OrderResponseDTO("1", "false", "请求微信支付失败，" + errCodeDes);
            }
        }
        return orderResponseDTO;
    }

    /**
     * 微信支付成功之后的处理逻辑
     *
     * @param resultMap
     */
    @Transactional
    @DataSource("master")
    private PayTradePO doAfterWeChatSuccess(Map<String, String> resultMap) {
        //1、保存微信交易的通知日志
        PayWeChatNotifyInfoPO weChatNotifyInfoPO = (PayWeChatNotifyInfoPO) ObjectUtil.mapToBean(PayWeChatNotifyInfoPO.class, resultMap);
        weChatPayNotifyService.saveWeChatPayNotify(weChatNotifyInfoPO);
        //2、更改订单状态
        PayNotifyInfoDTO payNotifyInfoDTO = new PayNotifyInfoDTO();
        payNotifyInfoDTO.setOrderTradeNo(weChatNotifyInfoPO.getOut_trade_no());
        payNotifyInfoDTO.setPaySerialNumber(weChatNotifyInfoPO.getTransaction_id());
        payNotifyInfoDTO.setStatus(PayOrderStatus.PAYED);
        try {
            Timestamp platformPayDate = CalendarUtil.getTimestampFromString(weChatNotifyInfoPO.getTime_end());
            payNotifyInfoDTO.setPlatformPayDate(CalendarUtil.getDateLong(platformPayDate));
            payNotifyInfoDTO.setPlatformPayFormat(platformPayDate);
        } catch (ParseException e) {
            logger.error(e.toString(), e);
        }
        if (weChatNotifyInfoPO.getCoupon_fee() != null) {
            payNotifyInfoDTO.setCouponFee(new BigDecimal(weChatNotifyInfoPO.getCoupon_fee())
                    .divide(new BigDecimal("100")).doubleValue());
        } else {
            payNotifyInfoDTO.setCouponFee(0d);
        }
        payTradeService.updateOrderAfterPaySuccess(payNotifyInfoDTO);
        //3、保存支付成功的日志
        PayTradePO tradePO = payTradeService.findByOrderTradeNo(payNotifyInfoDTO.getOrderTradeNo());
        Map<String, Object> logMap = new HashMap<String, Object>();
        logMap.put("paySerialNumber", payNotifyInfoDTO.getPaySerialNumber());
        logMap.put("payBank", tradePO.getPayService());
        logMap.put("orderTradeNo", tradePO.getOrderTradeNo());
        logMap.put("totalFee", tradePO.getTotalFee());
        logMap.put("status", PayOrderStatus.PAYED_NAME);
        payLogService.saveLog(logMap, PayLogConstant.PAY_STEP_NOTIFY, "1");
        return tradePO;
    }

    /**
     * 根据不同的商户号码获取不同的微信配置类
     *
     * @param payPartnerAccoutId
     * @return
     */
    private MyWxPayConfig getMyWxPayConfig(Long payPartnerAccoutId) {
        PayPartnerAccountPO payPartnerAccout = PayCacheHandle.getPayPartnerAccout(payPartnerAccoutId);
        return new MyWxPayConfig(payPartnerAccout.getAppid(), payPartnerAccout.getPartner(), payPartnerAccout.getEncryptKey(), payPartnerAccout.getKeyPath());
    }
}
