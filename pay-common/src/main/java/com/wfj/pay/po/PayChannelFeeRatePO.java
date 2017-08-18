package com.wfj.pay.po;

import java.io.Serializable;

public class PayChannelFeeRatePO implements Serializable {

	private static final long serialVersionUID = -9082631879164610966L;
	private Long id;
	// 支付渠道账户
	private Integer payPartner;
	// 渠道费率
	private Double feeCostRate;
	// 渠道下的费率类型
	private String rateType;
	// 渠道类型名称
	private String rateTypeName;
	// 所属渠道
	private String payType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPayPartner() {
		return payPartner;
	}

	public void setPayPartner(Integer payPartner) {
		this.payPartner = payPartner;
	}

	public Double getFeeCostRate() {
		return feeCostRate;
	}

	public void setFeeCostRate(Double feeCostRate) {
		this.feeCostRate = feeCostRate;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getRateTypeName() {
		return rateTypeName;
	}

	public void setRateTypeName(String rateTypeName) {
		this.rateTypeName = rateTypeName;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Override
	public String toString() {
		return "PayChannelFeeRatePO [id=" + id + ", payPartner=" + payPartner + ", feeCostRate=" + feeCostRate
				+ ", rateType=" + rateType + ", rateTypeName=" + rateTypeName + ", payType=" + payType + "]";
	}

}
