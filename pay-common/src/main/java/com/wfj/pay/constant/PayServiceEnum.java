package com.wfj.pay.constant;

import java.util.Arrays;

/**
 * Created by wjg on 2017/6/22.
 */
public enum PayServiceEnum {
    ONLIE_BANK("1"),
    THIRD_CHANNEL("2"),
    BANK_DIRECT("3");

    private String code;

    PayServiceEnum(String code) {
        this.code = code;
    }

    public static boolean isContain(String code){
        long count =   Arrays.stream(PayServiceEnum.values()).filter(payServiceEnum -> code.equals(payServiceEnum.getCode())).count();
        return count != 0;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
