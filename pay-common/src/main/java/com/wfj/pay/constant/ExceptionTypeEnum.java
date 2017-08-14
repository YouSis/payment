package com.wfj.pay.constant;

/**
 * Created by kongqf on 2017/7/25.
 */
public enum ExceptionTypeEnum {
    PAYORDER_SERVICE_TYPE("110", "支付订单"),
    REFUND_SERVICE_TYPE("120", "退款订单"),
    WECHATPAY_TYPE("130", "微信支付"),
    ALIPAY_TYPE("140", "支付宝支付"),
    SEND_PAY_DATA_TYPE("150","发送订单数据"),
    SEND_REFUND_DATA_TYPE("160","发送退款单数据");


    private String code;
    private String name;

    private ExceptionTypeEnum() {
    }

    private ExceptionTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getErrorCodeName(String code) {
        ExceptionTypeEnum[] ecs = ExceptionTypeEnum.values();
        for (ExceptionTypeEnum er : ecs) {
            if (er.getCode().equals(code)) {
                return er.getName();
            }
        }
        return null;
    }
}
