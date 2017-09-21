package com.wfj.pay.Dao;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.wfj.pay.po.PayLogEsPO;

public interface payLogRepository extends ElasticsearchRepository<PayLogEsPO, Long>{

	/**
	 * 根据订单号查询支付日志
	 * @param orderTradeNo
	 * @return
	 */
	List<PayLogEsPO> findByOrderTradeNoOrderByIdDesc(String orderTradeNo);
	
}
