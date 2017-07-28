package com.wfj.pay.constant;

/**
 * Created by kongqf on 2017/7/25.
 */
public enum ErrorCodeEnum {
    /**
     * 错误编码规范： 长度：8位 前两位:系统 中间三位:业务 后三位:异常编码
     */
    SAVE_PAYORDER_ERROR("00110001","保存订单失败"),
    SAVE_PAYORDER_ERROR1("00110002","保存订单失败");
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
