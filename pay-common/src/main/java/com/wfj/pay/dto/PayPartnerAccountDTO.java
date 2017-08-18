package com.wfj.pay.dto;

import java.sql.Timestamp;

public class PayPartnerAccountDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4713577685804448898L;

	private Integer pageNo;
	private Integer pageSize;

	/**
	 * 主键ID.
	 */
	private Long id;
	/**
	 * 合作身份者ID.
	 */
	private String partner;
	/**
	 * 交易安全检验码，由数字和字母组成的32位字符串.
	 */
	private String encryptKey;
	/**
	 * 签约支付宝账号或卖家收款支付宝帐户.
	 */
	private String sellerEmail;
	/**
	 * 创建时间.
	 */
	private Timestamp createDate;
	/**
	 * 费率
	 */
	private Double feeCostRate;
	/**
	 * 支付介质编码
	 */
	private String payMediumCode;
	/**
	 * 秘钥文件的存放位置
	 */
	private String keyPath;
	/**
	 * 支付渠道类型：ALIPAY/TENPAY/NETPAY等
	 */
	private String payType;
	// Constructors
	private String branchId;
	/**
	 * payMediumCodeCre
	 */
	private String payMediumCodeCredit;
	/**
	 * appId
	 */
	private String appid;
	/**
	 * 应用门店号
	 */
	private String storeId;
	/**
	 * 门店名称（目前主要用于区分支付宝超市和百货）
	 */
	private String storeName;

	/**
	 * 支付宝公钥
	 */
	private String publicKey;

	/**
	 * 支付宝私钥
	 */
	private String privateKey;

	/**
	 * 支付类型(中文) 多表关联查询
	 */
	private String payTypeCode;

	public String getPayTypeCode() {
		return payTypeCode;
	}

	public void setPayTypeCode(String payTypeCode) {
		this.payTypeCode = payTypeCode;
	}

	public PayPartnerAccountDTO(Long id, String partner, String encryptKey, String sellerEmail, Timestamp createDate,
			Double feeCostRate, String payMediumCode, String keyPath, String payType, String branchId,
			String payMediumCodeCredit, String appid, String storeId, String storeName, String publicKey,
			String privateKey, String payTypeCode) {
		super();
		this.id = id;
		this.partner = partner;
		this.encryptKey = encryptKey;
		this.sellerEmail = sellerEmail;
		this.createDate = createDate;
		this.feeCostRate = feeCostRate;
		this.payMediumCode = payMediumCode;
		this.keyPath = keyPath;
		this.payType = payType;
		this.branchId = branchId;
		this.payMediumCodeCredit = payMediumCodeCredit;
		this.appid = appid;
		this.storeId = storeId;
		this.storeName = storeName;
		this.publicKey = publicKey;
		this.privateKey = privateKey;
		this.payTypeCode = payTypeCode;
	}

	/** default constructor */
	public PayPartnerAccountDTO() {
	}

	/** minimal constructor */
	public PayPartnerAccountDTO(Long id) {
		this.id = id;
	}

	/** full constructor */

	// Property accessors
	/**
	 * 取得主键ID.
	 * 
	 * @return id 主键ID
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * 设置主键ID.
	 * 
	 * @param id
	 *            主键ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 取得合作身份者ID.
	 * 
	 * @return partner 合作身份者ID
	 */
	public String getPartner() {
		return this.partner;
	}

	/**
	 * 设置合作身份者ID.
	 * 
	 * @param partner
	 *            合作身份者ID
	 */
	public void setPartner(String partner) {
		this.partner = partner;
	}

	/**
	 * 取得交易安全检验码，由数字和字母组成的32位字符串.
	 * 
	 * @return encryptKey 交易安全检验码，由数字和字母组成的32位字符串
	 */
	public String getEncryptKey() {
		return this.encryptKey;
	}

	/**
	 * 设置交易安全检验码，由数字和字母组成的32位字符串.
	 * 
	 * @param encryptKey
	 *            交易安全检验码，由数字和字母组成的32位字符串
	 */
	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	/**
	 * 取得签约支付宝账号或卖家收款支付宝帐户.
	 * 
	 * @return sellerEmail 签约支付宝账号或卖家收款支付宝帐户
	 */
	public String getSellerEmail() {
		return this.sellerEmail;
	}

	/**
	 * 设置签约支付宝账号或卖家收款支付宝帐户.
	 * 
	 * @param sellerEmail
	 *            签约支付宝账号或卖家收款支付宝帐户
	 */
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
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

	public Double getFeeCostRate() {
		return feeCostRate;
	}

	public void setFeeCostRate(Double feeCostRate) {
		this.feeCostRate = feeCostRate;
	}

	public String getPayMediumCode() {
		return payMediumCode;
	}

	public void setPayMediumCode(String payMediumCode) {
		this.payMediumCode = payMediumCode;
	}

	public String getKeyPath() {
		return keyPath;
	}

	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getPayMediumCodeCredit() {
		return payMediumCodeCredit;
	}

	public void setPayMediumCodeCredit(String payMediumCodeCredit) {
		this.payMediumCodeCredit = payMediumCodeCredit;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

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

	@Override
	public String toString() {
		return "PayPartnerAccountDTO [id=" + id + ", partner=" + partner + ", encryptKey=" + encryptKey
				+ ", sellerEmail=" + sellerEmail + ", createDate=" + createDate + ", feeCostRate=" + feeCostRate
				+ ", payMediumCode=" + payMediumCode + ", keyPath=" + keyPath + ", payType=" + payType + ", branchId="
				+ branchId + ", payMediumCodeCredit=" + payMediumCodeCredit + ", appid=" + appid + ", storeId="
				+ storeId + ", storeName=" + storeName + ", publicKey=" + publicKey + ", privateKey=" + privateKey
				+ ", payTypeCode=" + payTypeCode + "]";
	}

}