package com.wfj.pay.dto;

/**
 * 选择银行下拉选择框DTO.
 * 
 * @author yangyinbo
 */
public class SelectBankDTO implements java.io.Serializable {
	private static final long serialVersionUID = -5266857086110968402L;
	
	private Integer id;
	
	private String name;
	
	private String value;
	
	private Integer third_bank_flag;
	
	private Integer third_channel_flag;
	
	private Integer online_bank_flag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "SelectBankDTO [id=" + id + ", name=" + name + ", value="
				+ value + ", third_bank_flag=" + third_bank_flag
				+ ", third_channel_flag=" + third_channel_flag
				+ ", online_bank_flag=" + online_bank_flag + "]";
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getThird_bank_flag() {
		return third_bank_flag;
	}

	public void setThird_bank_flag(Integer third_bank_flag) {
		this.third_bank_flag = third_bank_flag;
	}

	public Integer getThird_channel_flag() {
		return third_channel_flag;
	}

	public void setThird_channel_flag(Integer third_channel_flag) {
		this.third_channel_flag = third_channel_flag;
	}

	public Integer getOnline_bank_flag() {
		return online_bank_flag;
	}

	public void setOnline_bank_flag(Integer online_bank_flag) {
		this.online_bank_flag = online_bank_flag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
