package com.wfj.pay.service;

import java.util.List;

import com.wfj.pay.dto.OrderQueryReqDTO;
import com.wfj.pay.dto.OrderQueryResDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.po.PayTradePO;

public interface IPayOrderService {

	/**
	 * 分页查询支付未成功的订单
	 * @param orderDTO
	 * @return
	 */
	PaginationDTO<OrderQueryResDTO> findAllOrderCompensate(OrderQueryReqDTO orderDTO);

	/**
	 * 查询所有支付未成功订单
	 * @param orderDTO
	 */
	List<PayTradePO> findAllOrderByStatus(OrderQueryReqDTO orderDTO);

	List<PayTradePO> tradeToES();

}
