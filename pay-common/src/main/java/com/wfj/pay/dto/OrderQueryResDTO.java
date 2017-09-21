package com.wfj.pay.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 订单明细查询DTO
 * 
 * @author haowenchao
 */
public class OrderQueryResDTO implements Serializable {

	private static final long serialVersionUID = 2687916197241519893L;

	/**
	 * 业务流水号.
	 */
	private String bpOrderId;

	/**
	 * 接口业务.
	 */
	private String bpName;
	/**
	 * 订单号.
	 */
	private String orderTradeNo;
	/**
	 * 订单生成时间.
	 */
	private Long createDate;
	/**
	 * 支付成功时间.
	 */
	private Long payDate;
	/**
	 * 订单内容.
	 */
	private String content;
	/**
	 * 支付通行证.
	 */
	private String account;
	/**
	 * 支付金额.
	 */
	private Double totalFee;
	/**
	 * 支付渠道.
	 */
	private String payType;
	/**
	 * 支付渠道名称.
	 */
	private String payTypeName;
	/**
	 * 支付平台流水号.
	 */
	private String paySerialNumber;

	private String rate;
	/**
	 * 状态.
	 */
	private Long status;
	/**
	 * 状态.
	 */
	private String statusName;
	/**
	 * 查看详细.
	 */
	private String detailUrl;
	/**
	 * 操作.
	 */
	private String operationUrl;
	/**
	 * 支付银行.
	 */
	private String payBank;

	private String payBankName;

	private Long dicId;

	/**
	 * 商品名称.
	 */
	private String goodsName;

	/**
	 * 支付账号.
	 */
	private String payAccount;

	/**
	 * 用户ID.
	 */
	private String unid;

	/**
	 * 用户昵称
	 */
	private String nickname;

	/**
	 * 被支付用户昵称
	 */
	private String payNickname;

	/**
	 * 订单展示地址.
	 */
	private String showUrl;

	/**
	 * 业务平台ID.
	 */
	private Long bpId;
	/**
	 * 订单初始化终端标识（01:PC端，02:手机端）.
	 */
	private String initOrderTerminal;
	/**
	 * 订单初始化终端标识名称.
	 */
	private String initOrderTerminalName;

	/**
	 * 订单支付终端标识（01:PC端，02:手机端）.
	 */
	private String finalPayTerminal;
	private String finalPayTerminalName;
	/**
	 * 手续费金额
	 */
	private Double channelFeeCost = 0.0;
	/**
	 * 支付IP
	 */
	private String payIp;

	/**
	 * 支付服务.
	 */
	private Integer payService;
	private String payServiceName;

	/**
	 * 应付金额.
	 */
	private Double needPayPrice;

	/**
	 * 议价收入.应付卡金额*（20%-对应卡的费率）
	 */
	private Double bargainIncome;

	/**
	 * 支付货币类型.
	 */
	private String payCurrency;

	/**
	 * 签约商户编码
	 */
	private String merCode;

	/**
	 * 用户账户
	 */
	private String userName;

	private Timestamp createDateFormat;

	private Long payPartner;

	/**
	 * get 业务流水号.
	 * 
	 * @return 业务流水号.
	 */
	public String getBpOrderId() {
		return bpOrderId;
	}

	/**
	 * set 业务流水号.
	 * 
	 * @param bpOrderId
	 *            业务流水号.
	 */
	public void setBpOrderId(String bpOrderId) {
		this.bpOrderId = bpOrderId;
	}

	/**
	 * get 接口业务.
	 * 
	 * @return 接口业务.
	 */
	public String getBpName() {
		return bpName;
	}

	/**
	 * set 接口业务.
	 * 
	 * @param bpName
	 *            接口业务.
	 */
	public void setBpName(String bpName) {
		this.bpName = bpName;
	}

	/**
	 * get 订单号.
	 * 
	 * @return 订单号.
	 */
	public String getOrderTradeNo() {
		return orderTradeNo;
	}

	/**
	 * set 订单号.
	 * 
	 * @param orderTradeNo
	 *            订单号.
	 */
	public void setOrderTradeNo(String orderTradeNo) {
		this.orderTradeNo = orderTradeNo;
	}

	/**
	 * get 订单生成时间.
	 * 
	 * @return 订单生成时间.
	 */
	public Long getCreateDate() {
		return createDate;
	}

	/**
	 * set 订单生成时间.
	 * 
	 * @param createDate
	 *            订单生成时间.
	 */
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	/**
	 * get 支付成功时间.
	 * 
	 * @return 支付成功时间.
	 */
	public Long getPayDate() {
		return payDate;
	}

