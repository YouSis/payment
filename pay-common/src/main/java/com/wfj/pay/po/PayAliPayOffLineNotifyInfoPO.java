package com.wfj.pay.po;

import java.io.Serializable;

public class PayAliPayOffLineNotifyInfoPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2676899693898195767L;
	/**
	 * 支付宝交易号
	 */
	private String trade_no;
	/**
	 * 订单流水号
	 */
	private String out_trade_no;
	/**
	 * 买家支付宝用户号  该参数已废弃，请不要使用
	 */
	private String open_id;
	/**
	 * 买家支付宝账号
	 */
	private String buyer_logon_id;
	/**
	 * 交易金额
	 */
	private double total_amount;
	/**
	 * 实收金额
	 */
	private double receipt_amount;
	/**
	 * 买家付款的金额
	 */
	private double buyer_pay_amount;
	/**
	 * 使用积分宝付款的金额
	 */
	private double point_amount;
	/**
	 * 交易中可给用户开具发票的金额
	 */
	private double invoice_amount;
	/**
	 * 交易支付时间
	 */
	private String send_pay_date;
	/**
	 * 交易支付时间
	 */
	private String gmt_payment;
	/**
	 * 交易支付使用的资金单据列表 
	 */
	private String fund_bill_list;
	/**
	 * 支付宝卡余额
	 */
	private double card_balance;
	/**
	 * 发生支付交易的商户门店名称 
	 */
	private String store_name;
	/**
	 * 买家在支付宝的用户id
	 */
	private String buyer_user_id;
	/**
	 * 本次交易支付所使用的单品券优惠的商品优惠信息
	 */
	private String discount_goods_detail;
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
	/**
	 * 通知类型.
	 */
	private Integer notifyType;
	
	//撤销订单需要的字段
	/**
	 * 	是否需要重试
	 */
	private String retry_flag;
	/**
	 * 
	 */
	private String action;

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

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public String getBuyer_logon_id() {
		return buyer_logon_id;
	}

	public void setBuyer_logon_id(String buyer_logon_id) {
		this.buyer_logon_id = buyer_logon_id;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public double getReceipt_amount() {
		return receipt_amount;
	}

	public void setReceipt_amount(double receipt_amount) {
		this.receipt_amount = receipt_amount;
	}

	public double getBuyer_pay_amount() {
		return buyer_pay_amount;
	}

	public void setBuyer_pay_amount(double buyer_pay_amount) {
		this.buyer_pay_amount = buyer_pay_amount;
	}

	public double getPoint_amount() {
		return point_amount;
	}

	public void setPoint_amount(double point_amount) {
		this.point_amount = point_amount;
	}

	public double getInvoice_amount() {
		return invoice_amount;
	}

	public void setInvoice_amount(double invoice_amount) {
		this.invoice_amount = invoice_amount;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSend_pay_date() {
		return send_pay_date;
	}

	public void setSend_pay_date(String send_pay_date) {
		this.send_pay_date = send_pay_date;
	}

	public String getFund_bill_list() {
		return fund_bill_list;
	}

	public void setFund_bill_list(String fund_bill_list) {
		this.fund_bill_list = fund_bill_list;
	}

	public double getCard_balance() {
		return card_balance;
	}

	public void setCard_balance(double card_balance) {
		this.card_balance = card_balance;
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

	public String getDiscount_goods_detail() {
		return discount_goods_detail;
	}

	public void setDiscount_goods_detail(String discount_goods_detail) {
		this.discount_goods_detail = discount_goods_detail;
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

	public Integer getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(Integer notifyType) {
		this.notifyType = notifyType;
	}

	public String getRetry_flag() {
		return retry_flag;
	}

	public void setRetry_flag(String retry_flag) {
		this.retry_flag = retry_flag;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getGmt_payment() {
		return gmt_payment;
	}

	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}

	@Override
	public String toString() {
		return "PayAliPayOffLineNotifyInfoPO{" +
				"trade_no='" + trade_no + '\'' +
				", out_trade_no='" + out_trade_no + '\'' +
				", open_id='" + open_id + '\'' +
				", buyer_logon_id='" + buyer_logon_id + '\'' +
				", total_amount=" + total_amount +
				", receipt_amount=" + receipt_amount +
				", buyer_pay_amount=" + buyer_pay_amount +
				", point_amount=" + point_amount +
				", invoice_amount=" + invoice_amount +
				", send_pay_date='" + send_pay_date + '\'' +
				", gmt_payment='" + gmt_payment + '\'' +
				", fund_bill_list='" + fund_bill_list + '\'' +
				", card_balance=" + card_balance +
				", store_name='" + store_name + '\'' +
				", buyer_user_id='" + buyer_user_id + '\'' +
				", discount_goods_detail='" + discount_goods_detail + '\'' +
				", code='" + code + '\'' +
				", msg='" + msg + '\'' +
				", sub_code='" + sub_code + '\'' +
				", sub_msg='" + sub_msg + '\'' +
				", notifyType=" + notifyType +
				", retry_flag='" + retry_flag + '\'' +
				", action='" + action + '\'' +
				'}';
	}
}
