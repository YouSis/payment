package com.wfj.pay.Dao;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.repository.CrudRepository;

import com.wfj.pay.po.PayOrderStatisticsEsPO;

public interface PayOrderStatisticsRepository extends CrudRepository<PayOrderStatisticsEsPO, Long>{

	@Query("{ \"query\": { \"bool\": { \"must\": { \"bool\":{ \"should\":[{\"field\" : {\"payType\" : ?0}}, {\"field\" : {\"bpId\" : ?1}}]}, \"range\": { \"createDate\": { \"from\": ?2, \"to\": ?3, \"include_lower\": true, \"include_upper\": true }}}}}, "
			+ "\"aggs\": { \"groupBymerCode\": {\"terms\": {\"field\": \"merCode\"}, \"aggs\": { \"sumFee\": { \"sum\": { \"field\": \"totalFee\"}}}}}}")
	List<PayOrderStatisticsEsPO> findByPayTypeInAndBpIdInAndCreateDateBetween(List<String> payTypes, List<Long> bpIds, Long startTime, Long endTime);
	
	@Query("{\"query\": { \"bool\": { \"must\": { \"bool\":{ \"should\":[{\"field\" : {\"payType\" : ?0}},{\"field\" : {\"bpId\" : ?1}}]}}}}}")
	List<PayOrderStatisticsEsPO> findByPayTypeIn(List<String> payTypes, List<Long> bpIds);
}