	/**
	 * set 支付成功时间.
	 * 
	 * @param payDate
	 *            支付成功时间.
	 */
	public void setPayDate(Long payDate) {
		this.payDate = payDate;
	}

	/**
	 * get 订单内容.
	 * 
	 * @return 订单内容.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * set 订单内容.
	 * 
	 * @param content
	 *            订单内容.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * get 支付通行证.
	 * 
	 * @return 支付通行证.
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * set 支付通行证.
	 * 
	 * @param account
	 *            支付通行证.
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * get 支付渠道.
	 * 
	 * @return 支付渠道.
	 */
	public String getPayType() {
		return payType;
	}

	/**
	 * set 支付渠道.
	 * 
	 * @param payType
	 *            支付渠道.
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}

	/**
	 * get 支付渠道名称.
	 * 
	 * @return 支付渠道名称.
	 */
	public String getPayTypeName() {
		return payTypeName;
	}

	/**
	 * set 支付渠道名称.
	 * 
	 * @param payTypeName
	 *            支付渠道名称.
	 */
	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	/**
	 * get 支付平台流水号.
	 * 
	 * @return 支付平台流水号.
	 */
	public String getPaySerialNumber() {
		return paySerialNumber;
	}

	/**
	 * set 支付平台流水号.
	 * 
	 * @param paySerialNumber
	 *            支付平台流水号.
	 */
	public void setPaySerialNumber(String paySerialNumber) {
		this.paySerialNumber = paySerialNumber;
	}

	/**
	 * get 状态.
	 * 
	 * @return 状态.
	 */
	public Long getStatus() {
		return status;
	}

	/**
	 * set 状态.
	 * 
	 * @param status
	 *            状态.
	 */
	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * get 状态.
	 * 
	 * @return 状态.
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * set 状态.
	 * 
	 * @param statusName
	 *            状态.
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * get 查看详细.
	 * 
	 * @return 查看详细.
	 */
	public String getDetailUrl() {
		return detailUrl;
	}

	/**
	 * set 查看详细.
	 * 
	 * @param detailUrl
	 *            查看详细.
	 */
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	/**
	 * get 操作.
	 * 
	 * @return 操作.
	 */
	public String getOperationUrl() {
		return operationUrl;
	}

	/**
	 * set 操作.
	 * 
	 * @param operationUrl
	 *            操作.
	 */
	public void setOperationUrl(String operationUrl) {
		this.operationUrl = operationUrl;
	}

	/**
	 * get 支付银行.
	 * 
	 * @return 支付银行.
	 */
	public String getPayBank() {
		return payBank;
	}

	/**
	 * set 支付银行.
	 * 
	 * @param payBank
	 *            支付银行.
	 */
	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

	/**
	 * get 商品名称.
	 * 
	 * @return 商品名称.
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * set 商品名称.
	 * 
	 * @param goodsName
	 *            商品名称.
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * get 支付账号.
	 * 
	 * @return 支付账号.
	 */
	public String getPayAccount() {
		return payAccount;
	}

	/**
	 * set 支付账号.
	 * 
	 * @param payAccount
	 *            支付账号.
	 */
	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	/**
	 * get 用户ID.
	 * 
	 * @return 用户ID.
	 */
	public String getUnid() {
		return unid;
	}

	/**
	 * set 用户ID.
	 * 
	 * @param unid
	 *            用户ID.
	 */
	public void setUnid(String unid) {
		this.unid = unid;
	}

	/**
	 * get 订单展示地址.
	 * 
	 * @return 订单展示地址.
	 */
	public String getShowUrl() {
		return showUrl;
	}

	/**
	 * set 订单展示地址.
	 * 
	 * @param showUrl
	 *            订单展示地址.
	 */
	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	/**
	 * get 业务平台ID.
	 * 
	 * @return 业务平台ID.
	 */
	public Long getBpId() {
		return bpId;
	}

	/**
	 * set 业务平台ID.
	 * 
	 * @param bpId
	 *            业务平台ID.
	 */
	public void setBpId(Long bpId) {
		this.bpId = bpId;
	}

	/**
	 * get 订单初始化终端标识（01:PC端，02:手机端）.
	 * 
	 * @return 订单初始化终端标识（01:PC端，02:手机端）.
	 */
	public String getInitOrderTerminal() {
		return initOrderTerminal;
	}

