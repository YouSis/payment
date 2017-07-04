package com.wfj.pay.dubbo;

import com.wfj.pay.dto.*;

/**
 * Created by wjg on 2017/6/22.
 */
public interface IOfflinePayDubbo {
    /**
     * 创建线下支付订单并支付
     * @param orderRequestDTO
     * @return
     */
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    /**
     * 订单查询
     * @param orderQueryRequestDTO
     * @return
     */
    OrderResponseDTO queryOrder(OrderQueryRequestDTO orderQueryRequestDTO);

    /**
     * 订单关闭
     * @param orderCloseRequestDTO
     * @return
     */
    OrderResponseDTO closeOrder(OrderCloseRequestDTO orderCloseRequestDTO);

    /**
     * 退款
     * @param refundOrderRequestDTO
     * @return
     */
    RefundOrderResponseDTO createRefundOrder(RefundOrderRequestDTO refundOrderRequestDTO);

    /**
     * 退款查询
     * @param refundOrderQueryRequestDTO
     * @return
     */
    RefundOrderResponseDTO queryRefundOrder(RefundOrderQueryRequestDTO refundOrderQueryRequestDTO);
}
