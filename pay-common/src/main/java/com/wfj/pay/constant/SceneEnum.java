package com.wfj.pay.constant;

/**
 * Created by kongqf on 2017/7/7.
 */
public enum SceneEnum {
    //scene	条码支付固定传入bar_code ----------------支付场景 条码支付，取值：bar_code 声波支付，取值：wave_code
    BarCode("bar_code", "条码支付"),
    WaveCode("wave_code", "声波支付");

    SceneEnum(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    private String type;//交易接口
    private String typeName;//交易接口名称

    public String getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }
}
