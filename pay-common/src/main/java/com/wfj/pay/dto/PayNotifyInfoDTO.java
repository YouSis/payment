package com.wfj.pay.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by wjg on 2017/6/28.
 */
public class PayNotifyInfoDTO implements Serializable {

    private static final long serialVersionUID = -8341051819455450800L;
    /**
     * 支付平台交易号
     */
    private String orderTradeNo;
    /**
     * 第三方交易流水号
     */
    private String paySerialNumber;
    /**
     * 第三方支付通知时间时间戳
     */
    private Long platformPayDate;
    /**
     * 第三方支付通知时间
     */
    private Timestamp platformPayFormat;
    /**
     * 优惠金额
     */
    private double couponFee;
    /**
     * 支付状态
     */
    private Long status;


    public String getOrderTradeNo() {
        return orderTradeNo;
    }

    public void setOrderTradeNo(String orderTradeNo) {
        this.orderTradeNo = orderTradeNo;
    }

    public String getPaySerialNumber() {
        return paySerialNumber;
    }

    public void setPaySerialNumber(String paySerialNumber) {
        this.paySerialNumber = paySerialNumber;
    }

    public Long getPlatformPayDate() {
        return platformPayDate;
    }

    public void setPlatformPayDate(Long platformPayDate) {
        this.platformPayDate = platformPayDate;
    }

    public Timestamp getPlatformPayFormat() {
        return platformPayFormat;
    }

    public void setPlatformPayFormat(Timestamp platformPayFormat) {
        this.platformPayFormat = platformPayFormat;
    }

    public double getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(double couponFee) {
        this.couponFee = couponFee;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PayNotifyInfoDTO{");
        sb.append("orderTradeNo='").append(orderTradeNo).append('\'');
        sb.append(", paySerialNumber='").append(paySerialNumber).append('\'');
        sb.append(", platformPayDate=").append(platformPayDate);
        sb.append(", platformPayFormat=").append(platformPayFormat);
        sb.append(", couponFee=").append(couponFee);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
