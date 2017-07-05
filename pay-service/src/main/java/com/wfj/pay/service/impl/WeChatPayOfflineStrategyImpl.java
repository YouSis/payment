package com.wfj.pay.service.impl;

import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.cache.PayCacheHandle;
import com.wfj.pay.constant.PayLogConstant;
import com.wfj.pay.constant.PayRefundTradeStatus;
import com.wfj.pay.constant.PayTradeStatus;
import com.wfj.pay.constant.PayTypeEnum;
import com.wfj.pay.dto.*;
import com.wfj.pay.po.*;
import com.wfj.pay.sdk.wxpay.MyWxPayConfig;
import com.wfj.pay.sdk.wxpay.WXPay;
import com.wfj.pay.sdk.wxpay.WXPayConstants;
import com.wfj.pay.sdk.wxpay.WxPayTradeStateConstants;
import com.wfj.pay.service.*;
import com.wfj.pay.utils.CalendarUtil;
import com.wfj.pay.utils.DistributedLock;
import com.wfj.pay.utils.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    private IPayRefundWeChatOfNotifyService refundWeChatOfNotifyService;
    @Autowired
    private IPayTradeService payTradeService;
    @Autowired
    private IPayRefundTradeService refundTradeService;
    @Autowired
    private IPayLogService payLogService;
    @Value("${distributed.lock.zk.address}")
    private String distributedLockZKUrl;
    private final ExecutorService executorService = Executors.newFixedThreadPool(30, r -> {
        Thread t = new Thread(r);
        t.setName("wechatpay-query-thread-" + t.getId());
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
        data.put("total_fee", String.valueOf(new BigDecimal(String.valueOf(payTradeDTO.getTotalFee())).multiply(
                new BigDecimal("100")).intValue()));
        data.put("spbill_create_ip", SPBILL_CREATE_IP);
        data.put("auth_code", payTradeDTO.getAuthCode());
        //2、调用微信sdk发起支付
        long beginTime = System.currentTimeMillis();
        Map<String, String> resultMap;
        try {
            WXPay wxPay = new WXPay(wxPayConfig);
            resultMap = wxPay.microPay(data);
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
        responseDTO = processResponse(resultMap,payTradeDTO.getOrderTradeNo());
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

    @Override
    public OrderResponseDTO closeOrder(PayTradePO tradePO) {
        //1、先进行单笔查询，如果是支付成功状态，直接返回错误
        OrderResponseDTO responseDTO = queryOrder(tradePO);
        if ("0".equals(responseDTO.getResultCode()) || "true".equals(responseDTO.getResultMsg())) {
            responseDTO = new OrderResponseDTO("1", "false", "订单已支付，不能关闭");
            return responseDTO;
        }
        //2、如果是未支付，调用微信关闭接口
        long beginTime = System.currentTimeMillis();
        Map<String, String> data = new HashMap<>();
        data.put("out_trade_no", tradePO.getOrderTradeNo());
        MyWxPayConfig myWxPayConfig = getMyWxPayConfig(tradePO.getPayPartner());
        Map<String, String> resultMap;
        try {
            WXPay wxPay = new WXPay(myWxPayConfig);
            resultMap = wxPay.reverse(data);
        } catch (Exception e) {
            //调用微信失败
            //此处应该抛出异常给监控平台
            logger.error("调用微信关闭订单失败:" + e.toString(), e);
            responseDTO = new OrderResponseDTO("1", "false", "请求微信撤销失败，请重试");
            return responseDTO;
        }
        long endTime = System.currentTimeMillis();
        logger.info("--->订单号为{}的订单调用微信撤销接口的时间为{}毫秒", tradePO.getOrderTradeNo(), endTime - beginTime);
        //3、处理微信返回结果
        responseDTO = processCloseResponse(resultMap, tradePO.getOrderTradeNo());
        return responseDTO;
    }

    @Override
    public RefundOrderResponseDTO toRefund(PayRefundTradePO refundTradePO,PayTradePO tradePO) {
        RefundOrderResponseDTO responseDTO;
        MyWxPayConfig myWxPayConfig = getMyWxPayConfig(tradePO.getPayPartner());
        long beginTime = System.currentTimeMillis();
        //1、调用微信退款接口
        Map<String, String> data = new HashMap<>();
        data.put("out_trade_no", refundTradePO.getOrderTradeNo());
        data.put("out_refund_no",refundTradePO.getRefundTradeNo());
        data.put("total_fee",String.valueOf(new BigDecimal(String.valueOf(tradePO.getTotalFee())).multiply(new BigDecimal("100")).intValue()));
        data.put("refund_fee",String.valueOf(new BigDecimal(String.valueOf(refundTradePO.getRefundFee())).multiply(new BigDecimal("100")).intValue()));
        Map<String, String> resultMap;
        try {
            WXPay wxPay = new WXPay(myWxPayConfig);
            resultMap = wxPay.refund(data);
        } catch (Exception e) {
            //调用微信失败
            //此处应该抛出异常给监控平台
            logger.error("调用微信退款接口失败:" + e.toString(), e);
            responseDTO = new RefundOrderResponseDTO("1", "false", "请求微信退款失败，请重试");
            return responseDTO;
        }
        long endTime = System.currentTimeMillis();
        logger.info("--->订单号为{}的订单调用微信退款接口的时间为{}毫秒", tradePO.getOrderTradeNo(), endTime - beginTime);
        responseDTO = processRefundResponse(resultMap);
        return responseDTO;
    }

    @Override
    public RefundOrderResponseDTO queryRefundOrder(PayRefundTradePO refundTradePO, PayTradePO tradePO) {
        RefundOrderResponseDTO responseDTO;
        MyWxPayConfig myWxPayConfig = getMyWxPayConfig(tradePO.getPayPartner());
        long beginTime = System.currentTimeMillis();
        //1、调用微信退款查询接口
        Map<String, String> data = new HashMap<>();
        data.put("out_refund_no", refundTradePO.getOrderTradeNo());
        Map<String, String> resultMap;
        try {
            WXPay wxPay = new WXPay(myWxPayConfig);
            resultMap = wxPay.refundQuery(data);
        } catch (Exception e) {
            //调用微信失败
            //此处应该抛出异常给监控平台
            logger.error("调用微信退款查询接口失败:" + e.toString(), e);
            responseDTO = new RefundOrderResponseDTO("1", "false", "请求微信退款查询失败，请重试");
            return responseDTO;
        }
        long endTime = System.currentTimeMillis();
        logger.info("--->订单号为{}的订单调用微信退款查询接口的时间为{}毫秒", tradePO.getOrderTradeNo(), endTime - beginTime);
        responseDTO = processRefundQueryResponse(resultMap);
        return responseDTO;
    }

    private RefundOrderResponseDTO processRefundQueryResponse(Map<String, String> resultMap) {
        RefundOrderResponseDTO responseDTO;
        String returnCode = resultMap.get("return_code");//报文格式是否正确
        String resultCode = resultMap.get("result_code");//业务状态，是否退款查询成功
        //1、先判断请求是否成功
        if (WXPayConstants.FAIL.equals(returnCode)) {
            responseDTO = new RefundOrderResponseDTO("1", "false", "请求微信退款查询失败，参数错误，请重试");
            return responseDTO;
        }
        //2、判断业务状态
        if (WXPayConstants.SUCCESS.equals(resultCode)) {
            //退款成功之后更新退款单的状态
            responseDTO = doAfterRefundSuccess(resultMap);
        } else {
            //退款查询失败
            String errCodeDes = resultMap.get("err_code_des");//关闭失败的描述
            responseDTO = new RefundOrderResponseDTO("1", "false", "退款失败，" + errCodeDes);
        }
        return  responseDTO;
    }

    private RefundOrderResponseDTO processRefundResponse(Map<String, String> resultMap) {
        RefundOrderResponseDTO responseDTO;
        String returnCode = resultMap.get("return_code");//报文格式是否正确
        String resultCode = resultMap.get("result_code");//业务状态，是否退款成功
        //1、先判断请求是否成功
        if (WXPayConstants.FAIL.equals(returnCode)) {
            responseDTO = new RefundOrderResponseDTO("1", "false", "请求微信退款失败，参数错误，请重试");
            return responseDTO;
        }
        //2、判断业务状态
        if (WXPayConstants.SUCCESS.equals(resultCode)) {
            //退款成功之后更新退款单的状态
            responseDTO = doAfterRefundSuccess(resultMap);
        } else {
            //支付失败
            String errCodeDes = resultMap.get("err_code_des");//关闭失败的描述
            responseDTO = new RefundOrderResponseDTO("1", "false", "请求微信退款失败，" + errCodeDes);
        }
        return  responseDTO;
    }

    private OrderResponseDTO processCloseResponse(Map<String, String> resultMap, String orderTradeNo) {
        OrderResponseDTO orderResponseDTO;
        String returnCode = resultMap.get("return_code");//报文格式是否正确
        String resultCode = resultMap.get("result_code");//业务状态，是否支付成功
        //1、先判断请求是否成功
        if (WXPayConstants.FAIL.equals(returnCode)) {
            orderResponseDTO = new OrderResponseDTO("1", "false", "请求微信撤销订单失败，参数错误，请重试");
            return orderResponseDTO;
        }
        //2、判断业务状态
        if (WXPayConstants.SUCCESS.equals(resultCode)) {
            orderResponseDTO = new OrderResponseDTO("0", "true", "关闭成功");
            //更新本地的订单状态，记录日志
            payTradeService.doAfterCloseSuccess(orderTradeNo, PayLogConstant.OPERATE_SOURCE_PERSON);
        } else {
            //支付失败
            String errCodeDes = resultMap.get("err_code_des");//关闭失败的描述
            orderResponseDTO = new OrderResponseDTO("1", "false", "请求微信查询失败，" + errCodeDes);
        }
        return orderResponseDTO;
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
                        orderResponseDTO = doAfterWeChatSuccess(resultMap);
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
    private OrderResponseDTO processResponse(Map<String, String> resultMap,String orderTradeNo) {
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
            DistributedLock zkLock = new DistributedLock(distributedLockZKUrl, orderTradeNo);
            zkLock.lock();
            try {
                orderResponseDTO =  doAfterWeChatSuccess(resultMap);
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
                Future<?> future = executorService.submit(() -> {
                    for (int i = 0; i < 6; i++) {
                        // 睡眠一定时间后再查询 总共查6次，中间间隔5s
                        try {
                            Thread.sleep(7500);
                        } catch (InterruptedException e) {
                            logger.error(e.toString(), e);
                        }
                        PayTradePO tradePO = payTradeService.findByOrderTradeNo(orderTradeNo);
                        if (PayTradeStatus.PAYED.equals(tradePO.getStatus())) {
                            break;
                        }
                        OrderResponseDTO responseDTO = queryOrder(tradePO);
                        if ("0".equals(responseDTO.getResultCode()) && "true".equals(responseDTO.getResultMsg())) {
                            break;
                        }
                    }
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
    private OrderResponseDTO doAfterWeChatSuccess(Map<String, String> resultMap) {
        //1、保存微信交易的通知日志
        PayWeChatNotifyInfoPO weChatNotifyInfoPO = (PayWeChatNotifyInfoPO) ObjectUtil.mapToBean(PayWeChatNotifyInfoPO.class, resultMap);
        weChatPayNotifyService.saveWeChatPayNotify(weChatNotifyInfoPO);
        //2、更改订单状态
        PayNotifyInfoDTO payNotifyInfoDTO = new PayNotifyInfoDTO();
        payNotifyInfoDTO.setOrderTradeNo(weChatNotifyInfoPO.getOut_trade_no());
        payNotifyInfoDTO.setPaySerialNumber(weChatNotifyInfoPO.getTransaction_id());
        payNotifyInfoDTO.setStatus(PayTradeStatus.PAYED);
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
        //3、转化为DTO
        return  payTradeService.transfer(resultMap.get("out_trade_no"));
    }


    @DataSource("master")
    @Transactional
    private RefundOrderResponseDTO doAfterRefundSuccess(Map<String, String> resultMap) {
        //1、修改退款单的状态
        RefundNotifyInfoDTO notifyInfoDTO = new RefundNotifyInfoDTO();
        notifyInfoDTO.setRefundTradeNo(resultMap.get("out_refund_no"));
        notifyInfoDTO.setRefundSerialNumber(resultMap.get("refund_id"));
        notifyInfoDTO.setRefundDate(new Timestamp(System.currentTimeMillis()));
        notifyInfoDTO.setStatus(PayRefundTradeStatus.REFUND);
        if(StringUtils.isNotEmpty(resultMap.get("coupon_refund_fee"))){
            notifyInfoDTO.setCouponFee(new BigDecimal(resultMap.get("coupon_refund_fee")).divide(new BigDecimal("100")).doubleValue());
        }
        refundTradeService.updateAfterRefundSuccess(notifyInfoDTO);
        //2、保存退款通知信息
        PayRefundWeChatOfNotifyInfoPO refundNotifyPO = (PayRefundWeChatOfNotifyInfoPO)ObjectUtil.mapToBean(PayRefundWeChatOfNotifyInfoPO.class, resultMap);
        refundNotifyPO.setRefundDate(new Timestamp(System.currentTimeMillis()));
        refundNotifyPO.setRefundFee(new BigDecimal(refundNotifyPO.getRefund_fee()).divide(new BigDecimal("100")).doubleValue());
        refundWeChatOfNotifyService.savePayRefundWeChatOfNotifyInfo(refundNotifyPO);
        //3、转换成dto返回
        return refundTradeService.transfer(resultMap.get("out_refund_no"));
    }
    /**
     * 根据不同的商户号码获取不同的微信配置类
     *
     * @param payPartnerAccoutId
     * @return
     */
    private MyWxPayConfig getMyWxPayConfig(Long payPartnerAccoutId) {
        PayPartnerAccountPO payPartnerAccout = PayCacheHandle.getPayPartnerAccout(payPartnerAccoutId);
        return new MyWxPayConfig(payPartnerAccout.getAppid(), payPartnerAccout.getPartner(), payPartnerAccout.getEncryptKey(), payPartnerAccout.getKeyPath()+"apiclient_cert.p12");
    }
}
