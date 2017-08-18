package com.wfj.pay.dto;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.sql.Timestamp;

/**
 * 渠道签约账号DTO.
 */
public class PartnerAccountDTO implements Serializable {

	private static final long serialVersionUID = 8851707037242894860L;

	private Integer id;

	private String partner;

	private String ENCRYPT_KEY;

	private String KEY_PATH;

	private Integer FEE_COST_RATE;

	private Timestamp CREATE_DATE;

	private String PAY_MEDIUM_CODE;

	private String SELLER_EMAIL;

	private String PAY_TYPE;

	private String STORE_NAME;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getENCRYPT_KEY() {
		return ENCRYPT_KEY;
	}

	public void setENCRYPT_KEY(String eNCRYPT_KEY) {
		ENCRYPT_KEY = eNCRYPT_KEY;
	}

	public String getKEY_PATH() {
		return KEY_PATH;
	}

	public void setKEY_PATH(String kEY_PATH) {
		KEY_PATH = kEY_PATH;
	}

	public Integer getFEE_COST_RATE() {
		return FEE_COST_RATE;
	}

	public void setFEE_COST_RATE(Integer fEE_COST_RATE) {
		FEE_COST_RATE = fEE_COST_RATE;
	}

	public Timestamp getCREATE_DATE() {
		return CREATE_DATE;
	}

	public void setCREATE_DATE(Timestamp cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	public String getPAY_MEDIUM_CODE() {
		return PAY_MEDIUM_CODE;
	}

	public void setPAY_MEDIUM_CODE(String pAY_MEDIUM_CODE) {
		PAY_MEDIUM_CODE = pAY_MEDIUM_CODE;
	}

	public String getSELLER_EMAIL() {
		return SELLER_EMAIL;
	}

	public void setSELLER_EMAIL(String sELLER_EMAIL) {
		SELLER_EMAIL = sELLER_EMAIL;
	}

	public String getPAY_TYPE() {
		return PAY_TYPE;
	}

	public void setPAY_TYPE(String pAY_TYPE) {
		PAY_TYPE = pAY_TYPE;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSTORE_NAME() {
		return STORE_NAME;
	}

	public void setSTORE_NAME(String sTORE_NAME) {
		STORE_NAME = sTORE_NAME;
	}

	@Override
	public String toString() {
		return "PartnerAccountDTO [id=" + id + ", partner=" + partner + ", ENCRYPT_KEY=" + ENCRYPT_KEY + ", KEY_PATH="
				+ KEY_PATH + ", FEE_COST_RATE=" + FEE_COST_RATE + ", CREATE_DATE=" + CREATE_DATE + ", PAY_MEDIUM_CODE="
				+ PAY_MEDIUM_CODE + ", SELLER_EMAIL=" + SELLER_EMAIL + ", PAY_TYPE=" + PAY_TYPE + ", STORE_NAME="
				+ STORE_NAME + "]";
	}

}
