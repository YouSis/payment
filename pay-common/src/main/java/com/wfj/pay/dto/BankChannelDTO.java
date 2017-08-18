package com.wfj.pay.dto;

import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * 支付渠道银行DTO.
 */
public class BankChannelDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4966705478964259675L;

	private Integer id;

	private Integer bp_id;
	
	private String dic_code;
	
	private String pay_type;
	
	private String client_type;
	
	private Integer pay_service;
	
	private Integer pay_partner;
	
	private String payTypeCode;
	
	private String bankCode;

	





	
	@Override
	public String toString() {
		return "BankChannelDTO [id=" + id + ", bp_id=" + bp_id + ", dic_code="
				+ dic_code + ", pay_type=" + pay_type + ", client_type="
				+ client_type + ", pay_service=" + pay_service
				+ ", pay_partner=" + pay_partner + ", payTypeCode="
				+ payTypeCode + ", bankCode=" + bankCode + "]";
	}


	public String getBankCode() {
		return bankCode;
	}


	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}


	public String getPayTypeCode() {
		return payTypeCode;
	}


	public void setPayTypeCode(String payTypeCode) {
		this.payTypeCode = payTypeCode;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBp_id() {
		return bp_id;
	}

	public void setBp_id(Integer bp_id) {
		this.bp_id = bp_id;
	}

	public String getDic_code() {
		return dic_code;
	}

	public void setDic_code(String dic_code) {
		this.dic_code = dic_code;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getClient_type() {
		return client_type;
	}

	public void setClient_type(String client_type) {
		this.client_type = client_type;
	}

	public Integer getPay_service() {
		return pay_service;
	}

	public void setPay_service(Integer pay_service) {
		this.pay_service = pay_service;
	}

	public Integer getPay_partner() {
		return pay_partner;
	}

	public void setPay_partner(Integer pay_partner) {
		this.pay_partner = pay_partner;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
