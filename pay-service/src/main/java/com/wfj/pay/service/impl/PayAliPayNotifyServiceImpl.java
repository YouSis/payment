package com.wfj.pay.service.impl;

import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.mapper.PayAliPayOffLineNotifyInfoMapper;
import com.wfj.pay.po.PayAliPayOffLineNotifyInfoPO;
import com.wfj.pay.service.IPayAliPayNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kongqf on 2017/7/11.
 */
@Service
public class PayAliPayNotifyServiceImpl implements IPayAliPayNotifyService {

    @Autowired
    private PayAliPayOffLineNotifyInfoMapper payAliPayOffLineNotifyInfoMapper;

    @DataSource("master")
    @Transactional
    @Override
    public void saveAliPayNotify(PayAliPayOffLineNotifyInfoPO payAlipayNotifyInfoPO) {
        payAliPayOffLineNotifyInfoMapper.saveNotifyInfo(payAlipayNotifyInfoPO);
    }
}
