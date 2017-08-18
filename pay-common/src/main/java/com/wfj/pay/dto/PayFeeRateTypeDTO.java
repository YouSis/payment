package com.wfj.pay.dto;

import java.io.Serializable;

/**
 * 支付费率类型DTO
 * @author yangyinbo
 *
 */
public class PayFeeRateTypeDTO implements Serializable{

	
	private static final long serialVersionUID = -2264628759984482196L;
	//所属支付渠道，支付宝，财付通等
	private String payType;
	//渠道下费率类型   支付宝网银，支付宝wap等
	private String rateType;
	//渠道下费率类型的中文名称
	private String rateTypeName;
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
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
	@Override
	public String toString() {
		return "PayFeeRateTypeDTO [payType=" + payType + ", rateType="
				+ rateType + ", rateTypeName=" + rateTypeName + "]";
	}
	public PayFeeRateTypeDTO(String payType, String rateType,
			String rateTypeName) {
		super();
		this.payType = payType;
		this.rateType = rateType;
		this.rateTypeName = rateTypeName;
	}
	public PayFeeRateTypeDTO() {
		super();
	}

}
