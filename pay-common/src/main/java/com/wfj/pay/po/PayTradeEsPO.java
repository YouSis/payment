package com.wfj.pay.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.Timestamp;

/**
 * 
 * 类说明 : 支付订单类.
 */
@Document(indexName = "pay-data",type = "pay-trade")
public class PayTradeEsPO implements java.io.Serializable {
	private static final long serialVersionUID = -7491853205417294059L;

	// Fields

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

	// Constructors

	/** default constructor */
	public PayTradeEsPO() {
	}

	/** minimal constructor */
	public PayTradeEsPO(Long id) {
		this.id = id;
	}

	/** full constructor */
	public PayTradeEsPO(Long id, String orderTradeNo, String payType,
                        String payBank, String goodsName, String remark, Double totalFee,
                        Long status, Long createDate, Timestamp createDateFormat,
                        Long bpId, String bpOrderId, String showUrl, String content,
                        String notifyUrl, String returnUrl, String account,
                        String payAccount, Long payDate, Timestamp payDateFormat,
                        String paySerialNumber, Long createDateMonth, Long createDateDay,
                        Long createDateQuarter, Long payDateMonth, Long payDateDay,
                        Long payDateQuarter, String unid, Timestamp notifyDate,
                        String notifyNum, String notifyStatus, String bpParams,
                        String notifyId, String orderUrl, String initOrderTerminal,
                        String finalPayTerminal, String payLimit, String payIp,
                        String payUnid, Long payPartner, Double channelFeeCost,
                        Integer payService, Double needPayPrice, Double bargainIncome,
                        Double realCardPrice, Double realIncome, String nickname,
                        String payNickname, String payCurrency, String merCode,
                        String goodsContent, String channelFeeCostDetail,
                        Double platformFeeCost, String platformFeeCostDetail,
                        String payMediumCode, String username, String cashier, double couponFee) {
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
		this.couponFee = couponFee;
	}

	// Property accessors
	/**
	 * 取得订单ID.
	 *
	 * @return id 订单ID
	 */
	public Long getId() {
		return this.id;
	}


	/**
	 * 设置订单ID.
	 * 
	 * @param id
	 *            订单ID
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
	 * 取得支付方式(1:网银,2:支付宝).
	 * 
	 * @return payType 支付方式(1:网银,2:支付宝)
	 */
	public String getPayType() {
		return this.payType;
	}

	/**
	 * 设置支付方式(1:网银,2:支付宝).
	 * 
	 * @param payType
	 *            支付方式(1:网银,2:支付宝)
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}

	/**
	 * 取得支付银行（支付方式为网银时有效）.
	 * 
	 * @return payBank 支付银行（支付方式为网银时有效）
	 */
	public String getPayBank() {
		return this.payBank;
	}

	/**
	 * 设置支付银行（支付方式为网银时有效）.
	 * 
	 * @param payBank
	 *            支付银行（支付方式为网银时有效）
	 */
	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

	/**
	 * 取得商品名称.
	 * 
	 * @return goodsName 商品名称
	 */
	public String getGoodsName() {
		return this.goodsName;
	}

	/**
	 * 设置商品名称.
	 * 
	 * @param goodsName
	 *            商品名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 取得订单备注.
	 * 
	 * @return remark 订单备注
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 设置订单备注.
	 * 
	 * @param remark
	 *            订单备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 取得总金额.
	 * 
	 * @return totalPrice 总金额
	 */
	public Double getTotalFee() {
		return this.totalFee;
	}

	/**
	 * 设置总金额.
	 * 
	 * @param totalPrice
	 *            总金额
	 */
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * 取得订单状态.
	 * 
	 * @return status 订单状态
	 */
	public Long getStatus() {
		return this.status;
	}

	/**
	 * 设置订单状态.
	 * 
	 * @param status
	 *            订单状态
	 */
	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * 取得订单创建时间.
	 * 
	 * @return createDate 订单创建时间
	 */
	public Long getCreateDate() {
		return this.createDate;
	}

	/**
	 * 设置订单创建时间.
	 * 
	 * @param createDate
	 *            订单创建时间
	 */
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	/**
	 * 取得订单创建时间.
	 * 
	 * @return createDateFormat 订单创建时间
	 */
	public Timestamp getCreateDateFormat() {
		return this.createDateFormat;
	}

	/**
	 * 设置订单创建时间.
	 * 
	 * @param createDateFormat
	 *            订单创建时间
	 */
	public void setCreateDateFormat(Timestamp createDateFormat) {
		this.createDateFormat = createDateFormat;
	}

	/**
	 * 取得业务平台ID.
	 * 
	 * @return bpId 业务平台ID
	 */
	public Long getBpId() {
		return this.bpId;
	}

