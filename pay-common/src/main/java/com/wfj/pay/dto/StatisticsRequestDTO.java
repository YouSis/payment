package com.wfj.pay.dto;

import java.util.List;

/**
 * 统计模块查询参数DTO.
 * 
 * @author 渠道查询的请求bean
 * @date 2014-4-8 上午10:13:30
 */
public class StatisticsRequestDTO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6722900386321525274L;
	/**
	 * 当前页.
	 */
	private Integer pageNo;
	/**
	 * 页大小.
	 */
	private Integer pageSize;
	/**
	 * 查询开始时间.
	 */
	private Long startTime;
	/**
	 * 查询结束时间.
	 */
	private Long endTime;
	/**
	 * 统计间隔时间.
	 */
	private Integer groupTime;
	/**
	 * 业务平台ID.
	 */
	private Long bpId;
	/**
	 * 创建订单平台.
	 */
	private String initOrderTerminal;
	/**
	 * 统计内容编码.
	 */
	private Integer statList;
	/**
	 * 用户拥有的业务系统id权限.
	 */
	private List<Long> allowedBpIds;
	/**
	 * 货币.
	 */
	private String payCurrency;
	/**
	 * ops控制权限
	 */
	private String userId;

	public StatisticsRequestDTO() {

	}

	
	

	public StatisticsRequestDTO(Integer pageNo, Integer pageSize,
			Long startTime, Long endTime, Integer groupTime, Long bpId,
			String initOrderTerminal, Integer statList,
			List<Long> allowedBpIds, String payCurrency, String userId) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.startTime = startTime;
		this.endTime = endTime;
		this.groupTime = groupTime;
		this.bpId = bpId;
		this.initOrderTerminal = initOrderTerminal;
		this.statList = statList;
		this.allowedBpIds = allowedBpIds;
		this.payCurrency = payCurrency;
		this.userId = userId;
	}




	public String getUserId() {
		return userId;
	}




	public void setUserId(String userId) {
		this.userId = userId;
	}




	/**
	 * @return the pageNo
	 */
	public Integer getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the startTime
	 */
	public Long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the groupTime
	 */
	public Integer getGroupTime() {
		return groupTime;
	}

	/**
	 * @param groupTime
	 *            the groupTime to set
	 */
	public void setGroupTime(Integer groupTime) {
		this.groupTime = groupTime;
	}

	/**
	 * @return the bpId
	 */
	public Long getBpId() {
		return bpId;
	}

	/**
	 * @param bpId
	 *            the bpId to set
	 */
	public void setBpId(Long bpId) {
		this.bpId = bpId;
	}

	/**
	 * @return the initOrderTerminal
	 */
	public String getInitOrderTerminal() {
		return initOrderTerminal;
	}

	/**
	 * @param initOrderTerminal
	 *            the initOrderTerminal to set
	 */
	public void setInitOrderTerminal(String initOrderTerminal) {
		this.initOrderTerminal = initOrderTerminal;
	}

	/**
	 * @return the statList
	 */
	public Integer getStatList() {
		return statList;
	}

	/**
	 * @param statList
	 *            the statList to set
	 */
	public void setStatList(Integer statList) {
		this.statList = statList;
	}

	/**
	 * @return the allowedBpIds
	 */
	public List<Long> getAllowedBpIds() {
		return allowedBpIds;
	}

	/**
	 * @param allowedBpIds
	 *            the allowedBpIds to set
	 */
	public void setAllowedBpIds(List<Long> allowedBpIds) {
		this.allowedBpIds = allowedBpIds;
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




	@Override
	public String toString() {
		return "StatisticsRequestDTO [pageNo=" + pageNo + ", pageSize="
				+ pageSize + ", startTime=" + startTime + ", endTime="
				+ endTime + ", groupTime=" + groupTime + ", bpId=" + bpId
				+ ", initOrderTerminal=" + initOrderTerminal + ", statList="
				+ statList + ", allowedBpIds=" + allowedBpIds
				+ ", payCurrency=" + payCurrency + ", userId=" + userId + "]";
	}

}