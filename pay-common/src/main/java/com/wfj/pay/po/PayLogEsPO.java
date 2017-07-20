package com.wfj.pay.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.Timestamp;

/**
 * 
 * 类说明 : 支付日志表POJO.
 */
@Document(indexName = "pay-data",type = "pay-log")
public class PayLogEsPO implements java.io.Serializable {

	private static final long serialVersionUID = -1145702053575834999L;
	/**
	 * ID.
	 */
	@Id
	private Long id;
	/**
	 * 内容.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String content;
	/**
	 * 创建时间.
	 */
	@Field(type = FieldType.Date,index = FieldIndex.not_analyzed)
	private Timestamp createDateTime;
	/**
	 * 状态.
	 */
	@Field(type = FieldType.Long,index = FieldIndex.not_analyzed)
	private Long status;
	/**
	 * 订单流水号.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String orderTradeNo;
	/**
	 * 所属平台（peak、业务平台或第三方支付平台）.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String ownPlatform;

	// Constructors

	/** default constructor */
	public PayLogEsPO() {
	}

	/** minimal constructor */
	public PayLogEsPO(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getOrderTradeNo() {
		return orderTradeNo;
	}

	public void setOrderTradeNo(String orderTradeNo) {
		this.orderTradeNo = orderTradeNo;
	}

	public String getOwnPlatform() {
		return ownPlatform;
	}

	public void setOwnPlatform(String ownPlatform) {
		this.ownPlatform = ownPlatform;
	}
}