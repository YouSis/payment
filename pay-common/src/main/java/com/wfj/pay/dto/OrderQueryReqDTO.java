package com.wfj.pay.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 订单查询DTO.
 * 
 * @author haowenchao
 */
public class OrderQueryReqDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1641306411595983179L;

	/**
	 * unID.
	 */
	private String unid;

	/**
	 * 开始时间.
	 */
	private Long startTime;

	/**
	 * 结束时间.
	 */
	private Long endTime;

	/**
	 * 其它时间
	 */
	private Integer otherTime;

	/**
	 * 
	 */
	private String account;

	/**
	 * 订单号
	 */
	private String orderTradeNo;
	/**
	 * bpId.
	 */
	private Long bpId;
	/**
	 * bpId集合.
	 */
	private List<Long> bpIds;

	/**
	 * 支付渠道
	 */
	private String payType;

	/**
	 * 订单状态
	 */
	private Long status;

	/**
	 * 页码
	 */
	private int pageNo;

	/**
	 * 每页条数
	 */
	private int pageSize;

	/**
	 * 
	 */
	private Integer rownum;
	/**
	 * 订单初始化终端标识（01:PC端，02:手机端）.
	 */
	private String initOrderTerminal;

	/**
	 * 订单支付终端标识（01:PC端，02:手机端）.
	 */
	private String finalPayTerminal;

	/**
	 * 门店状态
	 */
	private Integer sortType;

	/**
	 * 门店参数
	 */
	private String sortParam;

	private String bpOrderId;

	/**
	 * 支付货币类型.
	 */
	private String payCurrency;
	/**
	 * 充值方式
	 */
	private String payBank;

	private String dicCode;

	private String payService;

	private String userName;

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPayService() {
		return payService;
	}

	public void setPayService(String payService) {
		this.payService = payService;
	}

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getOtherTime() {
		return otherTime;
	}

	public void setOtherTime(Integer otherTime) {
		this.otherTime = otherTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOrderTradeNo() {
		return orderTradeNo;
	}

	public void setOrderTradeNo(String orderTradeNo) {
		this.orderTradeNo = orderTradeNo;
	}

	public Long getBpId() {
		return bpId;
	}

	public void setBpId(Long bpId) {
		this.bpId = bpId;
	}

	public List<Long> getBpIds() {
		return bpIds;
	}

	public void setBpIds(List<Long> bpIds) {
		this.bpIds = bpIds;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getRownum() {
		return rownum;
	}

	public void setRownum(Integer rownum) {
		this.rownum = rownum;
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

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

	public String getSortParam() {
		return sortParam;
	}

	public void setSortParam(String sortParam) {
		this.sortParam = sortParam;
	}

	public String getPayCurrency() {
		return payCurrency;
	}

	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public OrderQueryReqDTO(String unid, Long startTime, Long endTime, Integer otherTime, String account,
			String orderTradeNo, Long bpId, List<Long> bpIds, String payType, Long status, int pageNo, int pageSize,
			Integer rownum, String initOrderTerminal, String finalPayTerminal, Integer sortType, String sortParam,
			String bpOrderId, String payCurrency, String payBank, String dicCode, String payService, String userName,
			String userId) {
		super();
		this.unid = unid;
		this.startTime = startTime;
		this.endTime = endTime;
		this.otherTime = otherTime;
		this.account = account;
		this.orderTradeNo = orderTradeNo;
		this.bpId = bpId;
		this.bpIds = bpIds;
		this.payType = payType;
		this.status = status;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.rownum = rownum;
		this.initOrderTerminal = initOrderTerminal;
		this.finalPayTerminal = finalPayTerminal;
		this.sortType = sortType;
		this.sortParam = sortParam;
		this.bpOrderId = bpOrderId;
		this.payCurrency = payCurrency;
		this.payBank = payBank;
		this.dicCode = dicCode;
		this.payService = payService;
		this.userName = userName;
		this.userId = userId;
	}

	public OrderQueryReqDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getBpOrderId() {
		return bpOrderId;
	}

	public void setBpOrderId(String bpOrderId) {
		this.bpOrderId = bpOrderId;
	}

	public String getPayBank() {
		return payBank;
	}

	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

}
