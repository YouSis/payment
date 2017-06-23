package com.wfj.pay.po;


/**
 * 业务平台DTO.
 * 
 * @author wjg
 * @date 2015-12-8 
 */
public class PayMerchantPO implements java.io.Serializable {
	/**
	 * 序列化版本.
	 */

	private static final long serialVersionUID = 507548595855674087L;

	/**
	 *签约商户ID 
	 */
	private Long id;
	
	/**
	 * 签约商户名称
	 */
	private String name;
	
	/**
	 * 签约商户编码
	 */
	private String merCode;
	
	/**
	 * 费率（用于结算）
	 */
	private Double feeCostRate;
	
	//constructor
	/**
	 * 商户类型（用于结算）
	 */
	private String merchantType;
	
	/**
	 * 是否打开有赞商城连接？0否 1是
	 */
	private String isOpenYZShop;
	
	/**
	 * 有赞商城连接
	 */
	private String yzShopUrl;
	
	/**
	 * 会员中心链接
	 */
	private String memberUrl;
	
	public PayMerchantPO() {
	}


	public PayMerchantPO(Long id, String name, String merCode,
			Double feeCostRate, String merchantType) {
		super();
		this.id = id;
		this.name = name;
		this.merCode = merCode;
		this.feeCostRate = feeCostRate;
		this.merchantType = merchantType;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public Double getFeeCostRate() {
		return feeCostRate;
	}

	public void setFeeCostRate(Double feeCostRate) {
		this.feeCostRate = feeCostRate;
	}


	public String getMerchantType() {
		return merchantType;
	}


	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getIsOpenYZShop() {
		return isOpenYZShop;
	}


	public void setIsOpenYZShop(String isOpenYZShop) {
		this.isOpenYZShop = isOpenYZShop;
	}


	public String getYzShopUrl() {
		return yzShopUrl;
	}


	public void setYzShopUrl(String yzShopUrl) {
		this.yzShopUrl = yzShopUrl;
	}


	public String getMemberUrl() {
		return memberUrl;
	}


	public void setMemberUrl(String memberUrl) {
		this.memberUrl = memberUrl;
	}

	@Override
	public String toString() {
		return "PayMerchantPO [id=" + id + ", name=" + name + ", merCode="
				+ merCode + ", feeCostRate=" + feeCostRate + ", merchantType="
				+ merchantType + ", isOpenYZShop=" + isOpenYZShop
				+ ", yzShopUrl=" + yzShopUrl + ", memberUrl=" + memberUrl + "]";
	}

}