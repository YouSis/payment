package com.wfj.pay.Dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.wfj.pay.po.PayOrderStatisticsEsPO;
import com.wfj.pay.po.PayTradeEsPO;

/**
 * 正向订单统计
 * 
 * @author jh
 *
 */
public interface PayTradeRepository extends ElasticsearchRepository<PayTradeEsPO, Long> {

	Page<PayTradeEsPO> findByPayTypeAndBpOrderIdAndBpIdAndStatusAndOrderTradeNoAndInitOrderTerminalAndBpIdInAndCreateDateBetweenOrderByCreateDateDesc(
			String payType, String bpOrderId, Long bpId, Long status, String orderTradeNo, String initOrderTerminal, List<Long> bpIds, Long startTime,
			Long endTime, Pageable pageable);
	
	Page<PayTradeEsPO> findByPayTypeAndBpOrderIdAndBpIdAndStatusAndOrderTradeNoAndInitOrderTerminalAndBpIdInOrderByCreateDateDesc(String payType, String bpOrderId, Long bpId, Long status, String orderTradeNo, String initOrderTerminal, List<Long> bpIds, Pageable pageable);
	
	Page<PayTradeEsPO> findByCreateDateBetweenOrderByCreateDateDesc(Long startTime, Long endTime, Pageable pageable);
	
	Page<PayTradeEsPO> findByBpIdInOrderByCreateDateDesc(List<Long> bpIds, Pageable pageable);
	
}
