package com.wfj.pay.po;

import java.sql.Timestamp;

/**
 * 渠道签约账户的信息表 ALIPAY、TENPAY、NETPAY、单独对接银行信息
 * 
 * @author admin
 * @date 2015-12-30
 */
public class PayPartnerAccountPO implements java.io.Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 5731487090904561637L;
	/**
	 * 主键ID.
	 */
	private Long id;
	/**
	 * 商户开户分行号
	 */
	private String branchId;
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
	 * 信用卡支付介质
	 */
	private String payMediumCodeCredit;
	/**
	 * 秘钥文件的存放位置
	 */
	private String keyPath;
	/**
	 * 支付渠道类型：ALIPAY/TENPAY/NETPAY等
	 */
	private String payType;
	/**
	 * 微信为微信公众号的id，支付宝为线下支付应用的appid
	 */
	private String appid;
	/**
	 * 应用门店号：
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
	// Constructors
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

	public PayPartnerAccountPO(Long id, String branchId, String partner, String encryptKey, String sellerEmail,
			Timestamp createDate, Double feeCostRate, String payMediumCode, String payMediumCodeCredit, String keyPath,
			String payType, String appid, String storeId, String storeName, String publicKey, String privateKey,
			String payTypeCode) {
		super();
		this.id = id;
		this.branchId = branchId;
		this.partner = partner;
		this.encryptKey = encryptKey;
		this.sellerEmail = sellerEmail;
		this.createDate = createDate;
		this.feeCostRate = feeCostRate;
		this.payMediumCode = payMediumCode;
		this.payMediumCodeCredit = payMediumCodeCredit;
		this.keyPath = keyPath;
		this.payType = payType;
		this.appid = appid;
		this.storeId = storeId;
		this.storeName = storeName;
		this.publicKey = publicKey;
		this.privateKey = privateKey;
		this.payTypeCode = payTypeCode;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("PayPartnerAccountPO{");
		sb.append("id=").append(id);
		sb.append(", branchId='").append(branchId).append('\'');
		sb.append(", partner='").append(partner).append('\'');
		sb.append(", encryptKey='").append(encryptKey).append('\'');
		sb.append(", sellerEmail='").append(sellerEmail).append('\'');
		sb.append(", createDate=").append(createDate);
		sb.append(", feeCostRate=").append(feeCostRate);
		sb.append(", payMediumCode='").append(payMediumCode).append('\'');
		sb.append(", payMediumCodeCredit='").append(payMediumCodeCredit).append('\'');
		sb.append(", keyPath='").append(keyPath).append('\'');
		sb.append(", payType='").append(payType).append('\'');
		sb.append(", appid='").append(appid).append('\'');
		sb.append(", storeId='").append(storeId).append('\'');
		sb.append(", storeName='").append(storeName).append('\'');
		sb.append(", publicKey='").append(publicKey).append('\'');
		sb.append(", privateKey='").append(privateKey).append('\'');
		sb.append(", payTypeCode='").append(payTypeCode).append('\'');
		sb.append('}');
		return sb.toString();
	}

	/** default constructor */
	public PayPartnerAccountPO() {
	}

	/** minimal constructor */
	public PayPartnerAccountPO(Long id) {
		this.id = id;
	}

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
	 * 取得商户开户分行号
	 * 
	 * @return branchId 开户分行号
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * 设置开户分行号
	 * 
	 * @param branchId
	 *            开户分行号
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
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
}