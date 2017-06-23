package com.wfj.pay.po;

/**
 * 类说明 :业务平台的收银台配置表.
 */
public class PayChannelPO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8281041806511220940L;
	/**
	 * 快捷支付id.
	 */
	private Integer id;
	
	/**
	 * 业务平台编码
	 */
	private Integer bpId;
	
	/**
	 * 银行简码
	 */
	private String dicCode;
	
	/**
	 * 支付通道类型   支付宝：ALIPAY   财付通：TENPAY  银联 ： NETPAY
	 */
	private String payType;
	
	/**
	 * 客户端类型  01 PC  02  MOBILE
	 */
	private String clientType;
	
	/**
	 * 支付渠道类型   1  网银直连   2  第三方渠道  3 银行直连
	 */
	private Integer payService;
	/**
	 * PAY_PARTNER_ACCOUNT 表的主键
	 */
	private Long payPartner;

	public PayChannelPO() {
	}

	public PayChannelPO(Integer id, Integer bpId, String dicCode,
			String payType, String clientType, Integer payService) {
		this.id = id;
		this.bpId = bpId;
		this.dicCode = dicCode;
		this.payType = payType;
		this.clientType = clientType;
		this.payService = payService;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBpId() {
		return bpId;
	}

	public void setBpId(Integer bpId) {
		this.bpId = bpId;
	}

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public Integer getPayService() {
		return payService;
	}

	public void setPayService(Integer payService) {
		this.payService = payService;
	}
	
	public Long getPayPartner() {
		return payPartner;
	}

	public void setPayPartner(Long payPartner) {
		this.payPartner = payPartner;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PayChannelPO [id=");
		builder.append(id);
		builder.append(", bpId=");
		builder.append(bpId);
		builder.append(", dicCode=");
		builder.append(dicCode);
		builder.append(", payType=");
		builder.append(payType);
		builder.append(", clientType=");
		builder.append(clientType);
		builder.append(", payService=");
		builder.append(payService);
		builder.append(", payPartner=");
		builder.append(payPartner);
		builder.append("]");
		return builder.toString();
	}
	
}