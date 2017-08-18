package com.wfj.pay.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class BusinessDTO implements Serializable {

	private static final long serialVersionUID = -6666353703998070306L;

	private Integer pageNo;
	private Integer pageSize;

	/**
	 * 分配给业务平台的ID.
	 */
	private Long id;
	/**
	 * 分配给业务平台的密匙.
	 */
	private String bpKey;
	/**
	 * 业务平台名称.
	 */
	private String bpName;
	/**
	 * 业务平台描述.
	 */
	private String description;
	/**
	 * 最后修改时间.
	 */
	private Timestamp lastDate;
	/**
	 * 创建时间.
	 */
	private Timestamp createDate;
	/**
	 * 状态.
	 */
	private String status;
	/**
	 * 异步通知地址.
	 */
	private String notifyUrl;
	/**
	 * 同步通知地址.
	 */
	private String redirectUrl;
	/**
	 * 手机端异步通知地址.
	 */
	private String mobileNotifyUrl;
	/**
	 * 手机端同步通知地址.
	 */
	private String mobileRedirectUrl;

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBpKey() {
		return bpKey;
	}

	public void setBpKey(String bpKey) {
		this.bpKey = bpKey;
	}

	public String getBpName() {
		return bpName;
	}

	public void setBpName(String bpName) {
		this.bpName = bpName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getLastDate() {
		return lastDate;
	}

	public void setLastDate(Timestamp lastDate) {
		this.lastDate = lastDate;
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

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getMobileNotifyUrl() {
		return mobileNotifyUrl;
	}

	public void setMobileNotifyUrl(String mobileNotifyUrl) {
		this.mobileNotifyUrl = mobileNotifyUrl;
	}

	public String getMobileRedirectUrl() {
		return mobileRedirectUrl;
	}

	public void setMobileRedirectUrl(String mobileRedirectUrl) {
		this.mobileRedirectUrl = mobileRedirectUrl;
	}

	@Override
	public String toString() {
		return "BusinessDTO [pageNo=" + pageNo + ", pageSize=" + pageSize + ", id=" + id + ", bpKey=" + bpKey
				+ ", bpName=" + bpName + ", description=" + description + ", lastDate=" + lastDate + ", createDate="
				+ createDate + ", status=" + status + ", notifyUrl=" + notifyUrl + ", redirectUrl=" + redirectUrl
				+ ", mobileNotifyUrl=" + mobileNotifyUrl + ", mobileRedirectUrl=" + mobileRedirectUrl + "]";
	}

}
