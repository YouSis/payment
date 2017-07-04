package com.wfj.pay.service;

import com.wfj.pay.po.PayRefundWeChatOfNotifyInfoPO;

/**
 * Created by wjg on 2017/7/3.
 */
public interface IPayRefundWeChatOfNotifyService {
    /**
     * 保存微信退款的通知报文
     * @param refundWeChatOfNotifyInfoPO
     */
    void savePayRefundWeChatOfNotifyInfo(PayRefundWeChatOfNotifyInfoPO refundWeChatOfNotifyInfoPO);
}
