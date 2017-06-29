package com.wfj.pay.service.impl;

import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.mapper.PayWeChatPayNotifyMapper;
import com.wfj.pay.po.PayWeChatNotifyInfoPO;
import com.wfj.pay.service.IPayWeChatPayNotifyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wjg on 2017/6/28.
 */
@Service
public class PayWeChatPayNotifyServiceImpl implements IPayWeChatPayNotifyService {
    private PayWeChatPayNotifyMapper payWeChatPayNotifyMapper;
    @Override
    @DataSource("master")
    @Transactional
    public void saveWeChatPayNotify(PayWeChatNotifyInfoPO payWeChatNotifyInfoPO) {
        payWeChatPayNotifyMapper.insert(payWeChatNotifyInfoPO);
    }
}
