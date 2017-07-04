package com.wfj.pay.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by wjg on 2017/6/28.
 */
public class RefundOrderQueryRequestDTO implements Serializable{

    private static final long serialVersionUID = 9215614687684150639L;
    @NotEmpty
    @Length(min = 1, max = 10)
    private String bpId;
    private String bpRefundOrderId;
    private String orderTradeNo;
    private String payType;

    public String getBpId() {
        return bpId;
    }

    public void setBpId(String bpId) {
        this.bpId = bpId;
    }

    public String getBpRefundOrderId() {
        return bpRefundOrderId;
    }

    public void setBpRefundOrderId(String bpRefundOrderId) {
        this.bpRefundOrderId = bpRefundOrderId;
    }

    public String getOrderTradeNo() {
        return orderTradeNo;
    }

    public void setOrderTradeNo(String orderTradeNo) {
        this.orderTradeNo = orderTradeNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RefundOrderQueryRequestDTO{");
        sb.append("bpId='").append(bpId).append('\'');
        sb.append(", bpRefundOrderId='").append(bpRefundOrderId).append('\'');
        sb.append(", orderTradeNo='").append(orderTradeNo).append('\'');
        sb.append(", payType='").append(payType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
