package com.wfj.pay.service.impl;

import com.wfj.pay.mapper.PayPartnerAccountMapper;
import com.wfj.pay.po.PayPartnerAccountPO;
import com.wfj.pay.service.IPayPartnerAccountServive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wjg on 2017/6/26.
 */
@Service
public class PayPartnerServiceImpl implements IPayPartnerAccountServive {
    @Autowired
    private PayPartnerAccountMapper partnerAccountMapper;
    @Override
    public PayPartnerAccountPO findPayPartnerAccoutById(Long id) {
        return partnerAccountMapper.selectById(id);
    }
}
