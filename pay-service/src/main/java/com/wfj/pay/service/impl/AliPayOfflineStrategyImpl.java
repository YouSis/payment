package com.wfj.pay.service.impl;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.wfj.ea.common.ErrorLevel;
import com.wfj.pay.cache.PayCacheHandle;
import com.wfj.pay.constant.ErrorCodeEnum;
import com.wfj.pay.constant.PayTradeStatus;
import com.wfj.pay.constant.PayTypeEnum;
import com.wfj.pay.constant.SceneEnum;
import com.wfj.pay.dto.BleException;
import com.wfj.pay.dto.OrderResponseDTO;
import com.wfj.pay.dto.PayTradeDTO;
import com.wfj.pay.dto.RefundOrderResponseDTO;
import com.wfj.pay.po.PayPartnerAccountPO;
import com.wfj.pay.po.PayRefundTradePO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.sdk.alipay.AliPayConfig;
import com.wfj.pay.sdk.alipay.AliPayConstants;
import com.wfj.pay.sdk.alipay.AliPayInterface;
import com.wfj.pay.sdk.alipay.NotifyTradeStatus;
import com.wfj.pay.service.IAliPayService;
import com.wfj.pay.service.IPayRefundTradeService;
import com.wfj.pay.service.IPayStrategyService;
import com.wfj.pay.service.IPayTradeService;
import com.wfj.pay.utils.DistributedLock;
import com.wfj.pay.utils.ExceptionUtil;
import com.wfj.pay.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by kongqf on 2017/7/5.
 */
@Service
public class AliPayOfflineStrategyImpl implements IPayStrategyService {
    private Logger logger = LoggerFactory.getLogger(AliPayOfflineStrategyImpl.class);

    @Value("${distributed.lock.zk.address}")
    private String distributedLockZKUrl;
    @Autowired
    private IAliPayService aliPayService;
    @Autowired
    private IPayTradeService payTradeService;
    @Autowired
    private IPayRefundTradeService refundTradeService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(30, r -> {
        Thread t = new Thread(r);
        t.setName("alipay-query-thread-" + t.getId());
        return t;
    });

    private final ExecutorService sendDataExecutorService = Executors.newFixedThreadPool(20, r -> {
        Thread t = new Thread(r);
        t.setName("send-pay-trade-thread-" + t.getId());
        return t;
    });

    @Override
    public boolean match(PayTypeEnum payTypeEnum) {
        return payTypeEnum.equals(PayTypeEnum.ALIPAY_OFFLINE);
    }

    @Override
    public OrderResponseDTO toPay(PayTradeDTO payTradeDTO) {
        OrderResponseDTO responseDTO;
        AliPayConfig aliPayConfig = getAliPayConfig(payTradeDTO.getPayPartner());
        AlipayClient alipayClient = getAliPayClient(aliPayConfig);

        //1、组织查询报文
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        Map<String, String> data = getPayParam(payTradeDTO);
        request.setBizContent(JSON.toJSONString(data));

        //2、调用支付宝sdk发起支付
        long beginTime = System.currentTimeMillis();
        AlipayTradePayResponse response = null;
        try {
            response = alipayClient.execute(request);
            logger.info("支付返回结果：" + JSON.toJSONString(response));
        } catch (AlipayApiException e) {
            ExceptionUtil.sendException(new BleException(ErrorCodeEnum.ALIPAY_ERROR.getErrorCode(),"调用支付宝支付失败："+e.toString()+"  "+data.toString(), ErrorLevel.WARNING.getCode()));
            logger.error("调用支付宝支付失败:" + e.toString(), e);
            responseDTO = new OrderResponseDTO("1", "false", "请求支付宝支付失败，请重试");
            return responseDTO;
        }
        long endTime = System.currentTimeMillis();
        logger.info("--->订单号为{}的订单请求支付宝下单的时间为{}毫秒", payTradeDTO.getOrderTradeNo(), endTime - beginTime);
        //3、处理支付宝返回的结果
        responseDTO = processResponse(response, payTradeDTO.getOrderTradeNo());
        return responseDTO;
    }

