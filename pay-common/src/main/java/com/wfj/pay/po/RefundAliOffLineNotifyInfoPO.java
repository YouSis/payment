package com.wfj.pay.po;

import java.io.Serializable;

public class RefundAliOffLineNotifyInfoPO implements Serializable {

	private static final long serialVersionUID = -5891483681386245880L;

	/**
	 * 支付宝交易号
	 */
	private String trade_no;
	/**
	 * 商户订单号
	 */
	private String out_trade_no;
	/**
	 * 用户的登录id
	 */
	private String buyer_logon_id;
	/**
	 * 本次退款是否发生了资金变化
	 */
	private String fund_change;
	/**
	 * 本次发生的退款金额
	 */
	private double refund_fee;
	/**
	 * 退款支付时间
	 */
	private String gmt_refund_pay;
	/**
	 * 交易在支付时候的门店名称
	 */
	private String store_name;
	/**
	 * 买家在支付宝的用户id
	 */
	private String buyer_user_id;
	/**
	 * 实际退回给用户的金额
	 */
	private String send_back_fee;
	/**
	 * 返回码
	 */
	private String code;
	/**
	 * 返回说明
	 */
	private String msg;
	/**
	 * 	子错误码
	 */
	private String sub_code;
	/**
	 * 	子错误码描述
	 */
	private String sub_msg;

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getBuyer_logon_id() {
		return buyer_logon_id;
	}

	public void setBuyer_logon_id(String buyer_logon_id) {
		this.buyer_logon_id = buyer_logon_id;
	}

	public String getFund_change() {
		return fund_change;
	}

	public void setFund_change(String fund_change) {
		this.fund_change = fund_change;
	}

	public double getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(double refund_fee) {
		this.refund_fee = refund_fee;
	}

	public String getGmt_refund_pay() {
		return gmt_refund_pay;
	}

	public void setGmt_refund_pay(String gmt_refund_pay) {
		this.gmt_refund_pay = gmt_refund_pay;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getBuyer_user_id() {
		return buyer_user_id;
	}

	public void setBuyer_user_id(String buyer_user_id) {
		this.buyer_user_id = buyer_user_id;
	}

	public String getSend_back_fee() {
		return send_back_fee;
	}

	public void setSend_back_fee(String send_back_fee) {
		this.send_back_fee = send_back_fee;
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

	public String getSub_code() {
		return sub_code;
	}

	public void setSub_code(String sub_code) {
		this.sub_code = sub_code;
	}

	public String getSub_msg() {
		return sub_msg;
	}

	public void setSub_msg(String sub_msg) {
		this.sub_msg = sub_msg;
	}

	@Override
	public String toString() {
		return "RefundAliOffLineNotifyInfo [trade_no=" + trade_no
				+ ", out_trade_no=" + out_trade_no + ", buyer_logon_id="
				+ buyer_logon_id + ", fund_change=" + fund_change
				+ ", refund_fee=" + refund_fee + ", gmt_refund_pay="
				+ gmt_refund_pay + ", store_name=" + store_name
				+ ", buyer_user_id=" + buyer_user_id + ", send_back_fee="
				+ send_back_fee + "]";
	}
}
