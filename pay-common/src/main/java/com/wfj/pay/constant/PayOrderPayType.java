package com.wfj.pay.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单支付渠道常用量.
 * 
 * @author admin
 * @date 2013-04-15 上午10:00:00
 */
public class PayOrderPayType {

	private PayOrderPayType() {

	}

	/**
	 * 支付宝.
	 */
	public final static String ALIPAY = "ALIPAY";
	/**
	 * 支付宝.
	 */
	public final static String ALIPAY_NAME = "支付宝";
	/**
	 * 支付宝手机.
	 */
	public final static String ALIPAY_MOBILE = "ALIPAY_MOBILE";

	/**
	 * 财付通.
	 */
	public final static String TENPAY = "TENPAY";
	/**
	 * 财付通.
	 */
	public final static String TENPAY_NAME = "财付通";
	/**
	 * 财付通.
	 */
	public final static String TENPAY_MOBILE = "TENPAY_MOBILE";

	/**
	 * 银联.
	 */
	public final static String NETPAY = "NETPAY";
	
	/**
	 * 银联.
	 */
	public final static String NETPAY_NAME = "银联";
	
	/**
	 * 银联.
	 */
	public final static String NETPAY_MOBILE = "NETPAY_MOBILE";
	
    /**
     * 工商银行
     */
	public final static String ICBCPAY = "ICBCPAY";

	/**
	 * 工商银行
	 */
	public final static String ICBCPAY_NAME = "工商银行";

	 /**
     * 招商银行
     */
	public final static String CMBPAY = "CMBPAY";

	/**
	 * 招商银行
	 */
	public final static String CMBPAY_NAME = "招商银行";

	/**
	 * 广发银行
	 * 
	 */
	public final static String CGBPAY = "CGBPAY";
	
	/**
	 * 广发银行
	 */
	public final static String CGBPAY_NAME = "广发银行";
		
	/**
	 * 微信
	 * 
	 */
	public final static String WECHATPAY = "WECHATPAY";
	
	/**
	 * 微信
	 */
	public final static String WECHATPAY_NAME = "微信";
	
	/**
	 * 微信
	 */
	public final static String WECHATPAY_MOBILE = "WECHATPAY_MOBILE";
	
	/**
	 * 微信扫货邦
	 */
	public final static String WECHATPAY_SHB = "WECHATPAY_SHB";
	
	/**
	 * 微信扫货邦
	 */
	public final static String WECHATPAY_SHB_NAME = "微信扫货邦";
	
	/**
	 * 支付宝线下
	 */
	public final static String ALIPAY_OFFLINE = "ALIPAY_OFFLINE" ;
	
	/**
	 * 支付宝线下
	 */
	public final static String ALIPAY_OFFLINE_NAME = "支付宝线下支付";
	
	/**
	 * 微信线下支付
	 */
	public final static String 	WECHATPAY_OFFLINE = "WECHATPAY_OFFLINE" ;	
	
	/**
	 * 微信线下支付
	 */
	public final static String 	WECHATPAY_OFFLINE_NAME = "微信线下支付" ;	
	/**
	/**
	 * 配置信息
	 */
	private static Map<String, String> payTypeMap = new HashMap<String, String>();

	static {
		payTypeMap.put(ALIPAY, ALIPAY_NAME);
		payTypeMap.put(ALIPAY_MOBILE, ALIPAY_NAME);
		payTypeMap.put(TENPAY, TENPAY_NAME);
		payTypeMap.put(TENPAY_MOBILE, TENPAY_NAME);
		payTypeMap.put(NETPAY, NETPAY_NAME);
		payTypeMap.put(NETPAY_MOBILE, NETPAY_NAME);
		payTypeMap.put(ICBCPAY, ICBCPAY_NAME);
		payTypeMap.put(CGBPAY, CGBPAY_NAME);
		payTypeMap.put(CMBPAY, CMBPAY_NAME);
		payTypeMap.put(WECHATPAY, WECHATPAY_NAME);
		payTypeMap.put(WECHATPAY_MOBILE, WECHATPAY_NAME);
		payTypeMap.put(WECHATPAY_SHB, WECHATPAY_SHB_NAME);
		payTypeMap.put(ALIPAY_OFFLINE, ALIPAY_OFFLINE_NAME);
		payTypeMap.put(WECHATPAY_OFFLINE, WECHATPAY_OFFLINE_NAME);
	}

	/**
	 * @return the payTypeMap
	 */
	public static Map<String, String> getPayTypeMap() {
		return payTypeMap;
	}

	/**
	 * 根据代码获得名称.
	 * 
	 * @param code
	 * @return
	 * @author admin
	 * @date 2013-04-15 上午10:00:00
	 */
	public static String getNameByCode(String code) {
		return payTypeMap.get(code);
	}

}
