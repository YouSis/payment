package com.wfj.pay.Dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wfj.pay.BootApplication;
import com.wfj.pay.po.PayLogEsPO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class payLogRepositoryTest {

	@Autowired
	private payLogRepository log;
	@Test
	public void test() {
		String orderTradeNo = "2017070000000000005564";
		List<PayLogEsPO> poList = log.findByOrderTradeNoOrderByIdDesc(orderTradeNo);
		poList.forEach(po -> {
			System.out.println(po);
		});
		System.out.println(poList);
	}

}
