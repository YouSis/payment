package com.wfj.pay.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wfj.pay.BootApplication;
import com.wfj.pay.service.ESDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class ESDataServiceImplTest {

	@Autowired
	private ESDataService service;
	@Test
	public void test() {
		service.tradeToES();
	}

}
