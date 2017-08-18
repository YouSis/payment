package com.wfj.pay.dto;

import java.io.Serializable;

/**
 * 支付渠道类型
 * 
 * @author
 *
 */
public class PayDictionaryDTO implements Serializable {

	private static final long serialVersionUID = 4846753082253955175L;
	/**
	 * ID.
	 */
	private Long id;
	/**
	 * 名称.
	 */
	private String name;
	/**
	 * 键值.
	 */
	private String value;
	/**
	 * 银行排序.
	 */
	private Long sort;

	/**
	 * 是否在网银直连配置里（0 否 1 是）
	 */
	private Integer thirdBankFlag;

	/**
	 * 是否在第三方支付配置里（0 否 1 是）
	 */
	private Integer thirdChannelFlag;

	/**
	 * 是否在银行直连配置里（0 否 1 是）
	 */
	private Integer onlineBankFlag;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public Integer getThirdBankFlag() {
		return thirdBankFlag;
	}

	public void setThirdBankFlag(Integer thirdBankFlag) {
		this.thirdBankFlag = thirdBankFlag;
	}

	public Integer getThirdChannelFlag() {
		return thirdChannelFlag;
	}

	public void setThirdChannelFlag(Integer thirdChannelFlag) {
		this.thirdChannelFlag = thirdChannelFlag;
	}

	public Integer getOnlineBankFlag() {
		return onlineBankFlag;
	}

	public void setOnlineBankFlag(Integer onlineBankFlag) {
		this.onlineBankFlag = onlineBankFlag;
	}

	@Override
	public String toString() {
		return "PayDictionaryDTO [id=" + id + ", name=" + name + ", value=" + value + ", sort=" + sort
				+ ", thirdBankFlag=" + thirdBankFlag + ", thirdChannelFlag=" + thirdChannelFlag + ", onlineBankFlag="
				+ onlineBankFlag + "]";
	}

	public PayDictionaryDTO(Long id, String name, String value, Long sort, Integer thirdBankFlag,
			Integer thirdChannelFlag, Integer onlineBankFlag) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.sort = sort;
		this.thirdBankFlag = thirdBankFlag;
		this.thirdChannelFlag = thirdChannelFlag;
		this.onlineBankFlag = onlineBankFlag;
	}

	public PayDictionaryDTO(Long id) {
		super();
		this.id = id;
	}

	public PayDictionaryDTO() {
		super();
	}

}