	/**
	 * 设置业务平台ID.
	 * 
	 * @param bpId
	 *            业务平台ID
	 */
	public void setBpId(Long bpId) {
		this.bpId = bpId;
	}

	/**
	 * 取得业务平台订单流水号.
	 * 
	 * @return bpOrderId 业务平台订单流水号
	 */
	public String getBpOrderId() {
		return this.bpOrderId;
	}

	/**
	 * 设置业务平台订单流水号.
	 * 
	 * @param bpOrderId
	 *            业务平台订单流水号
	 */
	public void setBpOrderId(String bpOrderId) {
		this.bpOrderId = bpOrderId;
	}

	/**
	 * 取得商品链接地址.
	 * 
	 * @return showUrl 商品链接地址
	 */
	public String getShowUrl() {
		return this.showUrl;
	}

	/**
	 * 设置商品链接地址.
	 * 
	 * @param showUrl
	 *            商品链接地址
	 */
	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	/**
	 * 取得订单内容.
	 * 
	 * @return content 订单内容
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 设置订单内容.
	 * 
	 * @param content
	 *            订单内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 取得支付成功显示页面.
	 * 
	 * @return notifyUrl 支付成功显示页面
	 */
	public String getNotifyUrl() {
		return this.notifyUrl;
	}

	/**
	 * 设置支付成功显示页面.
	 * 
	 * @param notifyUrl
	 *            支付成功显示页面
	 */
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	/**
	 * 取得支付成功显示页面.
	 * 
	 * @return returnUrl 支付成功显示页面
	 */
	public String getReturnUrl() {
		return this.returnUrl;
	}

	/**
	 * 设置支付成功显示页面.
	 * 
	 * @param returnUrl
	 *            支付成功显示页面
	 */
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	/**
	 * 取得登录帐号.
	 * 
	 * @return account 登录帐号
	 */
	public String getAccount() {
		return this.account;
	}

	/**
	 * 设置登录帐号.
	 * 
	 * @param account
	 *            登录帐号
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 取得充值帐号.
	 * 
	 * @return payAccount 充值帐号
	 */
	public String getPayAccount() {
		return this.payAccount;
	}

	/**
	 * 设置充值帐号.
	 * 
	 * @param payAccount
	 *            充值帐号
	 */
	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	/**
	 * 取得支付成功时间.
	 * 
	 * @return payDate 支付成功时间
	 */
	public Long getPayDate() {
		return this.payDate;
	}

	/**
	 * 设置支付成功时间.
	 * 
	 * @param payDate
	 *            支付成功时间
	 */
	public void setPayDate(Long payDate) {
		this.payDate = payDate;
	}

	/**
	 * 取得支付成功时间.
	 * 
	 * @return payDateFormat 支付成功时间
	 */
	public Timestamp getPayDateFormat() {
		return this.payDateFormat;
	}

	/**
	 * 设置支付成功时间.
	 * 
	 * @param payDateFormat
	 *            支付成功时间
	 */
	public void setPayDateFormat(Timestamp payDateFormat) {
		this.payDateFormat = payDateFormat;
	}

	/**
	 * 取得第三方支付平台的交易流水号.
	 * 
	 * @return paySerialNumber 第三方支付平台的交易流水号
	 */
	public String getPaySerialNumber() {
		return this.paySerialNumber;
	}

	/**
	 * 设置第三方支付平台的交易流水号.
	 * 
	 * @param paySerialNumber
	 *            第三方支付平台的交易流水号
	 */
	public void setPaySerialNumber(String paySerialNumber) {
		this.paySerialNumber = paySerialNumber;
	}

	/**
	 * 取得订单创建时间月.
	 * 
	 * @return createDateMonth 订单创建时间月
	 */
	public Long getCreateDateMonth() {
		return this.createDateMonth;
	}

	/**
	 * 设置订单创建时间月.
	 * 
	 * @param createDateMonth
	 *            订单创建时间月
	 */
	public void setCreateDateMonth(Long createDateMonth) {
		this.createDateMonth = createDateMonth;
	}

	/**
	 * 取得订单创建时间日.
	 * 
	 * @return createDateDay 订单创建时间日
	 */
	public Long getCreateDateDay() {
		return this.createDateDay;
	}

	/**
	 * 设置订单创建时间日.
	 * 
	 * @param createDateDay
	 *            订单创建时间日
	 */
	public void setCreateDateDay(Long createDateDay) {
		this.createDateDay = createDateDay;
	}

	/**
	 * 取得订单创建时间季度.
	 * 
	 * @return createDateQuarter 订单创建时间季度
	 */
	public Long getCreateDateQuarter() {
		return this.createDateQuarter;
	}

