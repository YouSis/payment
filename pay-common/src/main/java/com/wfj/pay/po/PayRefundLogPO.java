/**
 * 
 */
package com.wfj.pay.po;

import java.io.Serializable;
import java.sql.Timestamp;

/** 
 * @author  ghost
 * @version 创建时间：2016年3月10日 上午8:17:38 
 * 类说明 		退款订单日志PO类
 */
public class PayRefundLogPO implements Serializable{

	private static final long serialVersionUID = 5877416500109783614L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 日志内容
	 */
	private String content;
	/**
	 * 创建日期
	 */
	private Timestamp createDate;
	/**
	 * 业务状态   1 成功  2 失败
	 */
	private Long status;
	/**
	 * 支付平台正向支付订单号码
	 */
	private String orderTradeNo;
	/**
	 * 支付平台逆向退款订单号码
	 */
	private String refundTradeNo;
	
	
	
	public PayRefundLogPO() {
	}
	
	
	public PayRefundLogPO(Long id, String content, Timestamp createDate,
			Long status, String orderTradeNo, String refundTradeNo) {
		super();
		this.id = id;
		this.content = content;
		this.createDate = createDate;
		this.status = status;
		this.orderTradeNo = orderTradeNo;
		this.refundTradeNo = refundTradeNo;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getOrderTradeNo() {
		return orderTradeNo;
	}
	public void setOrderTradeNo(String orderTradeNo) {
		this.orderTradeNo = orderTradeNo;
	}
	public String getRefundTradeNo() {
		return refundTradeNo;
	}
	public void setRefundTradeNo(String refundTradeNo) {
		this.refundTradeNo = refundTradeNo;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PayRefundLogPO [id=");
		builder.append(id);
		builder.append(", content=");
		builder.append(content);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", status=");
		builder.append(status);
		builder.append(", orderTradeNo=");
		builder.append(orderTradeNo);
		builder.append(", refundTradeNo=");
		builder.append(refundTradeNo);
		builder.append("]");
		return builder.toString();
	}
	
}


