package com.wfj.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.wfj.ea.common.ErrorLevel;
import com.wfj.pay.cache.PayCacheHandle;
import com.wfj.pay.constant.ErrorCodeEnum;
import com.wfj.pay.constant.PayLogConstant;
import com.wfj.pay.constant.PayTradeStatus;
import com.wfj.pay.constant.PayTypeEnum;
import com.wfj.pay.dto.BleException;
import com.wfj.pay.dto.OrderResponseDTO;
import com.wfj.pay.dto.PayTradeDTO;
import com.wfj.pay.dto.RefundOrderResponseDTO;
import com.wfj.pay.po.PayPartnerAccountPO;
import com.wfj.pay.po.PayRefundTradePO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.sdk.wxpay.MyWxPayConfig;
import com.wfj.pay.sdk.wxpay.WXPay;
import com.wfj.pay.sdk.wxpay.WXPayConstants;
import com.wfj.pay.sdk.wxpay.WxPayTradeStateConstants;
import com.wfj.pay.service.IPayRefundTradeService;
import com.wfj.pay.service.IPayStrategyService;
import com.wfj.pay.service.IPayTradeService;
import com.wfj.pay.service.IWeChatPayService;
import com.wfj.pay.utils.DistributedLock;
import com.wfj.pay.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
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
    private IPayTradeService payTradeService;
    @Autowired
    private IWeChatPayService weChatPayService;
    @Autowired
    private IPayRefundTradeService refundTradeService;
    @Value("${distributed.lock.zk.address}")
    private String distributedLockZKUrl;
    private final ExecutorService executorService = Executors.newFixedThreadPool(30, r -> {
        Thread t = new Thread(r);
        t.setName("wechatpay-query-thread-" + t.getId());
        return t;
    });
    private final ExecutorService sendDataExecutorService = Executors.newFixedThreadPool(20, r -> {
        Thread t = new Thread(r);
        t.setName("send-pay-trade-thread-" + t.getId());
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
        HashMap<String, String> data = new HashMap<>();
        data.put("body", "订单号："+payTradeDTO.getGoodsName());
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
            ExceptionUtil.sendException(new BleException(ErrorCodeEnum.WECHAT_ERROR.getErrorCode(),"调用微信支付失败："+e.toString()+"  "+data.toString(), ErrorLevel.WARNING.getCode()));
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
            ExceptionUtil.sendException(new BleException(ErrorCodeEnum.WECHAT_ERROR.getErrorCode(),"调用微信查询失败："+e.toString()+"  "+data.toString(), ErrorLevel.WARNING.getCode()));
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
    public OrderResponseDTO closeOrder(PayTradePO tradePO,String source) {
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
            ExceptionUtil.sendException(new BleException(ErrorCodeEnum.WECHAT_ERROR.getErrorCode(),"调用微信关闭失败："+e.toString()+"  "+data.toString(), ErrorLevel.WARNING.getCode()));
            logger.error("调用微信关闭订单失败:" + e.toString(), e);
            responseDTO = new OrderResponseDTO("1", "false", "请求微信撤销失败，请重试");
            return responseDTO;
        }
        long endTime = System.currentTimeMillis();
        logger.info("--->订单号为{}的订单调用微信撤销接口的时间为{}毫秒", tradePO.getOrderTradeNo(), endTime - beginTime);
        //3、处理微信返回结果
        responseDTO = processCloseResponse(resultMap, tradePO.getOrderTradeNo(),source);
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
            ExceptionUtil.sendException(new BleException(ErrorCodeEnum.WECHAT_ERROR.getErrorCode(),"调用微信退款失败："+e.toString()+"  "+data.toString(), ErrorLevel.WARNING.getCode()));
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
        data.put("out_refund_no", refundTradePO.getRefundTradeNo());
        Map<String, String> resultMap;
        try {
            WXPay wxPay = new WXPay(myWxPayConfig);
            resultMap = wxPay.refundQuery(data);
        } catch (Exception e) {
            //调用微信失败
            //此处应该抛出异常给监控平台
            ExceptionUtil.sendException(new BleException(ErrorCodeEnum.WECHAT_ERROR.getErrorCode(),"调用微信退款查询失败："+e.toString()+"  "+data.toString(), ErrorLevel.WARNING.getCode()));
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
            //退款查询返回的报文有下标，顾进行转换
            resultMap.put("out_refund_no",resultMap.get("out_refund_no_0"));
            resultMap.put("refund_id",resultMap.get("refund_id_0"));
            if(StringUtils.isNotEmpty(resultMap.get("coupon_refund_fee_0"))){
                resultMap.put("coupon_refund_fee",resultMap.get("coupon_refund_fee_0"));
            }
            //退款成功之后更新退款单的状态
            responseDTO = weChatPayService.doAfterRefundSuccess(resultMap);
            //发送订单数据MQ
            sendDataExecutorService.submit(()->refundTradeService.sendPayRefundTradeToMQ(resultMap.get("out_refund_no")));
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
            responseDTO = weChatPayService.doAfterRefundSuccess(resultMap);
            //发送订单数据MQ
            sendDataExecutorService.submit(()->refundTradeService.sendPayRefundTradeToMQ(resultMap.get("out_refund_no")));
        } else {
            //支付失败
            String errCodeDes = resultMap.get("err_code_des");//关闭失败的描述
            responseDTO = new RefundOrderResponseDTO("1", "false", "请求微信退款失败，" + errCodeDes);
        }
        return  responseDTO;
    }

    private OrderResponseDTO processCloseResponse(Map<String, String> resultMap, String orderTradeNo,String source) {
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
            payTradeService.doAfterCloseSuccess(orderTradeNo, source);
            //发送订单数据到MQ
            payTradeService.sendPayTradeToMQ(orderTradeNo);
        } else {
            //支付失败
            String errCodeDes = resultMap.get("err_code_des");//关闭失败的描述
            orderResponseDTO = new OrderResponseDTO("1", "false", "请求微信撤销失败，" + errCodeDes);
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
                        orderResponseDTO = weChatPayService.doAfterWeChatSuccess(resultMap);
                    } finally {
                        zkLock.unlock();
                    }
                    //发送订单数据MQ
                    sendDataExecutorService.submit(()->payTradeService.sendPayTradeToMQ(resultMap.get("out_trade_no")));
                    break;
                case WxPayTradeStateConstants.REFUND:
                    orderResponseDTO = new OrderResponseDTO("1", "false", "订单已退款");
                    break;
                case WxPayTradeStateConstants.REVOKED:
                    orderResponseDTO = new OrderResponseDTO("1", "false", "订单已撤销");
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
     * @param resultMap 微信支付返回的map
     * @return OrderResponseDTO
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
                orderResponseDTO =  weChatPayService.doAfterWeChatSuccess(resultMap);
            } finally {
                zkLock.unlock();
            }
            //发送订单数据MQ
            sendDataExecutorService.submit(()->payTradeService.sendPayTradeToMQ(orderTradeNo));
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
     * 根据不同的商户号码获取不同的微信配置类
     *
     * @param payPartnerAccoutId
     * @return MyWxPayConfig
     */
    private MyWxPayConfig getMyWxPayConfig(Long payPartnerAccoutId) {
        PayPartnerAccountPO payPartnerAccout = PayCacheHandle.getPayPartnerAccout(payPartnerAccoutId);
        return new MyWxPayConfig(payPartnerAccout.getAppid(), payPartnerAccout.getPartner(), payPartnerAccout.getEncryptKey(), payPartnerAccout.getKeyPath()+"apiclient_cert.p12");
    }
}
