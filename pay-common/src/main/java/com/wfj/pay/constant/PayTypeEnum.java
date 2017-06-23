package com.wfj.pay.constant;

import java.util.Arrays;

/**
 * Created by wjg on 2017/6/22.
 */
public enum PayTypeEnum {
    ALIPAY_OFFLINE("ALIPAY_OFFLINE"),
    WECHATPAY_OFFLINE("WECHATPAY_OFFLINE");

    private String payType;

    PayTypeEnum(String payType) {
        this.payType = payType;
    }

    public static boolean isContain(String code){
        long count =   Arrays.stream(PayTypeEnum.values()).filter(payTypeEnum -> code.equals(payTypeEnum.getPayType())).count();
        return count != 0;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
