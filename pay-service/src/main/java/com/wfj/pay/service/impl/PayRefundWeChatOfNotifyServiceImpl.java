package com.wfj.pay.service.impl;

import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.mapper.PayRefundWeChatOfNotifyInfoMapper;
import com.wfj.pay.po.PayRefundWeChatOfNotifyInfoPO;
import com.wfj.pay.service.IPayRefundWeChatOfNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wjg on 2017/7/3.
 */
@Service
public class PayRefundWeChatOfNotifyServiceImpl implements IPayRefundWeChatOfNotifyService{
    @Autowired
    private PayRefundWeChatOfNotifyInfoMapper refundWeChatOfNotifyInfoMapper;
    @Override
    @DataSource("master")
    @Transactional
    public void savePayRefundWeChatOfNotifyInfo(PayRefundWeChatOfNotifyInfoPO refundWeChatOfNotifyInfoPO) {
        refundWeChatOfNotifyInfoMapper.insert(refundWeChatOfNotifyInfoPO);
    }
}
