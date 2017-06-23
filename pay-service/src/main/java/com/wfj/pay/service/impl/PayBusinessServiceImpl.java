package com.wfj.pay.service.impl;

import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.mapper.PayBusinessMapper;
import com.wfj.pay.po.PayBusinessPO;
import com.wfj.pay.service.IPayBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wjg on 2017/6/23.
 */
@Service
public class PayBusinessServiceImpl implements IPayBusinessService {
    @Autowired
    private PayBusinessMapper businessMapper;
    @Override
    @DataSource("slave")
    public PayBusinessPO findByBpId(Long bpId) {
        return businessMapper.selectByBpId(bpId);
    }
}
