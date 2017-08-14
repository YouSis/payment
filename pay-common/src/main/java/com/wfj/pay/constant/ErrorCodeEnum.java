package com.wfj.pay.constant;

/**
 * Created by kongqf on 2017/7/25.
 */
public enum ErrorCodeEnum {
    /**
     * 错误编码规范： 长度：8位 前两位:系统 中间三位:业务 后三位:异常编码
     */
    PAY_ERROR("00110001","订单支付失败"),
    PAY_QUERY_ERROR("00110002","订单查询失败"),
    PAY_CLOSE_ERROR("00110003","订单关闭失败"),
    REFUND_ERROR("00120001","退款失败"),
    REFUND_QUERY_ERROR("00120002","退款查询失败"),
    WECHAT_ERROR("00130001","调用微信失败"),
    ALIPAY_ERROR("00140001","调用支付宝失败"),
    SEND_PAY_DATA_ERROR("00150001","发送订单数据到MQ失败"),
    SEND_REFUND_DATA_ERROR("00160001","发送退款数据到MQ失败");
    private String errorCode;
    private String errorCodeDes;


    private ErrorCodeEnum(String errorCode, String errorCodeDes) {
        this.errorCode = errorCode;
        this.errorCodeDes = errorCodeDes;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCodeDes() {
        return errorCodeDes;
    }

    public void setErrorCodeDes(String errorCodeDes) {
        this.errorCodeDes = errorCodeDes;
    }
}
