package com.wfj.pay.Dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.wfj.pay.dto.PayDataDTO;
import com.wfj.pay.po.PayTradeEsPO;

/**
 * 正向订单统计
 * 
 * @author jh
 *
 */
public interface PayTradeRepository extends ElasticsearchRepository<PayTradeEsPO, Long>{

	/**
	 * 有bpid集合查询
	 * @param payType		支付方式
	 * @param bpOrderId		pos订单号
	 * @param bpId			bpid
	 * @param status		支付状态
	 * @param orderTradeNo		订单号
	 * @param initOrderTerminal	渠道
	 * @param bpIds				bpid集合
	 * @param startTime			查询开始时间
	 * @param endTime			查询结束时间
	 * @param pageable			分页类
	 * @return
	 */
	Page<PayTradeEsPO> findByPayTypeAndBpOrderIdAndBpIdAndStatusAndOrderTradeNoAndInitOrderTerminalAndBpIdInAndCreateDateBetweenOrderByCreateDateDesc(
			String payType, String bpOrderId, Long bpId, Long status, String orderTradeNo, String initOrderTerminal, List<Long> bpIds, Long startTime,
			Long endTime, Pageable pageable);
	
	/**
	 * 无bpid集合查询
	 * @param payType		支付方式
	 * @param bpOrderId		pos订单号
	 * @param bpId			bpid
	 * @param status		支付状态
	 * @param orderTradeNo		订单号
	 * @param initOrderTerminal	渠道
	 * @param startTime			查询开始时间
	 * @param endTime			查询结束时间
	 * @param pageable			分页类
	 * @return
	 */
	Page<PayTradeEsPO> findByPayTypeAndBpOrderIdAndBpIdAndStatusAndOrderTradeNoAndInitOrderTerminalAndCreateDateBetweenOrderByCreateDateDesc(
			String payType, String bpOrderId, Long bpId, Long status, String orderTradeNo, String initOrderTerminal, Long startTime,
			Long endTime, Pageable pageable);

	/**
	 * 根据订单号查询单条数据
	 * @param orderTradeNo
	 * @return
	 */
	PayTradeEsPO findByOrderTradeNo(String orderTradeNo);
	
	/**
	 * 统计管理按渠道查询 无bpids
	 * @param payType
	 * @param finalPayTerminal
	 * @param bpId
	 * @param payBank
	 * @param payService
	 * @param startTime
	 * @param endTime
	 * @param pageable
	 * @return
	 */
	Page<PayTradeEsPO> findByPayTypeAndFinalPayTerminalAndBpIdAndPayBankAndPayServiceAndCreateDateBetweenOrderByCreateDateDesc(
			String payType, String finalPayTerminal, Long bpId, String payBank, String payService, Long startTime,
			Long endTime, Pageable pageable);
	
	/**
	 * 统计管理按渠道查询 有bpids
	 * @param payType
	 * @param finalPayTerminal
	 * @param bpId
	 * @param payBank
	 * @param payService
	 * @param bpIds
	 * @param startTime
	 * @param endTime
	 * @param pageable
	 * @return
	 */
	Page<PayTradeEsPO> findByPayTypeAndFinalPayTerminalAndBpIdAndPayBankAndPayServiceAndBpIdInAndCreateDateBetweenOrderByCreateDateDesc(
			String payType, String finalPayTerminal, Long bpId, String payBank, String payService, List<Long> bpIds, Long startTime,
			Long endTime, Pageable pageable);
	
	/**
	 * 对账管理对账明细 无bpids
	 * @param payType
	 * @param finalPayTerminal
	 * @param bpId
	 * @param payBank
	 * @param payService
	 * @param startTime
	 * @param endTime
	 * @param pageable
	 * @return
	 */
	Page<PayTradeEsPO> findByPayTypeAndFinalPayTerminalAndBpIdAndPayBankAndPayServiceAndUsernameAndCreateDateBetweenOrderByCreateDateDesc(
			String payType, String finalPayTerminal, Long bpId, String payBank, String payService, String username, Long startTime,
			Long endTime, Pageable pageable);
	
	/**
	 * 对账管理对账明细 有bpids
	 * @param payType
	 * @param finalPayTerminal
	 * @param bpId
	 * @param payBank
	 * @param payService
	 * @param bpIds
	 * @param startTime
	 * @param endTime
	 * @param pageable
	 * @return
	 */
	Page<PayTradeEsPO> findByPayTypeAndFinalPayTerminalAndBpIdAndPayBankAndPayServiceAndUsernameAndBpIdInAndCreateDateBetweenOrderByCreateDateDesc(
			String payType, String finalPayTerminal, Long bpId, String payBank, String payService, String username, List<Long> bpIds, Long startTime,
			Long endTime, Pageable pageable);
}
