package com.wfj.pay.constant;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by wjg on 2017/6/22.
 */
public enum PayTypeEnum {
    ALIPAY_OFFLINE("ALIPAY_OFFLINE","13103"),
    WECHATPAY_OFFLINE("WECHATPAY_OFFLINE","13101");

    private String payType;
    private String mediumCode;

    PayTypeEnum(String payType, String mediumCode) {
        this.payType = payType;
        this.mediumCode = mediumCode;
    }

    public static boolean isContain(String code){
        long count =   Arrays.stream(PayTypeEnum.values()).filter(payTypeEnum -> code.equals(payTypeEnum.getPayType())).count();
        return count != 0;
    }

    public static String getMedium(String payType){
        Optional<PayTypeEnum> typeEnum = Arrays.stream(PayTypeEnum.values()).filter(payTypeEnum -> payType.equals(payTypeEnum.getPayType())).findFirst();
        return typeEnum.map(PayTypeEnum::getMediumCode).orElse(null);
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getMediumCode() {
        return mediumCode;
    }

    public void setMediumCode(String mediumCode) {
        this.mediumCode = mediumCode;
    }
}
