package com.wfj.pay.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Digits;
import java.io.Serializable;

/**
 * Created by wjg on 2017/6/30.
 */
public class RefundOrderRequestDTO implements Serializable {
    private static final long serialVersionUID = 8917681557877112100L;
    @NotEmpty
    @Length(min = 1, max = 10)
    private String bpId;
    @NotEmpty
    private String sign;
    private String bpOrderId;
    private String orderTradeNo;
    @NotEmpty
    @Length(min = 1, max = 100)
    private String bpRefundOrderId;
    private String payType;
    @NotEmpty
    @Digits(integer = 8,fraction = 2)
    private String refundFee;
    @NotEmpty
    private String antiPhishingKey;


    public String getBpId() {
        return bpId;
    }

    public void setBpId(String bpId) {
        this.bpId = bpId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
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

    public String getBpRefundOrderId() {
        return bpRefundOrderId;
    }

    public void setBpRefundOrderId(String bpRefundOrderId) {
        this.bpRefundOrderId = bpRefundOrderId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee;
    }

    public String getAntiPhishingKey() {
        return antiPhishingKey;
    }

    public void setAntiPhishingKey(String antiPhishingKey) {
        this.antiPhishingKey = antiPhishingKey;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RefundOrderRequestDTO{");
        sb.append("bpId='").append(bpId).append('\'');
        sb.append(", sign='").append(sign).append('\'');
        sb.append(", bpOrderId='").append(bpOrderId).append('\'');
        sb.append(", orderTradeNo='").append(orderTradeNo).append('\'');
        sb.append(", bpRefundOrderId='").append(bpRefundOrderId).append('\'');
        sb.append(", payType='").append(payType).append('\'');
        sb.append(", refundFee='").append(refundFee).append('\'');
        sb.append(", antiPhishingKey='").append(antiPhishingKey).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
