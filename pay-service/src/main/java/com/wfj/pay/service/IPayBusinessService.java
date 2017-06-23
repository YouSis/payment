package com.wfj.pay.service;

import com.wfj.pay.po.PayBusinessPO;

/**
 * Created by wjg on 2017/6/23.
 */
public interface IPayBusinessService {
    PayBusinessPO findByBpId(Long bpId);
}
