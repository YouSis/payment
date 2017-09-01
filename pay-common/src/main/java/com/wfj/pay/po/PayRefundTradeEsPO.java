/**
 * 
 */
package com.wfj.pay.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.sql.Timestamp;

/** 
 * @author  ghost
 * @version 创建时间：2016年3月9日 上午11:36:09 
 * 类说明 		退款单表
 */
@Document(indexName = "pay-data",type = "pay-refund-trade")
public class PayRefundTradeEsPO implements Serializable{

	private static final long serialVersionUID = 2831329910484007063L;
	/**
	 * 主键
	 */
	@Id
	private Long id;
	/**
	 * 商户退款单号码(作为分多次退款的依据)
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String bpRefundOrderId;
	/**
	 * 支付平台
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String refundTradeNo;
	/**
	 * 退款金额
	 */
	@Field(type = FieldType.Double,index = FieldIndex.not_analyzed)
	private Double refundFee;
	/**
	 * 退款订单的状态  1 初始   2 成功  3  失败
	 */
	@Field(type = FieldType.Long,index = FieldIndex.not_analyzed)
	private Long status;
	/**
	 * 创建时间
	 */
	@Field(type = FieldType.Date,index = FieldIndex.not_analyzed)
	private Timestamp createDateTime;
	/**
	 * 退款成功时间
	 */
	@Field(type = FieldType.Date,index = FieldIndex.not_analyzed)
	private Timestamp refundDate;
	/**
	 * 第三方渠道退款订单号
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String refundSerialNumber;
	/**
	 * 退款批次
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String batchNo;
	/**
	 * 支付平台正向支付订单号码
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String orderTradeNo;
	/**
	 * 交易平台号
	 */
	@Field(type = FieldType.Long,index = FieldIndex.not_analyzed)
	private Long bpId;
	/**
	 * 优惠金额
	 */
	@Field(type = FieldType.Double,index = FieldIndex.not_analyzed)
	private double couponFee;
	/**
	 * 支付方式
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String payType;
	/**
	 * 门店号码
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String merCode;
	/**
	 *正向订单的创建时间
	 */
	@Field(type = FieldType.Date,index = FieldIndex.not_analyzed)
	private Timestamp createOrderTime;


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

	public Timestamp getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public Timestamp getCreateOrderTime() {
		return createOrderTime;
	}

	public void setCreateOrderTime(Timestamp createOrderTime) {
		this.createOrderTime = createOrderTime;
	}
}


