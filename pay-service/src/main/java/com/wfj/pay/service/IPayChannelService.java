package com.wfj.pay.service;

import com.wfj.pay.po.PayChannelPO;

/**
 * Created by wjg on 2017/6/23.
 */
public interface IPayChannelService {
    /**
     * 查找bpId对应的dicCode是否配置了收银台
     * @param payService
     * @param dicCode
     * @param clientType
     * @param bpId
     * @return
     */
    PayChannelPO findByCashierParams(Integer payService,String dicCode,String clientType,Long bpId);
}
