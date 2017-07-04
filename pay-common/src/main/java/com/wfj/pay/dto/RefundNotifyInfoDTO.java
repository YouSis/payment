package com.wfj.pay.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by wjg on 2017/7/3.
 */
public class RefundNotifyInfoDTO implements Serializable {
    private static final long serialVersionUID = 7229882300374377517L;
    private String refundTradeNo;
    private Timestamp refundDate;
    private String refundSerialNumber;
    private String batchNo;
    private double couponFee;
    private Long status;

    public String getRefundTradeNo() {
        return refundTradeNo;
    }

    public void setRefundTradeNo(String refundTradeNo) {
        this.refundTradeNo = refundTradeNo;
    }

    public Timestamp getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Timestamp refundDate) {
        this.refundDate = refundDate;
    }

    public String getRefundSerialNumber() {
        return refundSerialNumber;
    }

    public void setRefundSerialNumber(String refundSerialNumber) {
        this.refundSerialNumber = refundSerialNumber;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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
        final StringBuffer sb = new StringBuffer("RefundNotifyInfoDTO{");
        sb.append("refundTradeNo='").append(refundTradeNo).append('\'');
        sb.append(", refundDate=").append(refundDate);
        sb.append(", refundSerialNumber='").append(refundSerialNumber).append('\'');
        sb.append(", batchNo='").append(batchNo).append('\'');
        sb.append(", couponFee=").append(couponFee);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
