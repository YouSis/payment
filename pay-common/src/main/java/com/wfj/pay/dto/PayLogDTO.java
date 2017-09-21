package com.wfj.pay.dto;

import java.sql.Timestamp;

/**
 * @description 业务日志DTO
 * @author liq
 * @date 2013-4-12 上午9:46:29
 */
public class PayLogDTO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4607809208167176575L;
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
	private String orderSerialNumber;
	/**
	 * 所属平台（peak、业务平台或第三方支付平台）.
	 */
	private String ownPlatform;
	/**
	 * 状态名称.
	 */
	private String statusName;
	/**
	 * 通信步骤.
	 */
	private int step;

	public PayLogDTO() {

	}

	public PayLogDTO(Long id, String content, Timestamp createDate,
			String status, String orderSerialNumber, String ownPlatform) {
		super();
		this.id = id;
		this.content = content;
		this.createDate = createDate;
		this.status = status;
		this.orderSerialNumber = orderSerialNumber;
		this.ownPlatform = ownPlatform;
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

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderSerialNumber() {
		return orderSerialNumber;
	}

	public void setOrderSerialNumber(String orderSerialNumber) {
		this.orderSerialNumber = orderSerialNumber;
	}

	public String getOwnPlatform() {
		return ownPlatform;
	}

	public void setOwnPlatform(String ownPlatform) {
		this.ownPlatform = ownPlatform;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

}
