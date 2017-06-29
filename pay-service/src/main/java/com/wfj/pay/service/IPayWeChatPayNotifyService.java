package com.wfj.pay.service;

import com.wfj.pay.po.PayWeChatNotifyInfoPO;

/**
 * Created by wjg on 2017/6/28.
 */
public interface IPayWeChatPayNotifyService {
    /**
     * 保存微信支付成功之后的通知信息
     * @param payWeChatNotifyInfoPO
     */
    void saveWeChatPayNotify(PayWeChatNotifyInfoPO payWeChatNotifyInfoPO);
}