    @Override
    public OrderResponseDTO queryOrder(PayTradePO payTradePO) {
        OrderResponseDTO responseDTO;
        AliPayConfig aliPayConfig = getAliPayConfig(payTradePO.getPayPartner());
        AlipayClient alipayClient = getAliPayClient(aliPayConfig);

        //1、组织查询报文
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
        Map<String, String> data = new HashMap<>();
        data.put("out_trade_no", payTradePO.getOrderTradeNo());
        request.setBizContent(JSON.toJSONString(data));
        logger.info("查询需加密的参数:", request.getBizContent());

        //2、调用sdk发起查询
        // 请求支付宝查询接口开始时间
        long beginTime = System.currentTimeMillis();
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
            logger.info("--->查询返回的参数：" + response.getBody());
        } catch (AlipayApiException e) {
            ExceptionUtil.sendException(new BleException(ErrorCodeEnum.ALIPAY_ERROR.getErrorCode(),"调用支付宝查询失败："+e.toString()+"  "+data.toString(), ErrorLevel.WARNING.getCode()));
            logger.error("调用支付宝查询订单失败:" + e.toString(), e);
            responseDTO = new OrderResponseDTO("1", "false", "请求支付宝查询失败，请重试");
            return responseDTO;
        }
        // 请求支付宝查询接口结束时间
        long endTime = System.currentTimeMillis();
        logger.info("--->订单号为{}的订单单笔查询支付宝的时间为{}毫秒", payTradePO.getOrderTradeNo(), endTime - beginTime);

