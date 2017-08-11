package com.wfj.pay.mapper;

import com.wfj.pay.BootApplication;
import com.wfj.pay.po.PayWeChatNotifyInfoPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by wjg on 2017/7/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class PayWeChatPayNotifyMapperTest {
    @Autowired
    private PayWeChatPayNotifyMapper weChatPayNotifyMapper;
    @Test
    public void insert() throws Exception {
        PayWeChatNotifyInfoPO weChatNotifyInfoPO = new PayWeChatNotifyInfoPO();
        weChatNotifyInfoPO.setAppid("123");
        weChatNotifyInfoPO.setAttach("123");
        weChatNotifyInfoPO.setBank_type("123");
        weChatNotifyInfoPO.setCash_fee("123");
        weChatNotifyInfoPO.setCash_fee_type("123");
        weChatNotifyInfoPO.setCoupon_count("1");
        weChatNotifyInfoPO.setCouponFee("123");
        weChatNotifyInfoPO.setDevice_info("123");
        weChatNotifyInfoPO.setErr_code("123");
        weChatNotifyInfoPO.setOpenid("123");
        weChatNotifyInfoPO.setTotal_fee("123");
        weChatPayNotifyMapper.insert(weChatNotifyInfoPO);

    }
}