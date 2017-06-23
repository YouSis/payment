package com.wfj.pay.po;

import java.sql.Timestamp;

/**
 * 
 * @author admin
 *
 */
public class PayBusinessPO implements java.io.Serializable {


	private static final long serialVersionUID = 904794283119954420L;
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

	// Constructors

	/** default constructor */
	public PayBusinessPO() {
	}

	/** minimal constructor */
	public PayBusinessPO(Long id) {
		this.id = id;
	}

	/** full constructor */
	public PayBusinessPO(Long id, String bpKey, String bpName,
			String description, Timestamp lastDate, Timestamp createDate,
			String status, String notifyUrl, String redirectUrl,
			String mobileNotifyUrl, String mobileRedirectUrl) {
		this.id = id;
		this.bpKey = bpKey;
		this.bpName = bpName;
		this.description = description;
		this.lastDate = lastDate;
		this.createDate = createDate;
		this.status = status;
		this.notifyUrl = notifyUrl;
		this.redirectUrl = redirectUrl;
		this.mobileNotifyUrl = mobileNotifyUrl;
		this.mobileRedirectUrl = mobileRedirectUrl;
	}

	// Property accessors

	/**
	 * 取得分配给业务平台的ID.
	 * 
	 * @return id 分配给业务平台的ID
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * 设置分配给业务平台的ID.
	 * 
	 * @param id
	 *            分配给业务平台的ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 取得分配给业务平台的密匙.
	 * 
	 * @return bpKey 分配给业务平台的密匙
	 */
	public String getBpKey() {
		return this.bpKey;
	}

	/**
	 * 设置分配给业务平台的密匙.
	 * 
	 * @param bpKey
	 *            分配给业务平台的密匙
	 */
	public void setBpKey(String bpKey) {
		this.bpKey = bpKey;
	}

	/**
	 * 取得业务平台名称.
	 * 
	 * @return bpName 业务平台名称
	 */
	public String getBpName() {
		return this.bpName;
	}

	/**
	 * 设置业务平台名称.
	 * 
	 * @param bpName
	 *            业务平台名称
	 */
	public void setBpName(String bpName) {
		this.bpName = bpName;
	}

	/**
	 * 取得业务平台描述.
	 * 
	 * @return description 业务平台描述
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 设置业务平台描述.
	 * 
	 * @param description
	 *            业务平台描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 取得最后修改时间.
	 * 
	 * @return lastDate 最后修改时间
	 */
	public Timestamp getLastDate() {
		return this.lastDate;
	}

	/**
	 * 设置最后修改时间.
	 * 
	 * @param lastDate
	 *            最后修改时间
	 */
	public void setLastDate(Timestamp lastDate) {
		this.lastDate = lastDate;
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
	 * 取得异步通知地址.
	 * 
	 * @return notifyUrl 异步通知地址
	 */
	public String getNotifyUrl() {
		return this.notifyUrl;
	}

	/**
	 * 设置异步通知地址.
	 * 
	 * @param notifyUrl
	 *            异步通知地址
	 */
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	/**
	 * 取得同步通知地址.
	 * 
	 * @return redirectUrl 同步通知地址
	 */
	public String getRedirectUrl() {
		return this.redirectUrl;
	}

	/**
	 * 设置同步通知地址.
	 * 
	 * @param redirectUrl
	 *            同步通知地址
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	/**
	 * @return 手机端异步通知地址.
	 */
	public String getMobileNotifyUrl() {
		return mobileNotifyUrl;
	}

	/**
	 * @param mobileNotifyUrl
	 *            手机端异步通知地址
	 */
	public void setMobileNotifyUrl(String mobileNotifyUrl) {
		this.mobileNotifyUrl = mobileNotifyUrl;
	}

	/**
	 * @return 手机端同步通知地址.
	 */
	public String getMobileRedirectUrl() {
		return mobileRedirectUrl;
	}

	/**
	 * @param mobileRedirectUrl
	 *            手机端同步通知地址
	 */
	public void setMobileRedirectUrl(String mobileRedirectUrl) {
		this.mobileRedirectUrl = mobileRedirectUrl;
	}
	
    /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#toString()
         */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PayBusinessPO [id=");
		builder.append(id);
		builder.append(", bpKey=");
		builder.append(bpKey);
		builder.append(", bpName=");
		builder.append(bpName);
		builder.append(", description=");
		builder.append(description);
		builder.append(", lastDate=");
		builder.append(lastDate);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", status=");
		builder.append(status);
		builder.append(", notifyUrl=");
		builder.append(notifyUrl);
		builder.append(", redirectUrl=");
		builder.append(redirectUrl);
		builder.append(", mobileNotifyUrl=");
		builder.append(mobileNotifyUrl);
		builder.append(", mobileRedirectUrl=");
		builder.append(mobileRedirectUrl);
		builder.append("]");
		return builder.toString();
	}
}