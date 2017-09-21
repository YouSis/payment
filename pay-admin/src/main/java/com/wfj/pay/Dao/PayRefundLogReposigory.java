package com.wfj.pay.Dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.wfj.pay.po.PayRefundLogEsPO;

public interface PayRefundLogReposigory extends ElasticsearchRepository<PayRefundLogEsPO, Long>{

}
