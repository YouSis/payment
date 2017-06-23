package com.wfj.pay.dubbo;

import com.wfj.pay.dto.OrderRequestDTO;
import com.wfj.pay.dto.OrderResponseDTO;

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
}
