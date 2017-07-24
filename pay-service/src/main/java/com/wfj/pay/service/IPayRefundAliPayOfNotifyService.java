package com.wfj.pay.service;


import com.wfj.pay.po.RefundAliOffLineNotifyInfoPO;

/**
 * Created by wjg on 2017/7/3.
 */
public interface IPayRefundAliPayOfNotifyService {
    /**
     * 保存支付宝退款的通知报文
     *
     * @param refundAliOffLineNotifyInfoPO
     */
    void savePayRefundAliPayOfNotifyInfo(RefundAliOffLineNotifyInfoPO refundAliOffLineNotifyInfoPO);
}
