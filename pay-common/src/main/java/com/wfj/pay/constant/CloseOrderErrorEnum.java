package com.wfj.pay.constant;

public enum CloseOrderErrorEnum {

	CLOSE_ORDER_PARAM_NULL("C001", "缺少必须参数！"),
	CLOSE_ORDER_PAY_ORDER_NULL("C002", "不存在该订单！"),
	CLOSE_ORDER_SIGN_ORDER("C003", "加密信息错误！"),
	CLOSE_ORDER_ORDER_PAYMENT("C004", "该订单已付款,不能关闭！"),
	CLOSE_ORDER_ERROR("C005", "关闭失败！"),
	CLOSE_ORDER_SYSTEM_ERROR("C006", "系统错误！");

	private String code;
	private String msg;

	private CloseOrderErrorEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public static String getMsg(String code) {
		for (CloseOrderErrorEnum errorEnum : CloseOrderErrorEnum.values()) {
			if (code.equals(errorEnum.getCode())) {
				return errorEnum.getMsg();
			}
		}
		return null;
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

}
