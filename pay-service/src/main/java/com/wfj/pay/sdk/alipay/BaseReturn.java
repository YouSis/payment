package com.wfj.pay.sdk.alipay;

/**
 * Created by kongqf on 2017/7/7.
 */
public class BaseReturn {
    String code;    //网关返回码,详见文档
    String msg;        //网关返回码描述,详见文档
    String subCode;    //业务返回码,详见文档
    String subMsg;    //业务返回码描述,详见文档
    String sign;    //必填签名,详见文档

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public void setSubMsg(String subMsg) {
        this.subMsg = subMsg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
