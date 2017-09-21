package com.wfj.pay.service;

import java.util.List;
import java.util.Map;

import com.wfj.pay.dto.OrderQueryReqDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.PayMentDateDTO;
import com.wfj.pay.dto.StatisticsDTO;
import com.wfj.pay.dto.StatisticsRequestDTO;

public interface PayTradeCountRepository {

	/**
	 * 查询符合条件订单数
	 * @param reqDTO
	 * @return
	 */
	Long getTradeCountByParams(OrderQueryReqDTO reqDTO);

	/**
	 * 统计管理统计查询  分页
	 * @param reqDTO
	 * @return
	 */
	PaginationDTO<StatisticsDTO> searchlist(StatisticsRequestDTO reqDTO);
	
	/**
	 * 带过滤条件统计订单金额
	 * @param payTypes  支付渠道集合
	 * @param bpIds		bpId集合
	 * @param startDate 开始时间   不能为空
	 * @param endDate	结束时间   不能为空
	 * @return
	 */
    public List<PayMentDateDTO> selectPayTradeCount(List<String> payTypes, List<String> merCodes, Long startDate, Long endDate);

    /**
     * 统计订单支付总金额和费率总金额
     * @param reqDTO
     * @return
     */
	Map<String, Object> verificaMoneyCount(OrderQueryReqDTO reqDTO);

}