	/**
	 * 设置订单创建时间季度.
	 * 
	 * @param createDateQuarter
	 *            订单创建时间季度
	 */
	public void setCreateDateQuarter(Long createDateQuarter) {
		this.createDateQuarter = createDateQuarter;
	}

	/**
	 * 取得订单支付时间月.
	 * 
	 * @return payDateMonth 订单支付时间月
	 */
	public Long getPayDateMonth() {
		return this.payDateMonth;
	}

	/**
	 * 设置订单支付时间月.
	 * 
	 * @param payDateMonth
	 *            订单支付时间月
	 */
	public void setPayDateMonth(Long payDateMonth) {
		this.payDateMonth = payDateMonth;
	}

	/**
	 * 取得订单支付时间日.
	 * 
	 * @return payDateDay 订单支付时间日
	 */
	public Long getPayDateDay() {
		return this.payDateDay;
	}

	/**
	 * 设置订单支付时间日.
	 * 
	 * @param payDateDay
	 *            订单支付时间日
	 */
	public void setPayDateDay(Long payDateDay) {
		this.payDateDay = payDateDay;
	}

	/**
	 * 取得订单支付时间季度.
	 * 
	 * @return payDateQuarter 订单支付时间季度
	 */
	public Long getPayDateQuarter() {
		return this.payDateQuarter;
	}

	/**
	 * 设置订单支付时间季度.
	 * 
	 * @param payDateQuarter
	 *            订单支付时间季度
	 */
	public void setPayDateQuarter(Long payDateQuarter) {
		this.payDateQuarter = payDateQuarter;
	}

	/**
	 * 取得支付用户在passport的ID.
	 * 
	 * @return unid 支付用户在passport的ID
	 */
	public String getUnid() {
		return this.unid;
	}

	/**
	 * 设置支付用户在passport的ID.
	 * 
	 * @param unid
	 *            支付用户在passport的ID
	 */
	public void setUnid(String unid) {
		this.unid = unid;
	}

	/**
	 * 取得异步通知第三方的时间.
	 * 
	 * @return notifyDate 异步通知第三方的时间
	 */
	public Timestamp getNotifyDate() {
		return this.notifyDate;
	}

	/**
	 * 设置异步通知第三方的时间.
	 * 
	 * @param notifyDate
	 *            异步通知第三方的时间
	 */
	public void setNotifyDate(Timestamp notifyDate) {
		this.notifyDate = notifyDate;
	}

	/**
	 * 取得异步通知的次数.
	 * 
	 * @return notifyNum 异步通知的次数
	 */
	public String getNotifyNum() {
		return this.notifyNum;
	}

	/**
	 * 设置异步通知的次数.
	 * 
	 * @param notifyNum
	 *            异步通知的次数
	 */
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

	/**
	 * @return 支付限制类型
	 */
	public String getPayLimit() {
		return payLimit;
	}

	/**
	 * @param payLimit
	 *            支付限制类型
	 */
	public void setPayLimit(String payLimit) {
		this.payLimit = payLimit;
	}

	/**
	 * @return the 支付IP
	 */
	public String getPayIp() {
		return payIp;
	}

	/**
	 * @param payIp
	 *            支付IP
	 */
	public void setPayIp(String payIp) {
		this.payIp = payIp;
	}

	/**
	 * @return 充值账户ID
	 */
	public String getPayUnid() {
		return payUnid;
	}

	/**
	 * @param payUnid
	 *            充值账户ID
	 */
	public void setPayUnid(String payUnid) {
		this.payUnid = payUnid;
	}

	/**
	 * @return 第三方渠道平台商户ID（支付宝为收款账户email）
	 */
	public Long getPayPartner() {
		return payPartner;
	}

	/**
	 * @param payPartner
	 *            第三方渠道平台商户ID（支付宝为收款账户email）
	 */
	public void setPayPartner(Long payPartner) {
		this.payPartner = payPartner;
	}

	/**
	 * @return the channelFeeCost
	 */
	public Double getChannelFeeCost() {
		return channelFeeCost;
	}

	/**
	 * @param channelFeeCost
	 *            the channelFeeCost to set
	 */
	public void setChannelFeeCost(Double channelFeeCost) {
		this.channelFeeCost = channelFeeCost;
	}

	/**
	 * 取得支付服务.用于后端级联下拉查询分类.1:网银直连;2:渠道账户支付;3:手机卡;4:游戏卡;5:移动支付;.
	 * 
	 * @return payService 支付服务.用于后端级联下拉查询分类.1:网银直连;2:渠道账户支付;3:手机卡;4:游戏卡;5:移动支付;
	 */
	public Integer getPayService() {
		return this.payService;
	}

