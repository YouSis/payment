package com.wfj.pay.service;

import com.wfj.pay.po.PayPartnerAccountPO;

/**
 * Created by wjg on 2017/6/26.
 */
public interface IPayPartnerAccountServive {
    /**
     * 根据主键查找对应的账户信息
     * @param id
     * @return
     */
    PayPartnerAccountPO findPayPartnerAccoutById(Long id);
}
