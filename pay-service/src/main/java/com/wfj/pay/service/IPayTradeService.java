package com.wfj.pay.service;

import com.wfj.pay.dto.OrderRequestDTO;
import com.wfj.pay.dto.OrderResponseDTO;
import com.wfj.pay.dto.PayNotifyInfoDTO;
import com.wfj.pay.dto.PayTradeDTO;
import com.wfj.pay.po.PayTradePO;

/**
 * Created by wjg on 2017/6/23.
 */
public interface IPayTradeService {
    /**
     * 根据bpId和bpOrderId查找订单
     * @param bpId
     * @param bpOrderId
     * @return
     */
    PayTradePO findByBpIdAndBpOrderId(Long bpId,String bpOrderId);

    /**
     * 根据bpId和orderTradeNo查找订单
     * @param bpId
     * @param orderTradeNo
     * @return
     */
    PayTradePO findByBpIdAndOrderTradeNo(Long bpId,String orderTradeNo);

    /**
     * 根据支付平台订单号查询订单信息
     * @param orderTradeNo
     * @return
     */
    PayTradePO findByOrderTradeNo(String orderTradeNo);

    /**
     * 创建订单
     * @param orderRequestDTO
     * @return
     */
    PayTradeDTO createOrder(OrderRequestDTO orderRequestDTO);

    /**
     * 支付成功之后修改订单状态、支付时间等
     * @param payNotifyInfoDTO
     */
    void updateOrderAfterPaySuccess(PayNotifyInfoDTO payNotifyInfoDTO);

    /**
     * 支付成功之后的实体类转换成OrderResponseDTO
     * @param payTradePO
     * @return
     */
    OrderResponseDTO transfer(PayTradePO payTradePO);
}
