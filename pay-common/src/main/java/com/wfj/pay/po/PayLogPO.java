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
public class PayLogPO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4096976662725589829L;
	/**
	 * ID.
	 */
	private Long id;
	/**
	 * 内容.
	 */
	private String content;
	/**
	 * 创建时间.
	 */
	private Timestamp createDate;
	/**
	 * 状态.
	 */
	private String status;
	/**
	 * 订单流水号.
	 */
	private String orderTradeNo;
	/**
	 * 所属平台（peak、业务平台或第三方支付平台）.
	 */
	private String ownPlatform;

	// Constructors

	/** default constructor */
	public PayLogPO() {
	}

	/** minimal constructor */
	public PayLogPO(Long id) {
		this.id = id;
	}

	/** full constructor */
	public PayLogPO(Long id, String content, Timestamp createDate,
			String status, String orderTradeNo, String ownPlatform) {
		this.id = id;
		this.content = content;
		this.createDate = createDate;
		this.status = status;
		this.orderTradeNo = orderTradeNo;
		this.ownPlatform = ownPlatform;
	}

	// Property accessors

	/**
	 * 取得ID.
	 * 
	 * @return id ID
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * 设置ID.
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 取得内容.
	 * 
	 * @return content 内容
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 设置内容.
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 取得创建时间.
	 * 
	 * @return createDate 创建时间
	 */
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	/**
	 * 设置创建时间.
	 * 
	 * @param createDate
	 *            创建时间
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/**
	 * 取得状态.
	 * 
	 * @return status 状态
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置状态.
	 * 
	 * @param status
	 *            状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 取得订单流水号.
	 * 
	 * @return orderSerialNumber 订单流水号
	 */
	public String getOrderTradeNo() {
		return this.orderTradeNo;
	}

	/**
	 * 设置订单流水号.
	 * 
	 * @param orderSerialNumber
	 *            支付平台订单流水号
	 */
	public void setOrderTradeNo(String orderTradeNo) {
		this.orderTradeNo = orderTradeNo;
	}

	/**
	 * 取得所属平台（peak、业务平台或第三方支付平台）.
	 * 
	 * @return ownPlatform 所属平台（peak、业务平台或第三方支付平台）
	 */
	public String getOwnPlatform() {
		return this.ownPlatform;
	}

	/**
	 * 设置所属平台（peak、业务平台或第三方支付平台）.
	 * 
	 * @param ownPlatform
	 *            所属平台（peak、业务平台或第三方支付平台）
	 */
	public void setOwnPlatform(String ownPlatform) {
		this.ownPlatform = ownPlatform;
	}

}