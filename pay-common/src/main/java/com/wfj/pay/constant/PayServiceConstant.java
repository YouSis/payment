package com.wfj.pay.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付服务类型常量.
 * 
 * @author admin
 * @date 2013-12-2 下午2:05:22
 */
public class PayServiceConstant {

	private PayServiceConstant() {

	}

    public static Map<Integer, String> param = new HashMap<Integer, String>() ;

	/**
	 * 网银直连.
	 */
	public static final Integer NET_BANK = 1;

	/**
	 * 渠道账户支付.
	 */
	public static final Integer CHANNEL_DIRECT = 2;

    /**
     * 网银直连名称.
     */
    public static final String NET_BANK_NAME = "网银直连" ;

    /**
     * 渠道账户支付名称.
     */
    public static final String CHANNEL_DIRECT_NAME = "渠道账户支付" ;


    static {
        param.put(NET_BANK, NET_BANK_NAME) ;
        param.put(CHANNEL_DIRECT, CHANNEL_DIRECT_NAME) ;
    }

    public static String getPayServiceName(Integer payService) {
        if (payService == null || payService == 0) {
            return null ;
        }
        String payServiceName = param.get(payService) ;
        if (payServiceName != null && payServiceName.length() != 0) {
            return payServiceName ;
        }
        return null ;
    }

}
