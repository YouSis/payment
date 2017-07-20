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
public class PayTradeRepositoryTest {
    @Autowired
    private PayTradeRepository payTradeRepository;

    @Test
    public void savePayTrade(){
        //PayTradePO po = new PayTradePO();
        //po.setId(123l);
        //po.setBpId(10045l);
        //po.setOrderTradeNo("201700785734856");
        //po.setPayType("WECHATPAY_OFFLINE");
        //po.setTotalFee(13.34);
        //long time = System.currentTimeMillis();
        //po.setCreateDate(time);
        //po.setCreateDateFormat(new Timestamp(time));
        //payTradeRepository.save(po);
    }
}
