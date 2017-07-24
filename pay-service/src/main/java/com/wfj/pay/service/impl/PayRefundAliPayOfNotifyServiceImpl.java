package com.wfj.pay.service.impl;

import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.mapper.RefundAliOffLineNotifyInfoMapper;
import com.wfj.pay.po.RefundAliOffLineNotifyInfoPO;
import com.wfj.pay.service.IPayRefundAliPayOfNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wjg on 2017/7/3.
 */
@Service
public class PayRefundAliPayOfNotifyServiceImpl implements IPayRefundAliPayOfNotifyService {
    @Autowired
    private RefundAliOffLineNotifyInfoMapper refundAliOffLineNotifyInfoMapper;

    @Override
    @DataSource("master")
    @Transactional
    public void savePayRefundAliPayOfNotifyInfo(RefundAliOffLineNotifyInfoPO refundAliOffLineNotifyInfoPO) {
        refundAliOffLineNotifyInfoMapper.saveNotifyInfo(refundAliOffLineNotifyInfoPO);
    }
}
