package com.wfj.pay.service.impl;

import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.mapper.PayTradeMapper;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.service.IPayTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wjg on 2017/6/23.
 */
@Service
public class PayTradeServiceImpl implements IPayTradeService {
    @Autowired
    private PayTradeMapper payTradeMapper;
    @Override
    @DataSource("slave")
    public PayTradePO findByBpIdAndBpOrderId(Long bpId, String bpOrderId) {
        return payTradeMapper.selectByBpIdAndBpOrderId(bpId,bpOrderId);
    }
}
