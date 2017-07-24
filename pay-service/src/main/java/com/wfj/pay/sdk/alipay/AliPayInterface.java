package com.wfj.pay.sdk.alipay;

/**
 * Created by kongqf on 2017/7/7.
 */
public enum AliPayInterface {
    AlipayTrade_Query(1, "alipay_trade_query_response", "统一收单线下交易查询"),
    AlipayTrade_Refund(2, "alipay_trade_refund_response", "统一收单交易退款接口"),
    AlipayTrade_Pay(3, "alipay_trade_pay_response", "统一收单交易支付接口"),
    AlipayTrade_Precreate(4, "alipay.trade.precreate", "统一收单线下交易预创建"),
    AlipayTrade_Cancel(5, "alipay.trade.cancel", "统一收单交易撤销接口"),
    AlipayTrade_Create(6, "alipay.trade.create", "统一收单交易创建接口"),
    AlipayTrade_FastpayRefundQuery(7, "alipay_trade_fastpay_refund_query_response", "统一收单交易退款查询"),
    AlipayTrade_Close(8, "alipay.trade.close", "统一收单交易关闭接口"),
    AlipayTrade_OrderSettle(9, "alipay.trade.close", "统一收单交易结算接口");

    AliPayInterface(Integer type, String method, String methodName) {
        this.type = type;
        this.method = method;
        this.methodName = methodName;
    }

    private Integer type;//交易类型
    private String method;//交易接口
    private String methodName;//交易接口名称

    public Integer getType() {
        return type;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMethod() {
        return method;
    }
}
