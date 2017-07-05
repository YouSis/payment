/**
 * 
 */
package com.wfj.pay.po;

/** 
 * @author  ghost
 * @version 创建时间：2016年1月24日 下午10:07:48 
 * 类说明 		微信异常通知PO类
 */
public class PayWeChatNotifyInfoPO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6956181607891674355L;
	/**
	 * 返回状态码  SUCCESS/FAIL
	 */
	private String return_code;
	/**
	 * 返回信息 比如：签名失败
	 */
	private String return_msg;
	
	//以下字段在return_code为SUCCESS时有返回
	/**
	 * 公众账号ID
	 */
	private String appid;
	/**
	 * 商户号
	 */
	private String mch_id;
	/**
	 * 设备号
	 */
	private String device_info;
	/**
	 * 随机字符串	
	 */
	private String nonce_str;
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 业务结果   SUCCESS/FAIL
	 */
	private String result_code;
	/**
	 * 错误代码
	 */
	private String err_code;
	/**
	 * 错误代码描述
	 */
	private String err_code_des;
	/**
	 * 用户在商户appid下的唯一标识
	 */
	private String openid;
	/**
	 * 交易类型   JSAPI、NATIVE、APP
	 */
	private String trade_type;
	/**
	 * 付款银行
	 */
	private String bank_type;
	/**
	 * 订单总金额，单位为分
	 */
	private String total_fee;
	/**
	 * 货币种类 : CNY代表人民币
	 */
	private String fee_type;
	/**
	 * 现金支付金额
	 */
	private String cash_fee;
	/**
	 * 现金支付货币类型
	 */
	private String cash_fee_type;
	/**
	 * 代金券或立减优惠金额
	 * 代金券或立减优惠金额<=订单总金额，订单总金额-代金券或立减优惠金额=现金支付金额
	 */
	private String coupon_fee;
	/**
	 * 后期自主添加优惠金额字段
	 */
	private String couponFee;
	/**
	 * 代金券或立减优惠使用数量
	 */
	private String coupon_count;
	/**
	 * 微信支付订单号
	 */
	private String transaction_id;
	/**
	 * 商户订单号
	 */
	private String out_trade_no;
	/**
	 * 商家数据包
	 */
	private String attach;
	/**
	 * 支付完成时间:20141030133525  格式为yyyyMMddHHmmss
	 */
	private String time_end;
	/**
	 * 通知类型.
	 */
	private String notifyType;
	/**
	 * 以下字段在return_code 和result_code 都为SUCCESS 的时候有返回
	 */
    private String trade_state = "";
    /**
     * 是否关注了公众号
     */
    private String is_subscribe = "";

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getCash_fee() {
		return cash_fee;
	}

	public void setCash_fee(String cash_fee) {
		this.cash_fee = cash_fee;
	}

	public String getCash_fee_type() {
		return cash_fee_type;
	}

	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}

	public String getCoupon_fee() {
		return coupon_fee;
	}

	public void setCoupon_fee(String coupon_fee) {
		this.coupon_fee = coupon_fee;
	}

	public String getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(String couponFee) {
		this.couponFee = couponFee;
	}

	public String getCoupon_count() {
		return coupon_count;
	}

	public void setCoupon_count(String coupon_count) {
		this.coupon_count = coupon_count;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getTrade_state() {
		return trade_state;
	}

	public void setTrade_state(String trade_state) {
		this.trade_state = trade_state;
	}

	public String getIs_subscribe() {
		return is_subscribe;
	}

	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
}


