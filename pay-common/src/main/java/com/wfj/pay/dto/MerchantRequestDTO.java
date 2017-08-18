package com.wfj.pay.dto;

import java.io.Serializable;

public class MerchantRequestDTO implements Serializable {

	private static final long serialVersionUID = 3326263953995530213L;

	private Integer pageNo;
	private Integer pageSize;
	/**
	 * 签约商户ID
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
		return "MerchantRequestDTO [pageNo=" + pageNo + ", pageSize=" + pageSize + ", id=" + id + ", name=" + name
				+ ", merCode=" + merCode + ", feeCostRate=" + feeCostRate + ", merchantType=" + merchantType
				+ ", isOpenYZShop=" + isOpenYZShop + ", yzShopUrl=" + yzShopUrl + ", memberUrl=" + memberUrl + "]";
	}

}
