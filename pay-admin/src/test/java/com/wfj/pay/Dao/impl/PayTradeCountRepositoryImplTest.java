package com.wfj.pay.Dao.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.wfj.pay.BootApplication;
import com.wfj.pay.dto.OrderQueryReqDTO;
import com.wfj.pay.service.impl.PayTradeCountRepositoryImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class PayTradeCountRepositoryImplTest {

	@Autowired
	PayTradeCountRepositoryImpl impl;
	
	@Test
	public void test() {
		List<String> payTypes = new ArrayList<String>();
		List<Long> bpIds = new ArrayList<Long>();
		Long startDate = 1500235885558L;
		Long endDate = 1500635885558L;
		//impl.selectPayTradeCount(payTypes, bpIds, startDate, endDate);
		
	}
	
	@Test
	public void test2() {
		OrderQueryReqDTO dto = new OrderQueryReqDTO();
		dto.setStartTime(1500235885558L);
		dto.setEndTime(1500635885558L);
		Long count = impl.getTradeCountByParams(dto);
		System.out.println(count);
	}

}