        //3、处理支付宝返回的结果
        responseDTO = processQueryResponse(response);
        return responseDTO;
    }

    @Override
    public OrderResponseDTO closeOrder(PayTradePO tradePO, String source) {
        //1、先进行单笔查询，如果是支付成功状态，直接返回错误
        OrderResponseDTO responseDTO = queryOrder(tradePO);
        if ("0".equals(responseDTO.getResultCode()) || "true".equals(responseDTO.getResultMsg())) {
            responseDTO = new OrderResponseDTO("1", "false", "订单已支付，不能关闭");
            return responseDTO;
        }
        //2、如果是未支付，调用支付宝关闭接口
        AliPayConfig aliPayConfig = getAliPayConfig(tradePO.getPayPartner());
        AlipayClient alipayClient = getAliPayClient(aliPayConfig);
        //1、组织查询报文
        Map<String, String> data = new HashMap<>();
        data.put("out_trade_no", tradePO.getOrderTradeNo());
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizContent(JSON.toJSONString(data));
        //2、如果是未支付，调用支付宝关闭接口
        AlipayTradeCloseResponse response = null;
        long beginTime = System.currentTimeMillis();
        try {
            response = alipayClient.execute(request);
            logger.info("调用支付宝关闭返回结果:" + JSON.toJSONString(response));
        } catch (AlipayApiException e) {
            ExceptionUtil.sendException(new BleException(ErrorCodeEnum.ALIPAY_ERROR.getErrorCode(),"调用支付宝关闭失败："+e.toString()+"  "+data.toString(), ErrorLevel.WARNING.getCode()));
            logger.error("调用支付宝关闭失败:" + e.toString(), e);
            responseDTO = new OrderResponseDTO("1", "false", "请求支付宝撤销失败，请重试");
            return responseDTO;
        }
        long endTime = System.currentTimeMillis();
        logger.info("--->订单号为{}的订单调用支付宝撤销接口的时间为{}毫秒", tradePO.getOrderTradeNo(), endTime - beginTime);

        //3、处理微信返回结果
        responseDTO = processCloseResponse(response, source);
        return responseDTO;
    }

    @Override
    public RefundOrderResponseDTO toRefund(PayRefundTradePO refundTradePO, PayTradePO tradePO) {
        RefundOrderResponseDTO responseDTO;
        AliPayConfig aliPayConfig = getAliPayConfig(tradePO.getPayPartner());
        AlipayClient alipayClient = getAliPayClient(aliPayConfig);

        //1、组织查询报文
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", tradePO.getOrderTradeNo());
        data.put("refund_amount", String.valueOf(refundTradePO.getRefundFee()));
        data.put("out_request_no", refundTradePO.getRefundTradeNo());
        data.put("operator_id", tradePO.getCashier());
        data.put("store_id", tradePO.getMerCode());
        data.put("terminal_id", tradePO.getBpOrderId().substring(0, 3));
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(JSON.toJSONString(data));

        //2、调用支付宝退款接口
        long beginTime = System.currentTimeMillis();
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
            logger.info("调用退款返回结果：" + JSON.toJSONString(response));
        } catch (AlipayApiException e) {
            ExceptionUtil.sendException(new BleException(ErrorCodeEnum.ALIPAY_ERROR.getErrorCode(),"调用支付宝退款失败："+e.toString()+"  "+data.toString(), ErrorLevel.WARNING.getCode()));
            logger.error("调用支付宝退款失败:" + e.toString(), e);
            responseDTO = new RefundOrderResponseDTO("1", "false", "请求支付宝退款失败，请重试");
            return responseDTO;
        }
        long endTime = System.currentTimeMillis();
        logger.info("--->订单号为{}的订单调用支付宝退款接口的时间为{}毫秒", tradePO.getOrderTradeNo(), endTime - beginTime);

        //3、处理支付宝返回的结果
        responseDTO = processRefundResponse(response, aliPayConfig, refundTradePO);
        return responseDTO;
    }

    @Override
    public RefundOrderResponseDTO queryRefundOrder(PayRefundTradePO refundTradePO, PayTradePO tradePO) {
        RefundOrderResponseDTO responseDTO;
        AliPayConfig aliPayConfig = getAliPayConfig(tradePO.getPayPartner());
        AlipayClient alipayClient = getAliPayClient(aliPayConfig);

        //1、组织查询报文
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", tradePO.getOrderTradeNo());
        data.put("out_request_no", refundTradePO.getRefundTradeNo());
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        request.setBizContent(JSON.toJSONString(data));

        //2、调用支付宝退款查询接口
        long beginTime = System.currentTimeMillis();
        AlipayTradeFastpayRefundQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
            logger.info("调用退款查询返回结果：" + JSON.toJSONString(response));
        } catch (AlipayApiException e) {
            ExceptionUtil.sendException(new BleException(ErrorCodeEnum.ALIPAY_ERROR.getErrorCode(),"调用支付宝退款查询失败："+e.toString()+"  "+data.toString(), ErrorLevel.WARNING.getCode()));
            logger.error("调用支付宝退款查询接口失败:" + e.toString(), e);
            responseDTO = new RefundOrderResponseDTO("1", "false", "请求支付宝退款查询失败，请重试");
            return responseDTO;
        }
        long endTime = System.currentTimeMillis();
        logger.info("--->订单号为{}的订单调用支付宝退款查询接口的时间为{}毫秒", tradePO.getOrderTradeNo(), endTime - beginTime);

        //3、处理支付宝返回的结果
        responseDTO = processRefundQueryResponse(response, refundTradePO);
        return responseDTO;
    }

    /**
     * 处理支付宝查询返回的结果
     *
     * @param response
     * @return
     */
    private OrderResponseDTO processQueryResponse(AlipayTradeQueryResponse response) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        if (response == null || !AliPayConstants.SUCCESS.equals(response.getCode())) {
            orderResponseDTO = new OrderResponseDTO("1", "false", "请求支付宝查询失败，参数错误，请重试");
            return orderResponseDTO;
        }

        //查询成功
        if (NotifyTradeStatus.TRADE_SUCCESS.getStatus().equals(response.getTradeStatus())) {
            DistributedLock zkLock = new DistributedLock(distributedLockZKUrl, response.getOutTradeNo());
            zkLock.lock();
            try {
                orderResponseDTO = aliPayService.doAfterAliSuccess(response.getBody(), AliPayInterface.AlipayTrade_Query);
            } finally {
                zkLock.unlock();
            }
            //发送订单数据MQ
            sendDataExecutorService.submit(() -> payTradeService.sendPayTradeToMQ(response.getOutTradeNo()));
        } else if (NotifyTradeStatus.TRADE_CLOSED.getStatus().equals(response.getTradeStatus())) {
            orderResponseDTO = new OrderResponseDTO("1", "false", "该订单已关闭或已退款");
            return orderResponseDTO;
        } else if (NotifyTradeStatus.WAIT_BUYER_PAY.getStatus().equals(response.getTradeStatus())) {
            orderResponseDTO = new OrderResponseDTO("1", "false", "等待买家付款");
            return orderResponseDTO;
        } else {
            orderResponseDTO = new OrderResponseDTO("1", "false", "订单支付失败！请重新支付");
            return orderResponseDTO;
        }

        return orderResponseDTO;
    }

    /**
     * 处理支付宝支付返回的结果
     *
     * @param response
     * @return
     */
    private OrderResponseDTO processResponse(AlipayTradePayResponse response, String orderTradeNo) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        if (response == null) {
            orderResponseDTO = new OrderResponseDTO("1", "false", "请求支付宝支付失败，参数错误，请重试");
            return orderResponseDTO;
        }

        //支付成功
        if (AliPayConstants.SUCCESS.equals(response.getCode())) {
            DistributedLock zkLock = new DistributedLock(distributedLockZKUrl, response.getOutTradeNo());
            zkLock.lock();
            try {
                orderResponseDTO = aliPayService.doAfterAliSuccess(response.getBody(), AliPayInterface.AlipayTrade_Pay);
            } finally {
                zkLock.unlock();
            }
            //发送订单数据MQ
            sendDataExecutorService.submit(() -> payTradeService.sendPayTradeToMQ(orderTradeNo));
        } else {
            if (AliPayConstants.PAYING.equals(response.getCode())) {
                orderResponseDTO = new OrderResponseDTO("0", "false", response.getMsg());
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
                orderResponseDTO = new OrderResponseDTO("1", "false", "请求支付宝支付失败，" + response.getSubCode());
            }
        }

        return orderResponseDTO;
    }

    private RefundOrderResponseDTO processRefundResponse(AlipayTradeRefundResponse response, AliPayConfig aliPayConfig, PayRefundTradePO refundTradePO) {
        RefundOrderResponseDTO responseDTO;
        if (response == null) {
            responseDTO = new RefundOrderResponseDTO("1", "false", "请求支付宝退款失败，参数错误，请重试");
            return responseDTO;
        }

        //退款成功
        if (AliPayConstants.SUCCESS.equals(response.getCode())) {
            Map<String, String> responseMap = JsonUtil.string2Map(response.getBody());
            String reslut = responseMap.get(AliPayInterface.AlipayTrade_Refund.getMethod());
            Map<String, String> resultMap = JsonUtil.string2Map(reslut);
            resultMap.put("out_refund_no", refundTradePO.getRefundTradeNo());
            responseDTO = aliPayService.doAfterRefundSuccess(resultMap);

            //发送订单数据MQ
            sendDataExecutorService.submit(() -> refundTradeService.sendPayRefundTradeToMQ(resultMap.get("out_refund_no")));
        } else {
            responseDTO = new RefundOrderResponseDTO("1", "false", "请求支付宝退款失败，" + response.getSubCode());
        }
        return responseDTO;
    }

    private RefundOrderResponseDTO processRefundQueryResponse(AlipayTradeFastpayRefundQueryResponse response, PayRefundTradePO refundTradePO) {
        RefundOrderResponseDTO responseDTO;
        //1、先判断请求是否成功
        if (response == null) {
            responseDTO = new RefundOrderResponseDTO("1", "false", "请求支付宝退款查询失败，参数错误，请重试");
            return responseDTO;
        }

        //退款查询成功
        if (AliPayConstants.SUCCESS.equals(response.getCode())) {
            Map<String, String> responseMap = JsonUtil.string2Map(response.getBody());
            String reslut = responseMap.get(AliPayInterface.AlipayTrade_FastpayRefundQuery.getMethod());
            Map<String, String> resultMap = JsonUtil.string2Map(reslut);
            resultMap.put("out_refund_no", refundTradePO.getRefundTradeNo());

            responseDTO = aliPayService.doAfterRefundSuccess(resultMap);
            //发送订单数据MQ
            sendDataExecutorService.submit(() -> refundTradeService.sendPayRefundTradeToMQ(resultMap.get("out_refund_no")));
        } else {
            responseDTO = new RefundOrderResponseDTO("1", "false", "退款失败，" + response.getSubCode());
        }
        return responseDTO;
    }

    private OrderResponseDTO processCloseResponse(AlipayTradeCloseResponse response, String source) {
        OrderResponseDTO orderResponseDTO;
        //1、先判断请求是否成功
        if (response == null) {
            orderResponseDTO = new OrderResponseDTO("1", "false", "请求支付宝撤销订单失败，参数错误，请重试");
            return orderResponseDTO;
        }
        //2、判断业务状态
        if (AliPayConstants.SUCCESS.equals(response.getCode())) {
            orderResponseDTO = new OrderResponseDTO("0", "true", "关闭成功");
            //更新本地的订单状态，记录日志
            aliPayService.doAfterCloseSuccess(response.getOutTradeNo(), source);
            //发送订单数据到MQ
            payTradeService.sendPayTradeToMQ(response.getOutTradeNo());
        } else {
            //支付失败
            orderResponseDTO = new OrderResponseDTO("1", "false", "请求支付宝撤销订单失败，" + response.getSubCode());
        }
        return orderResponseDTO;
    }

    /**
     * 拼接支付参数 把数组所有元素去空
     *
     * @param payTradeDTO
     * @return
     */
    public Map<String, String> getPayParam(PayTradeDTO payTradeDTO) {
        // 支付参数map
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("out_trade_no", payTradeDTO.getOrderTradeNo());
        paramMap.put("scene", SceneEnum.BarCode.getType());
        paramMap.put("auth_code", payTradeDTO.getAuthCode());
        paramMap.put("total_amount", String.valueOf(payTradeDTO.getTotalFee()));
        paramMap.put("subject", "订单号："+payTradeDTO.getGoodsName());
        paramMap.put("body", payTradeDTO.getContent());
        paramMap.put("operator_id", payTradeDTO.getCashier());
        PayPartnerAccountPO payPartnerAccout = PayCacheHandle.getPayPartnerAccout(payTradeDTO.getPayPartner());
        //针对开通支付宝口碑门店的特殊处理逻辑
        if(StringUtils.isNotEmpty(payPartnerAccout.getStoreId())){
            paramMap.put("alipay_store_id", payPartnerAccout.getStoreId());
        }else{
            paramMap.put("store_id", payTradeDTO.getMerCode());
        }
        paramMap.put("terminal_id", payTradeDTO.getBpOrderId().substring(0, 4));
        paramMap.put("timeout_express", "90m");
        return paramMap;
    }


    /**
     * aliPay 支付配置
     *
     * @param payPartnerAccoutId
     * @return
     */
    private AliPayConfig getAliPayConfig(Long payPartnerAccoutId) {
        PayPartnerAccountPO payPartnerAccout = PayCacheHandle.getPayPartnerAccout(payPartnerAccoutId);
        return new AliPayConfig(payPartnerAccout.getAppid(), payPartnerAccout.getPrivateKey(), payPartnerAccout.getPublicKey());
    }

    private AlipayClient getAliPayClient(AliPayConfig config) {
        return new DefaultAlipayClient(config.getURL(), config.getAPP_ID(), config.getAPP_PRIVATE_KEY(), config.getFORMAT(), config.getCHARSET(), config.getALIPAY_PUBLIC_KEY(), config.getSIGN_TYPE());
    }
}
