package com.wfj.pay.constant;

import java.util.Arrays;

/**
 * Created by wjg on 2017/6/22.
 */
public enum PayOrderTerminalEnum {
    ONLIE("01"),
    WAP("02"),
    PAD("03"),
    POS("04");

    private String code;

    PayOrderTerminalEnum(String code) {
        this.code = code;
    }

    public static boolean isContain(String code){
        long count =   Arrays.stream(PayOrderTerminalEnum.values()).filter(payOrderTerminalEnum -> code.equals(payOrderTerminalEnum.getCode())).count();
        return count != 0;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
