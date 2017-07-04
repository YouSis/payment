package com.wfj.pay.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 交易订单状态常量类.
 * 
 * @author admin
 * 
 */
public final class PayTradeStatus {

	/**
	 * 私有构造.
	 */
	private PayTradeStatus() {

	}

	/**
	 * 状态map.
	 */
	private static Map<Long, String> statusMap = new HashMap<Long, String>();
	/**
	 * 等待付款.
	 */
	public static final Long WAIT_PAY = 1L;
	public static final String WAIT_PAY_NAME = "等待付款";
	/**
	 * 已付款.
	 */
	public static final Long PAYED = 2L;
	public static final String PAYED_NAME = "已付款";
	/**
	 * 已取消.
	 */
	public static final Long CANCELED = 3L;
	public static final String CANCELED_NAME = "已关闭";

	
	/**
	 * 退款状态map.
	 */
	private static Map<Long, String> refundStatusMap = new HashMap<Long, String>();
	
	/**
	 * 等待退款.
	 */
	public static final Long WAIT_REFUND = 1L;
	public static final String WAIT_REFUND_NAME = "等待退款";
	
	/**
	 * 退款成功.
	 */
	public static final Long REFUNDED = 2L;
	public static final String REFUNDED_NAME = "退款成功";
	
	
	static {
		statusMap.put(WAIT_PAY, WAIT_PAY_NAME);
		statusMap.put(PAYED, PAYED_NAME);
		statusMap.put(CANCELED, CANCELED_NAME);

        refundStatusMap.put(WAIT_REFUND, WAIT_REFUND_NAME);
        refundStatusMap.put(REFUNDED, REFUNDED_NAME);
	}

	/**
	 * @return the status
	 */
	public static Map<Long, String> getStatusMap() {
		return statusMap;
	}

	/**
	 * 根据代号获得名称.
	 * 
	 * @param code
	 * @return
	 * @author admin
	 * @date 2013-04-15 上午10:00:00
	 */
	public static String getNameByCode(Long code) {
		return statusMap.get(code);
	}
	
	/**
	 * @return the status
	 */
	public static Map<Long, String> getRefundStatusMap() {
		return refundStatusMap;
	}

	/**
	 * 根据代号获得名称.
	 * 
	 * @param code
	 * @return
	 * @author admin
	 * @date 2013-04-15 上午10:00:00
	 */
	public static String getRefundNameByCode(Long code) {
		return refundStatusMap.get(code);
	}

}
