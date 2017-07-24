package com.wfj.pay.po;

import java.sql.Timestamp;

/**
 * 
 * 类说明 : 支付宝回调接口记录.
 */
public class PayAlipayNotifyInfoPO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3487965265136755095L;

	/**
	 * ID.
	 */
	private Long id;
	/**
	 * 订单流水号.
	 */
	private String orderTradeNo;
	/**
	 * 商品名称.
	 */
	private String subject;
	/**
	 * 支付类型.
	 */
	private String paymentType;
	/**
	 * 调用的接口名称.
	 */
	private String exterface;
	/**
	 * 支付宝交易号.
	 */
	private String tradeNo;
	/**
	 * 交易状态.
	 */
	private String tradeStatus;
	/**
	 * 通知校验ID.
	 */
	private String notifyId;
	/**
	 * 通知时间.
	 */
	private Timestamp notifyTime;
	/**
	 * 通知类型.
	 */
	private String notifyType;
	/**
	 * 交易创建时间.
	 */
	private Timestamp gmtCreate;
	/**
	 * 交易付款时间.
	 */
	private Timestamp gmtPayment;
	/**
	 * 交易关闭时间.
	 */
	private Timestamp gmtClose;
	/**
	 * 卖家支付宝帐号.
	 */
	private String sellerEmail;
	/**
	 * 买家支付宝帐号.
	 */
	private String buyerEmail;
	/**
	 * 卖家支付宝帐号对应的支付宝唯一用户号.
	 */
	private String sellerId;
	/**
	 * 买家支付宝帐号对应的支付宝唯一用户号.
	 */
	private String buyerId;
	/**
	 * 订单总金额.
	 */
	private Double totalPrice;
	/**
	 * 订单描述.
	 */
	private String description;
	/**
	 * 错误代码.
	 */
	private String errorCode;
	/**
	 * 网银流水号.
	 */
	private String bankSeqNo;
	/**
	 * 公用回传参数.
	 */
	private String extraCommonParam;
	/**
	 * 支付渠道组合信息.
	 */
	private String outChannelType;
	/**
	 * 支付金额组合信息.
	 */
	private String outChannelAmount;
	/**
	 * 实际支付渠道.
	 */
	private String outChannelInst;
	/**
	 * 最终支付方式{01:PC,02:手机).
	 */
	private String finalPayTerminal;

	// Constructors

	/** default constructor */
	public PayAlipayNotifyInfoPO() {
	}

	/** minimal constructor */
	public PayAlipayNotifyInfoPO(Long id) {
		this.id = id;
	}

	/** full constructor */
	public PayAlipayNotifyInfoPO(Long id, String orderTradeNo,
								 String subject, String paymentType, String exterface,
								 String tradeNo, String tradeStatus, String notifyId,
								 Timestamp notifyTime, String notifyType, Timestamp gmtCreate,
								 Timestamp gmtPayment, Timestamp gmtClose, String sellerEmail,
								 String buyerEmail, String sellerId, String buyerId,
								 Double totalPrice, String description, String errorCode,
								 String bankSeqNo, String extraCommonParam, String outChannelType,
								 String outChannelAmount, String outChannelInst,
								 String finalPayTerminal) {
		this.id = id;
		this.orderTradeNo = orderTradeNo;
		this.subject = subject;
		this.paymentType = paymentType;
		this.exterface = exterface;
		this.tradeNo = tradeNo;
		this.tradeStatus = tradeStatus;
		this.notifyId = notifyId;
		this.notifyTime = notifyTime;
		this.notifyType = notifyType;
		this.gmtCreate = gmtCreate;
		this.gmtPayment = gmtPayment;
		this.gmtClose = gmtClose;
		this.sellerEmail = sellerEmail;
		this.buyerEmail = buyerEmail;
		this.sellerId = sellerId;
		this.buyerId = buyerId;
		this.totalPrice = totalPrice;
		this.description = description;
		this.errorCode = errorCode;
		this.bankSeqNo = bankSeqNo;
		this.extraCommonParam = extraCommonParam;
		this.outChannelType = outChannelType;
		this.outChannelAmount = outChannelAmount;
		this.outChannelInst = outChannelInst;
		this.finalPayTerminal = finalPayTerminal;
	}

	// Property accessors

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
	 * 取得订单流水号.
	 * 
	 * @return orderTradeNo 订单流水号
	 */
	public String getOrderTradeNo() {
		return this.orderTradeNo;
	}

	/**
	 * 设置订单流水号.
	 * 
	 * @param orderTradeNo
	 *            订单流水号
	 */
	public void setOrderTradeNo(String orderTradeNo) {
		this.orderTradeNo = orderTradeNo;
	}

	/**
	 * 取得商品名称.
	 * 
	 * @return subject 商品名称
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * 设置商品名称.
	 * 
	 * @param subject
	 *            商品名称
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 取得支付类型.
	 * 
	 * @return paymentType 支付类型
	 */
	public String getPaymentType() {
		return this.paymentType;
	}

	/**
	 * 设置支付类型.
	 * 
	 * @param paymentType
	 *            支付类型
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * 取得调用的接口名称.
	 * 
	 * @return exterface 调用的接口名称
	 */
	public String getExterface() {
		return this.exterface;
	}

	/**
	 * 设置调用的接口名称.
	 * 
	 * @param exterface
	 *            调用的接口名称
	 */
	public void setExterface(String exterface) {
		this.exterface = exterface;
	}

	/**
	 * 取得支付宝交易号.
	 * 
	 * @return tradeNo 支付宝交易号
	 */
	public String getTradeNo() {
		return this.tradeNo;
	}

	/**
	 * 设置支付宝交易号.
	 * 
	 * @param tradeNo
	 *            支付宝交易号
	 */
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	/**
	 * 取得交易状态.
	 * 
	 * @return tradeStatus 交易状态
	 */
	public String getTradeStatus() {
		return this.tradeStatus;
	}

	/**
	 * 设置交易状态.
	 * 
	 * @param tradeStatus
	 *            交易状态
	 */
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	/**
	 * 取得通知校验ID.
	 * 
	 * @return notifyId 通知校验ID
	 */
	public String getNotifyId() {
		return this.notifyId;
	}

	/**
	 * 设置通知校验ID.
	 * 
	 * @param notifyId
	 *            通知校验ID
	 */
	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	/**
	 * 取得通知时间.
	 * 
	 * @return notifyTime 通知时间
	 */
	public Timestamp getNotifyTime() {
		return this.notifyTime;
	}

	/**
	 * 设置通知时间.
	 * 
	 * @param notifyTime
	 *            通知时间
	 */
	public void setNotifyTime(Timestamp notifyTime) {
		this.notifyTime = notifyTime;
	}

	/**
	 * 取得通知类型.
	 * 
	 * @return notifyType 通知类型
	 */
	public String getNotifyType() {
		return this.notifyType;
	}

	/**
	 * 设置通知类型.
	 * 
	 * @param notifyType
	 *            通知类型
	 */
	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	/**
	 * 取得交易创建时间.
	 * 
	 * @return gmtCreate 交易创建时间
	 */
	public Timestamp getGmtCreate() {
		return this.gmtCreate;
	}

	/**
	 * 设置交易创建时间.
	 * 
	 * @param gmtCreate
	 *            交易创建时间
	 */
	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/**
	 * 取得交易付款时间.
	 * 
	 * @return gmtPayment 交易付款时间
	 */
	public Timestamp getGmtPayment() {
		return this.gmtPayment;
	}

	/**
	 * 设置交易付款时间.
	 * 
	 * @param gmtPayment
	 *            交易付款时间
	 */
	public void setGmtPayment(Timestamp gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	/**
	 * 取得交易关闭时间.
	 * 
	 * @return gmtClose 交易关闭时间
	 */
	public Timestamp getGmtClose() {
		return this.gmtClose;
	}

	/**
	 * 设置交易关闭时间.
	 * 
	 * @param gmtClose
	 *            交易关闭时间
	 */
	public void setGmtClose(Timestamp gmtClose) {
		this.gmtClose = gmtClose;
	}

	/**
	 * 取得卖家支付宝帐号.
	 * 
	 * @return sellerEmail 卖家支付宝帐号
	 */
	public String getSellerEmail() {
		return this.sellerEmail;
	}

	/**
	 * 设置卖家支付宝帐号.
	 * 
	 * @param sellerEmail
	 *            卖家支付宝帐号
	 */
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	/**
	 * 取得买家支付宝帐号.
	 * 
	 * @return buyerEmail 买家支付宝帐号
	 */
	public String getBuyerEmail() {
		return this.buyerEmail;
	}

	/**
	 * 设置买家支付宝帐号.
	 * 
	 * @param buyerEmail
	 *            买家支付宝帐号
	 */
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	/**
	 * 取得卖家支付宝帐号对应的支付宝唯一用户号.
	 * 
	 * @return sellerId 卖家支付宝帐号对应的支付宝唯一用户号
	 */
	public String getSellerId() {
		return this.sellerId;
	}

	/**
	 * 设置卖家支付宝帐号对应的支付宝唯一用户号.
	 * 
	 * @param sellerId
	 *            卖家支付宝帐号对应的支付宝唯一用户号
	 */
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * 取得买家支付宝帐号对应的支付宝唯一用户号.
	 * 
	 * @return buyerId 买家支付宝帐号对应的支付宝唯一用户号
	 */
	public String getBuyerId() {
		return this.buyerId;
	}

	/**
	 * 设置买家支付宝帐号对应的支付宝唯一用户号.
	 * 
	 * @param buyerId
	 *            买家支付宝帐号对应的支付宝唯一用户号
	 */
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	/**
	 * 取得订单总金额.
	 * 
	 * @return totalPrice 订单总金额
	 */
	public Double getTotalPrice() {
		return this.totalPrice;
	}

	/**
	 * 设置订单总金额.
	 * 
	 * @param totalPrice
	 *            订单总金额
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * 取得订单描述.
	 * 
	 * @return description 订单描述
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 设置订单描述.
	 * 
	 * @param description
	 *            订单描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 取得错误代码.
	 * 
	 * @return errorCode 错误代码
	 */
	public String getErrorCode() {
		return this.errorCode;
	}

	/**
	 * 设置错误代码.
	 * 
	 * @param errorCode
	 *            错误代码
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 取得网银流水号.
	 * 
	 * @return bankSeqNo 网银流水号
	 */
	public String getBankSeqNo() {
		return this.bankSeqNo;
	}

	/**
	 * 设置网银流水号.
	 * 
	 * @param bankSeqNo
	 *            网银流水号
	 */
	public void setBankSeqNo(String bankSeqNo) {
		this.bankSeqNo = bankSeqNo;
	}

	/**
	 * 取得公用回传参数.
	 * 
	 * @return extraCommonParam 公用回传参数
	 */
	public String getExtraCommonParam() {
		return this.extraCommonParam;
	}

	/**
	 * 设置公用回传参数.
	 * 
	 * @param extraCommonParam
	 *            公用回传参数
	 */
	public void setExtraCommonParam(String extraCommonParam) {
		this.extraCommonParam = extraCommonParam;
	}

	/**
	 * 取得支付渠道组合信息.
	 * 
	 * @return outChannelType 支付渠道组合信息
	 */
	public String getOutChannelType() {
		return this.outChannelType;
	}

	/**
	 * 设置支付渠道组合信息.
	 * 
	 * @param outChannelType
	 *            支付渠道组合信息
	 */
	public void setOutChannelType(String outChannelType) {
		this.outChannelType = outChannelType;
	}

	/**
	 * 取得支付金额组合信息.
	 * 
	 * @return outChannelAmount 支付金额组合信息
	 */
	public String getOutChannelAmount() {
		return this.outChannelAmount;
	}

	/**
	 * 设置支付金额组合信息.
	 * 
	 * @param outChannelAmount
	 *            支付金额组合信息
	 */
	public void setOutChannelAmount(String outChannelAmount) {
		this.outChannelAmount = outChannelAmount;
	}

	/**
	 * 取得实际支付渠道.
	 * 
	 * @return outChannelInst 实际支付渠道
	 */
	public String getOutChannelInst() {
		return this.outChannelInst;
	}

	/**
	 * 设置实际支付渠道.
	 * 
	 * @param outChannelInst
	 *            实际支付渠道
	 */
	public void setOutChannelInst(String outChannelInst) {
		this.outChannelInst = outChannelInst;
	}

	/**
	 * @return the finalPayTerminal
	 */
	public String getFinalPayTerminal() {
		return finalPayTerminal;
	}

	/**
	 * @param finalPayTerminal
	 *            the finalPayTerminal to set
	 */
	public void setFinalPayTerminal(String finalPayTerminal) {
		this.finalPayTerminal = finalPayTerminal;
	}

}