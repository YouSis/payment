package com.wfj.pay.dto;

import java.io.Serializable;

public class PayPasswdDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3080479590268827725L;
	private String uid;
	private String passwd;
	private String passwd2;
	private String oldPasswd;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getPasswd2() {
		return passwd2;
	}
	public void setPasswd2(String passwd2) {
		this.passwd2 = passwd2;
	}
	public String getOldPasswd() {
		return oldPasswd;
	}
	public void setOldPasswd(String oldPasswd) {
		this.oldPasswd = oldPasswd;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PayPasswdDTO [uid=");
		builder.append(uid);
		builder.append(", passwd=");
		builder.append(passwd);
		builder.append(", passwd2=");
		builder.append(passwd2);
		builder.append(", oldPasswd=");
		builder.append(oldPasswd);
		builder.append("]");
		return builder.toString();
	}
	
}
