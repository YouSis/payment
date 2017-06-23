package com.wfj.pay.service;

import com.wfj.pay.po.PayTradePO;

/**
 * Created by wjg on 2017/6/23.
 */
public interface IPayTradeService {
    PayTradePO findByBpIdAndBpOrderId(Long bpId,String bpOrderId);
}