	/**
	 * set 订单初始化终端标识（01:PC端，02:手机端）.
	 * 
	 * @param initOrderTerminal
	 *            订单初始化终端标识（01:PC端，02:手机端）.
	 */
	public void setInitOrderTerminal(String initOrderTerminal) {
		this.initOrderTerminal = initOrderTerminal;
	}

	/**
	 * get 订单初始化终端标识名称.
	 * 
	 * @return 订单初始化终端标识名称.
	 */
	public String getInitOrderTerminalName() {
		return initOrderTerminalName;
	}

	/**
	 * set 订单初始化终端标识名称.
	 * 
	 * @param initOrderTerminalName
	 *            订单初始化终端标识名称.
	 */
	public void setInitOrderTerminalName(String initOrderTerminalName) {
		this.initOrderTerminalName = initOrderTerminalName;
	}

	/**
	 * get 订单支付终端标识（01:PC端，02:手机端）.
	 * 
	 * @return 订单支付终端标识（01:PC端，02:手机端）.
	 */
	public String getFinalPayTerminal() {
		return finalPayTerminal;
	}

	/**
	 * set 订单支付终端标识（01:PC端，02:手机端）.
	 * 
	 * @param finalPayTerminal
	 *            订单支付终端标识（01:PC端，02:手机端）.
	 */
	public void setFinalPayTerminal(String finalPayTerminal) {
		this.finalPayTerminal = finalPayTerminal;
	}

	/**
	 * get 手续费金额
	 * 
	 * @return 手续费金额
	 */
	public Double getChannelFeeCost() {
		return channelFeeCost;
	}

	/**
	 * set 手续费金额
	 * 
	 * @param channelFeeCost
	 *            手续费金额
	 */
	public void setChannelFeeCost(Double channelFeeCost) {
		this.channelFeeCost = channelFeeCost;
	}

	/**
	 * get 支付IP
	 * 
	 * @return 支付IP
	 */
	public String getPayIp() {
		return payIp;
	}

	/**
	 * set 支付IP
	 * 
	 * @param payIp
	 *            支付IP
	 */
	public void setPayIp(String payIp) {
		this.payIp = payIp;
	}

	/**
	 * get 支付服务.
	 * 
	 * @return 支付服务.
	 */
	public Integer getPayService() {
		return payService;
	}

	/**
	 * set 支付服务.
	 * 
	 * @param payService
	 *            支付服务.
	 */
	public void setPayService(Integer payService) {
		this.payService = payService;
	}

	/**
	 * get 应付金额.
	 * 
	 * @return 应付金额.
	 */
	public Double getNeedPayPrice() {
		return needPayPrice;
	}

	/**
	 * set 应付金额.
	 * 
	 * @param needPayPrice
	 *            应付金额.
	 */
	public void setNeedPayPrice(Double needPayPrice) {
		this.needPayPrice = needPayPrice;
	}

	/**
	 * get 议价收入.
	 * 
	 * @return 议价收入.
	 */
	public Double getBargainIncome() {
		return bargainIncome;
	}

	/**
	 * set 议价收入.
	 * 
	 * @param bargainIncome
	 *            议价收入.
	 */
	public void setBargainIncome(Double bargainIncome) {
		this.bargainIncome = bargainIncome;
	}

	/**
	 * get 用户昵称
	 * 
	 * @return 用户昵称
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * set 用户昵称
	 * 
	 * @param nickname
	 *            用户昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * get 被支付用户昵称
	 * 
	 * @return 被支付用户昵称
	 */
	public String getPayNickname() {
		return payNickname;
	}

	/**
	 * set 被支付用户昵称
	 * 
	 * @param payNickname
	 *            被支付用户昵称
	 */
	public void setPayNickname(String payNickname) {
		this.payNickname = payNickname;
	}

	/**
	 * get 支付货币类型.
	 * 
	 * @return 支付货币类型.
	 */
	public String getPayCurrency() {
		return payCurrency;
	}

