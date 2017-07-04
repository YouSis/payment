package com.wfj.pay.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by wjg on 2017/6/28.
 */
public class OrderCloseRequestDTO implements Serializable{

    private static final long serialVersionUID = 2183876685238809678L;
    @NotEmpty
    @Length(min = 1, max = 10)
    private String bpId;
    private String bpOrderId;
    private String orderTradeNo;
    @NotEmpty
    private String antiPhishingKey;
    @NotEmpty
    private String sign;

    public String getBpId() {
        return bpId;
    }

    public void setBpId(String bpId) {
        this.bpId = bpId;
    }

    public String getBpOrderId() {
        return bpOrderId;
    }

    public void setBpOrderId(String bpOrderId) {
        this.bpOrderId = bpOrderId;
    }

    public String getOrderTradeNo() {
        return orderTradeNo;
    }

    public void setOrderTradeNo(String orderTradeNo) {
        this.orderTradeNo = orderTradeNo;
    }

    public String getAntiPhishingKey() {
        return antiPhishingKey;
    }

    public void setAntiPhishingKey(String antiPhishingKey) {
        this.antiPhishingKey = antiPhishingKey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
