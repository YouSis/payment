package com.wfj.pay.po;

import java.io.Serializable;
import java.util.Arrays;

public class UserRightsPO implements Serializable {

	private static final long serialVersionUID = -8809571916287171218L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 登陆用户
	 */
	private String userId;

	/**
	 * 业务平台Id
	 */
	private Long bpId;

	/**
	 * 业务平台名称，显示用
	 */
	private String bpName;

	/**
	 * 业务权限列表
	 */
	private String[] bpIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getBpId() {
		return bpId;
	}

	public void setBpId(Long bpId) {
		this.bpId = bpId;
	}

	public String getBpName() {
		return bpName;
	}

	public void setBpName(String bpName) {
		this.bpName = bpName;
	}

	public String[] getBpIds() {
		return bpIds;
	}

	public void setBpIds(String[] bpIds) {
		this.bpIds = bpIds;
	}

	@Override
	public String toString() {
		return "UserRightsPO [id=" + id + ", userId=" + userId + ", bpId=" + bpId + ", bpName=" + bpName + ", bpIds="
				+ Arrays.toString(bpIds) + "]";
	}

}
