package com.wfj.pay.service;

import com.wfj.pay.dto.OrderResponseDTO;
import com.wfj.pay.dto.RefundOrderResponseDTO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.sdk.alipay.AliPayInterface;

import java.util.Map;

/**
 * Created by kongqf on 2017/7/11.
 */
public interface IAliPayService {
    /**
     * 支付宝支付成功之后的操作
     *
     * @param result
     * @param aliPayInterface
     * @return
     */
    OrderResponseDTO doAfterAliSuccess(String result, AliPayInterface aliPayInterface);


    /**
     * 支付宝退款成功之后的操作
     *
     * @param resultMap
     * @return
     */
    RefundOrderResponseDTO doAfterRefundSuccess(Map<String, String> resultMap);


    /**
     * 关闭成功之后的处理，更新订单状态、记录日志
     * @param orderTradeNo
     */
    void doAfterCloseSuccess(String orderTradeNo,String operateSource);


    /**
     * 根据支付平台订单号查询订单信息
     * @param orderTradeNo
     * @return
     */
    PayTradePO findByOrderTradeNo(String orderTradeNo);
}
