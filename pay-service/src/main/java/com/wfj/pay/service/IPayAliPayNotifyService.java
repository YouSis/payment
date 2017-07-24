package com.wfj.pay.service;

import com.wfj.pay.po.PayAliPayOffLineNotifyInfoPO;

/**
 * Created by kongqf on 2017/7/11.
 */
public interface IPayAliPayNotifyService {

    /**
     * 保存支付宝支付成功之后的通知信息
     *
     * @param payAlipayNotifyInfoPO
     */
    void saveAliPayNotify(PayAliPayOffLineNotifyInfoPO payAlipayNotifyInfoPO);
}
