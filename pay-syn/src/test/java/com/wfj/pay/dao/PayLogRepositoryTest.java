package com.wfj.pay.dao;

import com.wfj.pay.BootApplication;
import com.wfj.pay.po.PayTradePO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

/**
 * Created by wjg on 2017/7/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class PayLogRepositoryTest {
    @Autowired
    private PayLogRepository payLogRepository;
    @Test
    public void saveLog(){
        System.out.println(123);
    }
}
