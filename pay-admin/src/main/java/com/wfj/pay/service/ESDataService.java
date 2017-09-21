package com.wfj.pay.service;

import java.util.List;

import com.wfj.pay.dto.OPSOrderResponseDTO;
import com.wfj.pay.dto.OrderQueryReqDTO;
import com.wfj.pay.dto.OrderQueryResDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.PayOrderDetailDTO;
import com.wfj.pay.dto.StatisticsByTypeDTO;
import com.wfj.pay.dto.StatisticsDTO;
import com.wfj.pay.dto.StatisticsRequestDTO;

/**
 * es操作service
 * @author jh
 *
 */
public interface ESDataService {

	/**
	 * 分页查询订单
	 * @param tradeDTO
	 * @return
	 */
	PaginationDTO<OPSOrderResponseDTO> findAllPagePayOrder(OrderQueryReqDTO tradeDTO);

	/**
	 * 查询支付详细信息
	 * @param orderTradeNo
	 * @return
	 */
	PayOrderDetailDTO findOrderDetail(String orderTradeNo);

	/**
	 * 导出订单的数据
	 * @param reqDTO
	 * @return
	 */
	List<OrderQueryResDTO> selectOrderExport(OrderQueryReqDTO reqDTO);

	/**
	 * 统计管理按渠道查询
	 * @param reqDTO
	 * @return
	 */
	PaginationDTO<StatisticsByTypeDTO> findAllPriceByType(OrderQueryReqDTO reqDTO);

	/**
	 * 查询对账信息列表.
	 * @param reqDTO
	 */
	PaginationDTO<OrderQueryResDTO> findAllPagePayOrderAdmin(OrderQueryReqDTO reqDTO);

	public void tradeToES();
}
