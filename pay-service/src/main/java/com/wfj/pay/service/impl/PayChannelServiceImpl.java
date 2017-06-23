package com.wfj.pay.service.impl;

import com.wfj.pay.mapper.PayChannelMapper;
import com.wfj.pay.po.PayChannelPO;
import com.wfj.pay.service.IPayChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wjg on 2017/6/23.
 */
@Service
public class PayChannelServiceImpl implements IPayChannelService {
    @Autowired
    private PayChannelMapper payChannelMapper;

    @Override
    public PayChannelPO findByCashierParams(Integer payService, String dicCode, String clientType, Long bpId) {
        return payChannelMapper.getPayChannelByBPDicCodePayService(bpId, dicCode, clientType, payService);
    }
}
