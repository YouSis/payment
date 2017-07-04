package com.wfj.pay.dto;

import java.io.Serializable;

/**
 * Created by wjg on 2017/6/30.
 */
public class RefundOrderResponseDTO implements Serializable {
    private static final long serialVersionUID = 7626313058983422630L;

    private String resultCode;
    private String resultMsg;
    private String errDetail;

    private String bpId;
    private String bpOrderId;
    private String totalFee;
    private String couponFee;
    private String orderTradeNo;
    private String payMediumCode;
    private String bpRefundOrderId;
    private String refundTradeNo;
    private String refundFee;
    private String batchNo;
    private String transactionId;
    private String sign;

    public RefundOrderResponseDTO() {
    }

    public RefundOrderResponseDTO(String resultCode, String resultMsg, String errDetail) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.errDetail = errDetail;
    }

    public RefundOrderResponseDTO(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getErrDetail() {
        return errDetail;
    }

    public void setErrDetail(String errDetail) {
        this.errDetail = errDetail;
    }

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

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(String couponFee) {
        this.couponFee = couponFee;
    }

    public String getOrderTradeNo() {
        return orderTradeNo;
    }

    public void setOrderTradeNo(String orderTradeNo) {
        this.orderTradeNo = orderTradeNo;
    }

    public String getPayMediumCode() {
        return payMediumCode;
    }

    public void setPayMediumCode(String payMediumCode) {
        this.payMediumCode = payMediumCode;
    }

    public String getBpRefundOrderId() {
        return bpRefundOrderId;
    }

    public void setBpRefundOrderId(String bpRefundOrderId) {
        this.bpRefundOrderId = bpRefundOrderId;
    }

    public String getRefundTradeNo() {
        return refundTradeNo;
    }

    public void setRefundTradeNo(String refundTradeNo) {
        this.refundTradeNo = refundTradeNo;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RefundOrderResponseDTO{");
        sb.append("resultCode='").append(resultCode).append('\'');
        sb.append(", resultMsg='").append(resultMsg).append('\'');
        sb.append(", errDetail='").append(errDetail).append('\'');
        sb.append(", bpId='").append(bpId).append('\'');
        sb.append(", bpOrderId='").append(bpOrderId).append('\'');
        sb.append(", totalFee='").append(totalFee).append('\'');
        sb.append(", couponFee='").append(couponFee).append('\'');
        sb.append(", orderTradeNo='").append(orderTradeNo).append('\'');
        sb.append(", payMediumCode='").append(payMediumCode).append('\'');
        sb.append(", bpRefundOrderId='").append(bpRefundOrderId).append('\'');
        sb.append(", refundTradeNo='").append(refundTradeNo).append('\'');
        sb.append(", refundFee='").append(refundFee).append('\'');
        sb.append(", batchNo='").append(batchNo).append('\'');
        sb.append(", transactionId='").append(transactionId).append('\'');
        sb.append(", sign='").append(sign).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