	/**
	 * 设置支付服务.用于后端级联下拉查询分类.1:网银直连;2:渠道账户支付;3:手机卡;4:游戏卡;5:移动支付;.
	 * 
	 * @param payService
	 *            支付服务.用于后端级联下拉查询分类.1:网银直连;2:渠道账户支付;3:手机卡;4:游戏卡;5:移动支付;
	 */
	public void setPayService(Integer payService) {
		this.payService = payService;
	}

	/**
	 * @return the needPayPrice
	 */
	public Double getNeedPayPrice() {
		return needPayPrice;
	}

	/**
	 * @param needPayPrice
	 *            the needPayPrice to set
	 */
	public void setNeedPayPrice(Double needPayPrice) {
		this.needPayPrice = needPayPrice;
	}

	/**
	 * @return the bargainIncome
	 */
	public Double getBargainIncome() {
		return bargainIncome;
	}

	/**
	 * @param bargainIncome
	 *            the bargainIncome to set
	 */
	public void setBargainIncome(Double bargainIncome) {
		this.bargainIncome = bargainIncome;
	}

	/**
	 * @return the realCardPrice
	 */
	public Double getRealCardPrice() {
		return realCardPrice;
	}

	/**
	 * @param realCardPrice
	 *            the realCardPrice to set
	 */
	public void setRealCardPrice(Double realCardPrice) {
		this.realCardPrice = realCardPrice;
	}

	/**
	 * @return the realIncome
	 */
	public Double getRealIncome() {
		return realIncome;
	}

	/**
	 * @param realIncome
	 *            the realIncome to set
	 */
	public void setRealIncome(Double realIncome) {
		this.realIncome = realIncome;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the payNickname
	 */
	public String getPayNickname() {
		return payNickname;
	}

	/**
	 * @param payNickname
	 *            the payNickname to set
	 */
	public void setPayNickname(String payNickname) {
		this.payNickname = payNickname;
	}

	/**
	 * @return the payCurrency
	 */
	public String getPayCurrency() {
		return payCurrency;
	}

	/**
	 * @param payCurrency
	 *            the payCurrency to set
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

	@Override
	public String toString() {
		return "PayTradePO [id=" + id + ", orderTradeNo=" + orderTradeNo
				+ ", payType=" + payType + ", payBank=" + payBank
				+ ", goodsName=" + goodsName + ", remark=" + remark
				+ ", totalFee=" + totalFee + ", status=" + status
				+ ", createDate=" + createDate + ", createDateFormat="
				+ createDateFormat + ", bpId=" + bpId + ", bpOrderId="
				+ bpOrderId + ", showUrl=" + showUrl + ", content=" + content
				+ ", notifyUrl=" + notifyUrl + ", returnUrl=" + returnUrl
				+ ", account=" + account + ", payAccount=" + payAccount
				+ ", payDate=" + payDate + ", payDateFormat=" + payDateFormat
				+ ", paySerialNumber=" + paySerialNumber + ", createDateMonth="
				+ createDateMonth + ", createDateDay=" + createDateDay
				+ ", createDateQuarter=" + createDateQuarter
				+ ", payDateMonth=" + payDateMonth + ", payDateDay="
				+ payDateDay + ", payDateQuarter=" + payDateQuarter + ", unid="
				+ unid + ", notifyDate=" + notifyDate + ", notifyNum="
				+ notifyNum + ", notifyStatus=" + notifyStatus + ", bpParams="
				+ bpParams + ", notifyId=" + notifyId + ", orderUrl="
				+ orderUrl + ", initOrderTerminal=" + initOrderTerminal
				+ ", finalPayTerminal=" + finalPayTerminal + ", payLimit="
				+ payLimit + ", payIp=" + payIp + ", payUnid=" + payUnid
				+ ", payPartner=" + payPartner + ", channelFeeCost="
				+ channelFeeCost + ", payService=" + payService
				+ ", needPayPrice=" + needPayPrice + ", bargainIncome="
				+ bargainIncome + ", realCardPrice=" + realCardPrice
				+ ", realIncome=" + realIncome + ", nickname=" + nickname
				+ ", payNickname=" + payNickname + ", payCurrency="
				+ payCurrency + ", merCode=" + merCode + ", goodsContent="
				+ goodsContent + ", channelFeeCostDetail="
				+ channelFeeCostDetail + ", platformFeeCost=" + platformFeeCost
				+ ", platformFeeCostDetail=" + platformFeeCostDetail
				+ ", payMediumCode=" + payMediumCode + ", username=" + username
				+ ", cashier=" + cashier + ", transactionId=" + transactionId
				+ ", platformPayDate=" + platformPayDate
				+ ", platformPayFormat=" + platformPayFormat + ", couponFee="
				+ couponFee + "]";
	}
}