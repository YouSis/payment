package com.wfj.pay.dto;

import java.io.Serializable;

/**
 * Created by wjg on 2017/6/22.
 */
public class OrderResponseDTO implements Serializable{
    private static final long serialVersionUID = -1009017617586265037L;

    private String resultCode;
    private String resultMsg;
    private String errDetail;

    private String tradeStatus;
    private String sign;
    private String bpOrderId;
    private String totalFee;
    private String couponFee;
    private String orderTradeNo;
    private String paySerialNumber;
    private Long payDate;
    private String payType;
    private String payMediumCode;
    private String channelFeeCostDetail;
    private String platformFeeCostDetail;
    private String refundDetail;
    public OrderResponseDTO(String resultCode, String resultMsg, String errDetail) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.errDetail = errDetail;
    }

    public OrderResponseDTO() {
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

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
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

    public String getPaySerialNumber() {
        return paySerialNumber;
    }

    public void setPaySerialNumber(String paySerialNumber) {
        this.paySerialNumber = paySerialNumber;
    }

    public Long getPayDate() {
        return payDate;
    }

    public void setPayDate(Long payDate) {
        this.payDate = payDate;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayMediumCode() {
        return payMediumCode;
    }

    public void setPayMediumCode(String payMediumCode) {
        this.payMediumCode = payMediumCode;
    }

    public String getChannelFeeCostDetail() {
        return channelFeeCostDetail;
    }

    public void setChannelFeeCostDetail(String channelFeeCostDetail) {
        this.channelFeeCostDetail = channelFeeCostDetail;
    }

    public String getPlatformFeeCostDetail() {
        return platformFeeCostDetail;
    }

    public void setPlatformFeeCostDetail(String platformFeeCostDetail) {
        this.platformFeeCostDetail = platformFeeCostDetail;
    }

    public String getRefundDetail() {
        return refundDetail;
    }

    public void setRefundDetail(String refundDetail) {
        this.refundDetail = refundDetail;
    }
}
