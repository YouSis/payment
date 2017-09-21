package com.wfj.pay.Dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.wfj.pay.po.PayRefundTradeEsPO;

public interface PayRefundTradeRepository extends ElasticsearchRepository<PayRefundTradeEsPO, Long>{

}
