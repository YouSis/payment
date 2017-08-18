package com.wfj.pay.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 订单统计实体类
 * @author jh
 *
 */
@Document(indexName = "pay-data",type = "pay-trade")
public class PayOrderStatisticsEsPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4588306664546984202L;
	
	/**
	 * 订单ID.
	 */
	@Id
	private Long id;
	/**
	 * 订单流水号.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String orderTradeNo;
	/**
	 * 支付方式(PayChannelEnum枚举类型).
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String payType;
	/**
	 * 支付银行（支付方式为网银时有效）.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String payBank;
	/**
	 * 商品名称.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String goodsName;
	/**
	 * 订单备注.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String remark;
	/**
	 * 总金额.
	 */
	@Field(type = FieldType.Double,index = FieldIndex.not_analyzed)
	private Double totalFee;
	/**
	 * 订单状态.
	 */
	@Field(type = FieldType.Long,index = FieldIndex.not_analyzed)
	private Long status;
	/**
	 * 订单创建时间.
	 */
	private Long createDate;
	/**
	 * 订单创建时间.
	 */
	@Field(type = FieldType.Date,index = FieldIndex.not_analyzed)
	private Timestamp createDateFormat;
	/**
	 * 业务平台ID.
	 */
	@Field(type = FieldType.Long,index = FieldIndex.not_analyzed)
	private Long bpId;
	/**
	 * 业务平台订单流水号.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String bpOrderId;
	/**
	 * 商品链接地址.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String showUrl;
	/**
	 * 订单内容.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String content;
	/**
	 * 支付成功显示页面.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String notifyUrl;
	/**
	 * 支付成功显示页面.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String returnUrl;
	/**
	 * 登录帐号.
	 */
	private String account;
	/**
	 * 充值帐号.
	 */
	private String payAccount;
	/**
	 * 支付成功时间.
	 */
	@Field(type = FieldType.Long,index = FieldIndex.not_analyzed)
	private Long payDate;
	/**
	 * 支付成功时间.
	 */
	@Field(type = FieldType.Date,index = FieldIndex.not_analyzed)
	private Timestamp payDateFormat;
	/**
	 * 第三方支付平台的交易流水号.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String paySerialNumber;
	/**
	 * 订单创建时间月.
	 */
	@Field(type = FieldType.Long,index = FieldIndex.not_analyzed)
	private Long createDateMonth;
	/**
	 * 订单创建时间日.
	 */
	@Field(type = FieldType.Long,index = FieldIndex.not_analyzed)
	private Long createDateDay;
	/**
	 * 订单创建时间季度.
	 */
	@Field(type = FieldType.Long,index = FieldIndex.not_analyzed)
	private Long createDateQuarter;
	/**
	 * 订单支付时间月.
	 */
	@Field(type = FieldType.Long,index = FieldIndex.not_analyzed)
	private Long payDateMonth;
	/**
	 * 订单支付时间日.
	 */
	@Field(type = FieldType.Long,index = FieldIndex.not_analyzed)
	private Long payDateDay;
	/**
	 * 订单支付时间季度.
	 */
	@Field(type = FieldType.Long,index = FieldIndex.not_analyzed)
	private Long payDateQuarter;
	/**
	 * 支付用户在passport的ID.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String unid;
	/**
	 * 异步通知第三方的时间.
	 */
	@Field(type = FieldType.Date,index = FieldIndex.not_analyzed)
	private Timestamp notifyDate;
	/**
	 * 异步通知的次数.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String notifyNum;
	/**
	 * 是否返回成功标识.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String notifyStatus;

	/**
	 * 业务平台回传参数.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String bpParams;

	/**
	 * 通知校验ID.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String notifyId;

	/**
	 * 业务平台订单详情地址.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String orderUrl;

	/**
	 * 订单初始化终端标识（01:PC端，02:手机端）
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String initOrderTerminal;

	/**
	 * 订单支付终端标识（01:PC端，02:手机端）
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String finalPayTerminal;
	/**
	 * 支付限制.1:RMB或者G宝消费;2:RMB消费;3:G宝消费.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String payLimit;
	/**
	 * 支付IP.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String payIp;
	/**
	 * 充值账户ID.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String payUnid;
	/**
	 * 第三方渠道平台商户ID（支付宝为收款账户email）.
	 */
	@Field(type = FieldType.Long,index = FieldIndex.not_analyzed)
	private Long payPartner;
	/**
	 * 渠道费支出
	 */
	@Field(type = FieldType.Double,index = FieldIndex.not_analyzed)
	private Double channelFeeCost;

	/**
	 * 支付服务.用于后端级联下拉查询分类.1:网银直连;2:渠道账户支付;3:手机卡;4:游戏卡;5:移动支付;.
	 */
	@Field(type = FieldType.Integer,index = FieldIndex.not_analyzed)
	private Integer payService;

	/**
	 * 应付卡金额.
	 */
	@Field(type = FieldType.Double,index = FieldIndex.not_analyzed)
	private Double needPayPrice;

	/**
	 * 议价收入.
	 */
	private Double bargainIncome;

	/**
	 * 实付卡金额.
	 */
	private Double realCardPrice;

	/**
	 * 实际收入.
	 */
	private Double realIncome;

	/**
	 * 创建和支付账号昵称.
	 */
	private String nickname;

	/**
	 * 充值账号昵称.
	 */
	private String payNickname;

	/**
	 * 支付货币类型.
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String payCurrency;

	/**
	 * 签约商户编码
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String merCode;

	/**
	 * OMS商品信息，用于计算费率的促销分摊 格式：0000000000101^99.50^2|0000000000101^18.98^3
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String goodsContent;
	/**
	 * 针对OMS的商品的渠道费率的分摊明细
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String channelFeeCostDetail;
	/**
	 * 针对OMS的商品的平台（签约商户）的费率总额
	 */
	@Field(type = FieldType.Double,index = FieldIndex.not_analyzed)
	private Double platformFeeCost;
	/**
	 * 针对OMS的商品的平台（签约商户）的费率分摊明细
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String platformFeeCostDetail;
	/**
	 * 支付介质编码
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String payMediumCode;
	/**
	 * 个人中心用户名
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String username;
	/**
	 * 收银员号
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String cashier;
	/**
	 * 扫货邦返回的交易号（实际是微信的流水号  退款时用到）
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String transactionId;
	/**
	 * 第三方通知支付成功时间
	 */
	@Field(type = FieldType.Long,index = FieldIndex.not_analyzed)
	private Long platformPayDate;
	/**
	 * 第三方通知支付成功时间
	 */
	@Field(type = FieldType.Date,index = FieldIndex.not_analyzed)
	private Timestamp platformPayFormat;
	/**
	 * 优惠金额
	 */
	@Field(type = FieldType.Double,index = FieldIndex.not_analyzed)
	private double couponFee;

	@Field(type=FieldType.String)
	private String key;
	@Field(type=FieldType.Long)
	private Long count;
	@Field(type=FieldType.Double)
	private Double sumFee;

	
	
	
	public PayOrderStatisticsEsPO() {
		super();
	}

	public PayOrderStatisticsEsPO(Long id) {
		this.id = id;
	}

	public PayOrderStatisticsEsPO(Long id, String orderTradeNo, String payType, String payBank, String goodsName,
			String remark, Double totalFee, Long status, Long createDate, Timestamp createDateFormat, Long bpId,
			String bpOrderId, String showUrl, String content, String notifyUrl, String returnUrl, String account,
			String payAccount, Long payDate, Timestamp payDateFormat, String paySerialNumber, Long createDateMonth,
			Long createDateDay, Long createDateQuarter, Long payDateMonth, Long payDateDay, Long payDateQuarter,
			String unid, Timestamp notifyDate, String notifyNum, String notifyStatus, String bpParams, String notifyId,
			String orderUrl, String initOrderTerminal, String finalPayTerminal, String payLimit, String payIp,
			String payUnid, Long payPartner, Double channelFeeCost, Integer payService, Double needPayPrice,
			Double bargainIncome, Double realCardPrice, Double realIncome, String nickname, String payNickname,
			String payCurrency, String merCode, String goodsContent, String channelFeeCostDetail,
			Double platformFeeCost, String platformFeeCostDetail, String payMediumCode, String username, String cashier,
			String transactionId, Long platformPayDate, Timestamp platformPayFormat, double couponFee, String key,
			Long count, Double sumFee) {
		super();
		this.id = id;
		this.orderTradeNo = orderTradeNo;
		this.payType = payType;
		this.payBank = payBank;
		this.goodsName = goodsName;
		this.remark = remark;
		this.totalFee = totalFee;
		this.status = status;
		this.createDate = createDate;
		this.createDateFormat = createDateFormat;
		this.bpId = bpId;
		this.bpOrderId = bpOrderId;
		this.showUrl = showUrl;
		this.content = content;
		this.notifyUrl = notifyUrl;
		this.returnUrl = returnUrl;
		this.account = account;
		this.payAccount = payAccount;
		this.payDate = payDate;
		this.payDateFormat = payDateFormat;
		this.paySerialNumber = paySerialNumber;
		this.createDateMonth = createDateMonth;
		this.createDateDay = createDateDay;
		this.createDateQuarter = createDateQuarter;
		this.payDateMonth = payDateMonth;
		this.payDateDay = payDateDay;
		this.payDateQuarter = payDateQuarter;
		this.unid = unid;
		this.notifyDate = notifyDate;
		this.notifyNum = notifyNum;
		this.notifyStatus = notifyStatus;
		this.bpParams = bpParams;
		this.notifyId = notifyId;
		this.orderUrl = orderUrl;
		this.initOrderTerminal = initOrderTerminal;
		this.finalPayTerminal = finalPayTerminal;
		this.payLimit = payLimit;
		this.payIp = payIp;
		this.payUnid = payUnid;
		this.payPartner = payPartner;
		this.channelFeeCost = channelFeeCost;
		this.payService = payService;
		this.needPayPrice = needPayPrice;
		this.bargainIncome = bargainIncome;
		this.realCardPrice = realCardPrice;
		this.realIncome = realIncome;
		this.nickname = nickname;
		this.payNickname = payNickname;
		this.payCurrency = payCurrency;
		this.merCode = merCode;
		this.goodsContent = goodsContent;
		this.channelFeeCostDetail = channelFeeCostDetail;
		this.platformFeeCost = platformFeeCost;
		this.platformFeeCostDetail = platformFeeCostDetail;
		this.payMediumCode = payMediumCode;
		this.username = username;
		this.cashier = cashier;
		this.transactionId = transactionId;
		this.platformPayDate = platformPayDate;
		this.platformPayFormat = platformPayFormat;
		this.couponFee = couponFee;
		this.key = key;
		this.count = count;
		this.sumFee = sumFee;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderTradeNo() {
		return orderTradeNo;
	}

	public void setOrderTradeNo(String orderTradeNo) {
		this.orderTradeNo = orderTradeNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayBank() {
		return payBank;
	}

	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public Timestamp getCreateDateFormat() {
		return createDateFormat;
	}

	public void setCreateDateFormat(Timestamp createDateFormat) {
		this.createDateFormat = createDateFormat;
	}

	public Long getBpId() {
		return bpId;
	}

	public void setBpId(Long bpId) {
		this.bpId = bpId;
	}

	public String getBpOrderId() {
		return bpOrderId;
	}

	public void setBpOrderId(String bpOrderId) {
		this.bpOrderId = bpOrderId;
	}

	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public Long getPayDate() {
		return payDate;
	}

	public void setPayDate(Long payDate) {
		this.payDate = payDate;
	}

	public Timestamp getPayDateFormat() {
		return payDateFormat;
	}

	public void setPayDateFormat(Timestamp payDateFormat) {
		this.payDateFormat = payDateFormat;
	}

	public String getPaySerialNumber() {
		return paySerialNumber;
	}

	public void setPaySerialNumber(String paySerialNumber) {
		this.paySerialNumber = paySerialNumber;
	}

	public Long getCreateDateMonth() {
		return createDateMonth;
	}

	public void setCreateDateMonth(Long createDateMonth) {
		this.createDateMonth = createDateMonth;
	}

	public Long getCreateDateDay() {
		return createDateDay;
	}

	public void setCreateDateDay(Long createDateDay) {
		this.createDateDay = createDateDay;
	}

	public Long getCreateDateQuarter() {
		return createDateQuarter;
	}

	public void setCreateDateQuarter(Long createDateQuarter) {
		this.createDateQuarter = createDateQuarter;
	}

	public Long getPayDateMonth() {
		return payDateMonth;
	}

	public void setPayDateMonth(Long payDateMonth) {
		this.payDateMonth = payDateMonth;
	}

	public Long getPayDateDay() {
		return payDateDay;
	}

	public void setPayDateDay(Long payDateDay) {
		this.payDateDay = payDateDay;
	}

	public Long getPayDateQuarter() {
		return payDateQuarter;
	}

	public void setPayDateQuarter(Long payDateQuarter) {
		this.payDateQuarter = payDateQuarter;
	}

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public Timestamp getNotifyDate() {
		return notifyDate;
	}

	public void setNotifyDate(Timestamp notifyDate) {
		this.notifyDate = notifyDate;
	}

	public String getNotifyNum() {
		return notifyNum;
	}

	public void setNotifyNum(String notifyNum) {
		this.notifyNum = notifyNum;
	}

	public String getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public String getBpParams() {
		return bpParams;
	}

	public void setBpParams(String bpParams) {
		this.bpParams = bpParams;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public String getOrderUrl() {
		return orderUrl;
	}

	public void setOrderUrl(String orderUrl) {
		this.orderUrl = orderUrl;
	}

	public String getInitOrderTerminal() {
		return initOrderTerminal;
	}

	public void setInitOrderTerminal(String initOrderTerminal) {
		this.initOrderTerminal = initOrderTerminal;
	}

	public String getFinalPayTerminal() {
		return finalPayTerminal;
	}

	public void setFinalPayTerminal(String finalPayTerminal) {
		this.finalPayTerminal = finalPayTerminal;
	}

	public String getPayLimit() {
		return payLimit;
	}

	public void setPayLimit(String payLimit) {
		this.payLimit = payLimit;
	}

	public String getPayIp() {
		return payIp;
	}

	public void setPayIp(String payIp) {
		this.payIp = payIp;
	}

	public String getPayUnid() {
		return payUnid;
	}

	public void setPayUnid(String payUnid) {
		this.payUnid = payUnid;
	}

	public Long getPayPartner() {
		return payPartner;
	}

	public void setPayPartner(Long payPartner) {
		this.payPartner = payPartner;
	}

	public Double getChannelFeeCost() {
		return channelFeeCost;
	}

	public void setChannelFeeCost(Double channelFeeCost) {
		this.channelFeeCost = channelFeeCost;
	}

	public Integer getPayService() {
		return payService;
	}

	public void setPayService(Integer payService) {
		this.payService = payService;
	}

	public Double getNeedPayPrice() {
		return needPayPrice;
	}

	public void setNeedPayPrice(Double needPayPrice) {
		this.needPayPrice = needPayPrice;
	}

	public Double getBargainIncome() {
		return bargainIncome;
	}

	public void setBargainIncome(Double bargainIncome) {
		this.bargainIncome = bargainIncome;
	}

	public Double getRealCardPrice() {
		return realCardPrice;
	}

	public void setRealCardPrice(Double realCardPrice) {
		this.realCardPrice = realCardPrice;
	}

	public Double getRealIncome() {
		return realIncome;
	}

	public void setRealIncome(Double realIncome) {
		this.realIncome = realIncome;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPayNickname() {
		return payNickname;
	}

	public void setPayNickname(String payNickname) {
		this.payNickname = payNickname;
	}

	public String getPayCurrency() {
		return payCurrency;
	}

	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getGoodsContent() {
		return goodsContent;
	}

	public void setGoodsContent(String goodsContent) {
		this.goodsContent = goodsContent;
	}

	public String getChannelFeeCostDetail() {
		return channelFeeCostDetail;
	}

	public void setChannelFeeCostDetail(String channelFeeCostDetail) {
		this.channelFeeCostDetail = channelFeeCostDetail;
	}

	public Double getPlatformFeeCost() {
		return platformFeeCost;
	}

	public void setPlatformFeeCost(Double platformFeeCost) {
		this.platformFeeCost = platformFeeCost;
	}

	public String getPlatformFeeCostDetail() {
		return platformFeeCostDetail;
	}

	public void setPlatformFeeCostDetail(String platformFeeCostDetail) {
		this.platformFeeCostDetail = platformFeeCostDetail;
	}

	public String getPayMediumCode() {
		return payMediumCode;
	}

	public void setPayMediumCode(String payMediumCode) {
		this.payMediumCode = payMediumCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Long getPlatformPayDate() {
		return platformPayDate;
	}

	public void setPlatformPayDate(Long platformPayDate) {
		this.platformPayDate = platformPayDate;
	}

	public Timestamp getPlatformPayFormat() {
		return platformPayFormat;
	}

	public void setPlatformPayFormat(Timestamp platformPayFormat) {
		this.platformPayFormat = platformPayFormat;
	}

	public double getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(double couponFee) {
		this.couponFee = couponFee;
	}

	public void setSumFee(Double sumFee) {
		this.sumFee = sumFee;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public double getSumFee() {
		return sumFee;
	}

	public void setSumFee(double sumFee) {
		this.sumFee = sumFee;
	}

	@Override
	public String toString() {
		return "PayOrderStatisticsEsPO [id=" + id + ", orderTradeNo=" + orderTradeNo + ", payType=" + payType
				+ ", payBank=" + payBank + ", goodsName=" + goodsName + ", remark=" + remark + ", totalFee=" + totalFee
				+ ", status=" + status + ", createDate=" + createDate + ", createDateFormat=" + createDateFormat
				+ ", bpId=" + bpId + ", bpOrderId=" + bpOrderId + ", showUrl=" + showUrl + ", content=" + content
				+ ", notifyUrl=" + notifyUrl + ", returnUrl=" + returnUrl + ", account=" + account + ", payAccount="
				+ payAccount + ", payDate=" + payDate + ", payDateFormat=" + payDateFormat + ", paySerialNumber="
				+ paySerialNumber + ", createDateMonth=" + createDateMonth + ", createDateDay=" + createDateDay
				+ ", createDateQuarter=" + createDateQuarter + ", payDateMonth=" + payDateMonth + ", payDateDay="
				+ payDateDay + ", payDateQuarter=" + payDateQuarter + ", unid=" + unid + ", notifyDate=" + notifyDate
				+ ", notifyNum=" + notifyNum + ", notifyStatus=" + notifyStatus + ", bpParams=" + bpParams
				+ ", notifyId=" + notifyId + ", orderUrl=" + orderUrl + ", initOrderTerminal=" + initOrderTerminal
				+ ", finalPayTerminal=" + finalPayTerminal + ", payLimit=" + payLimit + ", payIp=" + payIp
				+ ", payUnid=" + payUnid + ", payPartner=" + payPartner + ", channelFeeCost=" + channelFeeCost
				+ ", payService=" + payService + ", needPayPrice=" + needPayPrice + ", bargainIncome=" + bargainIncome
				+ ", realCardPrice=" + realCardPrice + ", realIncome=" + realIncome + ", nickname=" + nickname
				+ ", payNickname=" + payNickname + ", payCurrency=" + payCurrency + ", merCode=" + merCode
				+ ", goodsContent=" + goodsContent + ", channelFeeCostDetail=" + channelFeeCostDetail
				+ ", platformFeeCost=" + platformFeeCost + ", platformFeeCostDetail=" + platformFeeCostDetail
				+ ", payMediumCode=" + payMediumCode + ", username=" + username + ", cashier=" + cashier
				+ ", transactionId=" + transactionId + ", platformPayDate=" + platformPayDate + ", platformPayFormat="
				+ platformPayFormat + ", couponFee=" + couponFee + ", key=" + key + ", count=" + count + ", sumFee="
				+ sumFee + "]";
	}

}
