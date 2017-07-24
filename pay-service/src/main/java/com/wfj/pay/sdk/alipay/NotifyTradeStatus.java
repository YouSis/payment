package com.wfj.pay.sdk.alipay;

/**
 * Created by kongqf on 2017/7/10.
 */
public enum NotifyTradeStatus {
    WAIT_BUYER_PAY("WAIT_BUYER_PAY", "等待买家付款"),//	交易创建，等待买家付款
    TRADE_CLOSED("TRADE_CLOSED", "该订单已关闭或已退款"),//	未付款交易超时关闭，或支付完成后全额退款
    TRADE_SUCCESS("TRADE_SUCCESS", "支付成功"),//交易支付成功
    TRADE_FINISHED("TRADE_FINISHED", "交易结束，不可退款");//	交易结束，不可退款

    NotifyTradeStatus(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    private String status; //状态
    private String statusName; //状态描述

    public String getStatus() {
        return status;
    }

    public String getStatusName() {
        return statusName;
    }
}
