package com.wfj.pay.po;

/**
 * 
 * 类说明 : .
 */
public class PayDictionaryPO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7636073447088639715L;
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
	 * 是否在网银直连配置里（0  否  1 是）
	 */
	private Integer thirdBankFlag;
	
	/**
	 * 是否在第三方支付配置里（0  否  1 是）
	 */
	private Integer thirdChannelFlag;
	
	/**
	 * 是否在银行直连配置里（0  否  1 是）
	 */
	private Integer onlineBankFlag;
	
	public PayDictionaryPO() {
	}

	public PayDictionaryPO(Long id) {
		this.id = id;
	}
	
	public PayDictionaryPO(Long id, String name, String value, Long sort,
			Integer thirdBankFlag, Integer thirdChannelFlag,
			Integer onlineBankFlag) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.sort = sort;
		this.thirdBankFlag = thirdBankFlag;
		this.thirdChannelFlag = thirdChannelFlag;
		this.onlineBankFlag = onlineBankFlag;
	}

	/**
	 * 取得ID.
	 * 
	 * @return id ID
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * 设置ID.
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 取得名称.
	 * 
	 * @return name 名称
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置名称.
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 取得键值.
	 * 
	 * @return value 键值
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * 设置键值.
	 * 
	 * @param value
	 *            键值
	 */
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
		StringBuilder builder = new StringBuilder();
		builder.append("PayDictionaryPO [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", value=");
		builder.append(value);
		builder.append(", sort=");
		builder.append(sort);
		builder.append(", thirdBankFlag=");
		builder.append(thirdBankFlag);
		builder.append(", thirdChannelFlag=");
		builder.append(thirdChannelFlag);
		builder.append(", onlineBankFlag=");
		builder.append(onlineBankFlag);
		builder.append("]");
		return builder.toString();
	}
	
}