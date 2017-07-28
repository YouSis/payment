package com.wfj.pay.constant;

/**
 * Created by kongqf on 2017/7/25.
 */
public enum ExceptionTypeEnum {
    PAYORDER_SERVICE_TYPE("110","支付订单"),
    PAYREFUND_SERVICE_TYPE("149","退款订单"),
    PAYOFFLINE_SAVE_BACKPARAMETERS_TYPE("155","第三方支付平台返回信息保存DB"),
    TO_PAY_TYPE("140", "支付请求"),
    ANTIPHISHING_TIMESTAMP_CHECK("177","防钓鱼时间错校验"),
    LOAD_REDIS("178","加载redis"),
    SETTINGS_REDIS("179","设置redis"),
    BUILD_TEMPLATE_MSG("180","微信支付成功后构建模板消息"),
    SEND_MQ("208","发送至MQ");

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
