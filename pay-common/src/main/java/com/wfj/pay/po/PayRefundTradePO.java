/**
 * 
 */
package com.wfj.pay.po;

import java.io.Serializable;
import java.sql.Timestamp;

/** 
 * @author  ghost
 * @version 创建时间：2016年3月9日 上午11:36:09 
 * 类说明 		退款单表
 */
public class PayRefundTradePO implements Serializable{

	private static final long serialVersionUID = 2831329910484007063L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 商户退款单号码(作为分多次退款的依据)
	 */
	private String bpRefundOrderId;
	/**
	 * 支付平台
	 */
	private String refundTradeNo;
	/**
	 * 退款金额
	 */
	private Double refundFee;
	/**
	 * 退款订单的状态  1 初始   2 成功  3  失败
	 */
	private Long status;
	/**
	 * 创建时间
	 */
	private Timestamp createDate;
	/**
	 * 退款成功时间
	 */
	private Timestamp refundDate;
	/**
	 * 第三方渠道退款订单号
	 */
	private String refundSerialNumber;
	/**
	 * 退款批次
	 */
	private String batchNo;
	/**
	 * 支付平台正向支付订单号码
	 */
	private String orderTradeNo;
	/**
	 * 交易平台号
	 */
	private Long bpId;
	/**
	 * 优惠金额
	 */
	private double couponFee;

	
	public PayRefundTradePO() {
	}
	
	public PayRefundTradePO(Long id, String bpRefundOrderId,
			String refundTradeNo, Double refundFee, Long status,
			Timestamp createDate, Timestamp refundDate,
			String refundSerialNumber, String batchNo, String orderTradeNo, double couponFee) {
		super();
		this.id = id;
		this.bpRefundOrderId = bpRefundOrderId;
		this.refundTradeNo = refundTradeNo;
		this.refundFee = refundFee;
		this.status = status;
		this.createDate = createDate;
		this.refundDate = refundDate;
		this.refundSerialNumber = refundSerialNumber;
		this.batchNo = batchNo;
		this.orderTradeNo = orderTradeNo;
		this.couponFee = couponFee;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Double getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(Double refundFee) {
		this.refundFee = refundFee;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
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
	public String getOrderTradeNo() {
		return orderTradeNo;
	}
	public void setOrderTradeNo(String orderTradeNo) {
		this.orderTradeNo = orderTradeNo;
	}
	
	public Long getBpId() {
		return bpId;
	}

	public void setBpId(Long bpId) {
		this.bpId = bpId;
	}

	public double getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(double couponFee) {
		this.couponFee = couponFee;
	}

	@Override
	public String toString() {
		return "PayRefundTradePO [id=" + id + ", bpRefundOrderId="
				+ bpRefundOrderId + ", refundTradeNo=" + refundTradeNo
				+ ", refundFee=" + refundFee + ", status=" + status
				+ ", createDate=" + createDate + ", refundDate=" + refundDate
				+ ", refundSerialNumber=" + refundSerialNumber + ", batchNo="
				+ batchNo + ", orderTradeNo=" + orderTradeNo + ", bpId=" + bpId
				+ ", couponFee=" + couponFee + "]";
	}
}


