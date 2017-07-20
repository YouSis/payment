/**
 * 
 */
package com.wfj.pay.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.sql.Timestamp;

/** 
 * @author  ghost
 * @version 创建时间：2016年3月10日 上午8:17:38 
 * 类说明 		退款订单日志PO类
 */
@Document(indexName = "pay-data",type = "pay-refund-log")
public class PayRefundLogEsPO implements Serializable{

	private static final long serialVersionUID = 5877416500109783614L;
	/**
	 * 主键
	 */
	@Id
	private Long id;
	/**
	 * 日志内容
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String content;
	/**
	 * 创建日期
	 */
	@Field(type = FieldType.Date,index = FieldIndex.not_analyzed)
	private Timestamp createDateTime;
	/**
	 * 业务状态   1 成功  2 失败
	 */
	@Field(type = FieldType.Long,index = FieldIndex.not_analyzed)
	private Long status;
	/**
	 * 支付平台正向支付订单号码
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String orderTradeNo;
	/**
	 * 支付平台逆向退款订单号码
	 */
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String refundTradeNo;


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

	public Timestamp getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
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
}


