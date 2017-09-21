package com.wfj.pay.Dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wfj.pay.BootApplication;
import com.wfj.pay.po.PayTradeEsPO;
import com.wfj.pay.utils.HttpClientUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class PayTradeRepositoryTest {

	@Autowired
	private PayTradeRepository repository;
	
	@Test
	public void test() {
		String payType = "WECHATPAY_OFFLINE";
		String bpOrderId = null;
		Long bpId = null;
		Long status = 2L;
		String orderTradeNo = null;
		String initOrderTerminal = "04";
		List<Long> bpIds = new ArrayList<Long>();
		bpIds.add(10047L);
		//bpIds.add(10045L);
		Long startTime = 1500235885558L;
		Long endTime = null;
		
		Pageable pageable = new PageRequest(0, 10);
		//Page<PayTradeEsPO> list = repository.findAll();
		//Page<PayTradeEsPO> list = repository.findByPayTypeAndBpOrderIdAndBpIdAndStatusAndOrderTradeNoAndInitOrderTerminalAndBpIdInOrderByCreateDateDesc(payType, bpOrderId, bpId, status, orderTradeNo, initOrderTerminal, bpIds, pageable);
		//Page<PayTradeEsPO> list = repository.findByBpIdInOrderByCreateDateDesc(bpIds, pageable);
		
		//Page<PayTradeEsPO> list = repository.findByCreateDateBetweenOrderByCreateDateDesc(startTime, endTime, pageable);
		Page<PayTradeEsPO> list = repository.findByPayTypeAndBpOrderIdAndBpIdAndStatusAndOrderTradeNoAndInitOrderTerminalAndBpIdInAndCreateDateBetweenOrderByCreateDateDesc(payType, bpOrderId, bpId, status, orderTradeNo, initOrderTerminal, bpIds, startTime, endTime, pageable);
		List<PayTradeEsPO> esPO = list.getContent();
		esPO.forEach(po -> {
			System.out.println(po.getBpId());
			System.out.println(po.getOrderTradeNo());
			System.out.println(po.getCreateDate());
		});
		System.out.println(list);
		System.out.println(esPO.size());
		System.out.println(list.getSize());
	}
	
	@Test
	public void test3() {
		String payType = "WECHATPAY_OFFLINE";
		String bpOrderId = null;
		Long bpId = null;
		Long status = 2L;
		String orderTradeNo = null;
		String initOrderTerminal = "04";
		List<Long> bpIds = new ArrayList<Long>();
		bpIds.add(10047L);
		//bpIds.add(10045L);
		Long startTime = 1500235885558L;
		Long endTime = null;
		//Long po = repository.findByPayTypeAndBpOrderIdAndBpIdAndStatusAndOrderTradeNoAndInitOrderTerminalAndCreateDateBetween(payType, bpOrderId, bpId, status, orderTradeNo, initOrderTerminal, startTime, endTime);
		//System.out.println(po);
	}
	
	@Test
	public void test2() {
		String url = "http://10.6.2.128:9200/pay-data/pay-trade/_search?search_type=count";
		String data = "{"
				+ "\"query\": {"
				+ "\"bool\": {"
				+ "\"must\": ["
				+ "{"
				+ "\"bool\": {"
				+ "\"should\": ["
				+ "{"
				+ "\"match\": {"
				+ "\"payType\": \"WECHATPAY_OFFLINE\""
				+ "}"
				+ "},"
				+ "{"
				+ "\"match\": {"
				+ "\"payType\": \"ALIPAY_OFFLINE\""
				+ "}"
				+ "}"
				+ "]"
				+ "}"
				+ "},"
				+ "{"
				+ "\"bool\": {"
				+ "\"should\": ["
				+ "{"
				+ "\"match\": {"
				+ "\"bpId\": \"10045\""
				+ "}"
				+ "}"
				+ "]"
				+ "}"
				+ "}"
				+ "]"
				+ "}"
				+ "},"
				+ "\"aggs\": {"
				+ "\"groupBymerCode\": {"
				+ "\"terms\": {"
				+ "\"field\": \"merCode\""
				+ "},"
				+ "\"aggs\": {"
				+ "\"sumFee\": {"
				+ "\"sum\": {"
				+ "\"field\": \"totalFee\""
				+ "}"
				+ "}"
				+ "}"
				+ "},"
				+ "\"sumTotalFee\": {"
				+ "\"sum\": {"
				+ "\"field\": \"totalFee\""
				+ "}"
				+ "}"
				+ "}"
				+ "}";
		try {
			String result = HttpClientUtil.sendPostJson(url, data);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
