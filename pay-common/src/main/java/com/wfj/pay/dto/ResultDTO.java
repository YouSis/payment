package com.wfj.pay.dto;

public class ResultDTO {
	private String code;
	private String msg;
	
	
	public ResultDTO() {
	}
	public ResultDTO(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResultDTO [code=");
		builder.append(code);
		builder.append(", msg=");
		builder.append(msg);
		builder.append("]");
		return builder.toString();
	}
	
}
