package com.wfj.pay.dto;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 统计DTO
 * @author Administrator
 * @date 2016年11月8日 下午4:44:19
 */
public class PayMentDateDTO implements Serializable {
	
	private static final long serialVersionUID = 1256198542233510290L;
	private String storeNo;
	private String storeName;
	private String payTotalFee = "0.0000";
	private Long payToalCount = 0L;
	private String refundTotalFee = "0.0000";
	private Long refundTotalCount = 0L;
	private String couponTotalFee = "0.00";
	
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public String getPayTotalFee() {
		return payTotalFee;
	}
	public void setPayTotalFee(Double payTotalFee) {
		
		this.payTotalFee = new BigDecimal(String.valueOf(payTotalFee)).divide(new BigDecimal("10000")).setScale(4, BigDecimal.ROUND_HALF_UP).toString();
	}
	public Long getPayToalCount() {
		return payToalCount;
	}
	public void setPayToalCount(Long payToalCount) {
		this.payToalCount = payToalCount;
	}
	public String getRefundTotalFee() {
		return refundTotalFee;
	}
	public void setRefundTotalFee(Double refundTotalFee) {
		this.refundTotalFee = new BigDecimal(String.valueOf(refundTotalFee)).divide(new BigDecimal("10000")).setScale(4, BigDecimal.ROUND_HALF_UP).toString();
	}
	public Long getRefundTotalCount() {
		return refundTotalCount;
	}
	public void setRefundTotalCount(Long refundTotalCount) {
		this.refundTotalCount = refundTotalCount;
	}
	public String getCouponTotalFee() {
		return couponTotalFee;
	}
	public void setCouponTotalFee(Double couponTotalFee) {
		this.couponTotalFee = new BigDecimal(String.valueOf(couponTotalFee)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
	@Override
	public String toString() {
		return "PayMentDateDTO [storeNo=" + storeNo + ", storeName=" + storeName + ", payTotalFee=" + payTotalFee
				+ ", payToalCount=" + payToalCount + ", refundTotalFee=" + refundTotalFee + ", refundTotalCount="
				+ refundTotalCount + ", couponTotalFee=" + couponTotalFee + "]";
	}
	
	
}