	/**
	 * set 支付货币类型.
	 * 
	 * @param payCurrency
	 *            支付货币类型.
	 */
	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	@Override
	public String toString() {
		return "OrderQueryResDTO [bpOrderId=" + bpOrderId + ", bpName=" + bpName + ", orderTradeNo=" + orderTradeNo
				+ ", createDate=" + createDate + ", payDate=" + payDate + ", content=" + content + ", account="
				+ account + ", totalFee=" + totalFee + ", payType=" + payType + ", payTypeName=" + payTypeName
				+ ", paySerialNumber=" + paySerialNumber + ", rate=" + rate + ", status=" + status + ", statusName="
				+ statusName + ", detailUrl=" + detailUrl + ", operationUrl=" + operationUrl + ", payBank=" + payBank
				+ ", payBankName=" + payBankName + ", dicId=" + dicId + ", goodsName=" + goodsName + ", payAccount="
				+ payAccount + ", unid=" + unid + ", nickname=" + nickname + ", payNickname=" + payNickname
				+ ", showUrl=" + showUrl + ", bpId=" + bpId + ", initOrderTerminal=" + initOrderTerminal
				+ ", initOrderTerminalName=" + initOrderTerminalName + ", finalPayTerminal=" + finalPayTerminal
				+ ", finalPayTerminalName=" + finalPayTerminalName + ", channelFeeCost=" + channelFeeCost + ", payIp="
				+ payIp + ", payService=" + payService + ", payServiceName=" + payServiceName + ", needPayPrice="
				+ needPayPrice + ", bargainIncome=" + bargainIncome + ", payCurrency=" + payCurrency + ", merCode="
				+ merCode + ", userName=" + userName + ", createDateFormat=" + createDateFormat + ", payPartner="
				+ payPartner + "]";
	}

	/**
	 * 
	 */
	public OrderQueryResDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderQueryResDTO(String bpOrderId, String bpName, String orderTradeNo, Long createDate, Long payDate,
			String content, String account, Double totalFee, String payType, String payTypeName, String paySerialNumber,
			String rate, Long status, String statusName, String detailUrl, String operationUrl, String payBank,
			String payBankName, Long dicId, String goodsName, String payAccount, String unid, String nickname,
			String payNickname, String showUrl, Long bpId, String initOrderTerminal, String initOrderTerminalName,
			String finalPayTerminal, String finalPayTerminalName, Double channelFeeCost, String payIp,
			Integer payService, String payServiceName, Double needPayPrice, Double bargainIncome, String payCurrency,
			String merCode, String userName, Timestamp createDateFormat, Long payPartner) {
		super();
		this.bpOrderId = bpOrderId;
		this.bpName = bpName;
		this.orderTradeNo = orderTradeNo;
		this.createDate = createDate;
		this.payDate = payDate;
		this.content = content;
		this.account = account;
		this.totalFee = totalFee;
		this.payType = payType;
		this.payTypeName = payTypeName;
		this.paySerialNumber = paySerialNumber;
		this.rate = rate;
		this.status = status;
		this.statusName = statusName;
		this.detailUrl = detailUrl;
		this.operationUrl = operationUrl;
		this.payBank = payBank;
		this.payBankName = payBankName;
		this.dicId = dicId;
		this.goodsName = goodsName;
		this.payAccount = payAccount;
		this.unid = unid;
		this.nickname = nickname;
		this.payNickname = payNickname;
		this.showUrl = showUrl;
		this.bpId = bpId;
		this.initOrderTerminal = initOrderTerminal;
		this.initOrderTerminalName = initOrderTerminalName;
		this.finalPayTerminal = finalPayTerminal;
		this.finalPayTerminalName = finalPayTerminalName;
		this.channelFeeCost = channelFeeCost;
		this.payIp = payIp;
		this.payService = payService;
		this.payServiceName = payServiceName;
		this.needPayPrice = needPayPrice;
		this.bargainIncome = bargainIncome;
		this.payCurrency = payCurrency;
		this.merCode = merCode;
		this.userName = userName;
		this.createDateFormat = createDateFormat;
		this.payPartner = payPartner;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Timestamp getCreateDateFormat() {
		return createDateFormat;
	}

	public void setCreateDateFormat(Timestamp createDateFormat) {
		this.createDateFormat = createDateFormat;
	}

	public Long getPayPartner() {
		return payPartner;
	}

	public void setPayPartner(Long payPartner) {
		this.payPartner = payPartner;
	}

	public String getPayBankName() {
		return payBankName;
	}

	public void setPayBankName(String payBankName) {
		this.payBankName = payBankName;
	}

	public Long getDicId() {
		return dicId;
	}

	public void setDicId(Long dicId) {
		this.dicId = dicId;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getFinalPayTerminalName() {
		return finalPayTerminalName;
	}

	public void setFinalPayTerminalName(String finalPayTerminalName) {
		this.finalPayTerminalName = finalPayTerminalName;
	}

	public String getPayServiceName() {
		return payServiceName;
	}

	public void setPayServiceName(String payServiceName) {
		this.payServiceName = payServiceName;
	}

}
