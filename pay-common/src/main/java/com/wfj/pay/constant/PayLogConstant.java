package com.wfj.pay.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wjg on 2017/6/26.
 */
public class PayLogConstant {
    //支付交易日志状态
    public static final String SUCCESS_NAME = "1";
    public static final String SUCCESS_CH_NAME = "成功";
    public static final String FAIL_NAME = "2";
    public static final String FAIL_CN_NAME = "失败";
    //日志的来源
    public static final String OPERATE_SOURCE_SCHEDULE="0";
    public static final String OPERATE_SOURCE_SCHEDULE_NAME="调度执行";
    public static final String OPERATE_SOURCE_PERSON = "1";
    public static final String OPERATE_SOURCE_PERSON_NAME = "人为操作";
    /**
     * 创建订单的日志模板
     */
    public static final String PAY_STEP_CREATE = "【<strong>wfjPay接收业务平台订单请求</strong>】<br>业务平台ID:<font color=\"blue\">#{bpId}</font>,业务平台名称:<font color=\"blue\">#{bpName}</font>,业务平台订单号:<font color=\"blue\">#{bpOrderId}</font>,登录UID:<font color=\"blue\">#{unid}</font>,wfj支付平台订单流水号:<font color=\"blue\">#{orderTradeNo}</font>,订单总金额:<font color=\"blue\">#{totalFee}</font>元,订单内容:<font color=\"blue\">#{content}</font>,商品名称:<font color=\"blue\">#{goodsName}</font>,商品链接地址:<font color=\"blue\">#{showUrl}</font>,订单状态:<font color=\"blue\">#{status}</font>.";
    /**
     * 请求支付的日志模板
     */
    public static final String PAY_STEP_TOPAY = "【<strong>wfjPay请求第三方支付平台</strong>】<br>订单流水号:<font color=\"blue\">#{orderTradeNo}</font>,支付类型:<font color=\"blue\">#{payType}</font>,支付银行:<font color=\"blue\">#{payBank}</font>,订单总金额:<font color=\"blue\">#{totalFee}</font>元,订单状态:<font color=\"blue\">#{status}</font>.";
    /**
     * 返回支付结果的日志模板
     */
    public static final String PAY_STEP_NOTIFY = "【<strong>wfjPay收到第三方支付平台同步通知</strong>】<br>订单流水号:<font color=\"blue\">#{orderTradeNo}</font>,支付银行:<font color=\"blue\">#{payBank}</font>,交易号:<font color=\"blue\">#{paySerialNumber}</font>,订单总金额:<font color=\"blue\">#{totalFee}</font>元,订单状态:<font color=\"blue\">#{status}</font>.";
    /**
     * 关闭订单的日志模板
     */
    public static final String PAY_STEP_CLOSE = "【<strong>wfjPay业务平台关闭订单</strong>】<br>订单流水号:<font color=\"blue\">#{orderTradeNo}</font>,业务平台ID:<font color=\"blue\">#{bpId}</font>,业务平台订单流水号:<font color=\"blue\">#{bpOrderId}</font>,操作方式为：<font color=\"blue\">#{operateSource}</font>.";
    /**
     * 创建退款单日志模板
     */
    public static final String REFUND_SETP_CREATE="【<strong>wfjPay接收业务平台退款订单请求</strong>】<br>业务平台名称:<font color=\"blue\">#{bpName}</font>,业务平台退款单号:<font color=\"blue\">#{bpRefundOrderId}</font>,wfj支付平台退款单流水号:<font color=\"blue\">#{refundTradeNo}</font>,退款单金额:<font color=\"blue\">#{refundFee}</font>元,退款单状态:<font color=\"blue\">#{status}</font>.";
    /**
     * 退款成功的日志模板
     */
    public static final String REFUND_STEP_SUCCESS="【<strong>wfjPay接收业务平台退款成功</strong>】<br>业务平台退款单号:<font color=\"blue\">#{bpRefundOrderId}</font>,wfj支付平台退款单流水号:<font color=\"blue\">#{refundTradeNo}</font>,退款单金额:<font color=\"blue\">#{refundFee}</font>元,退款单状态:<font color=\"blue\">#{status}</font>.";
    
  //日志状态
    public static Map<String,String> statusMap = new HashMap<String,String>();
	
	static {
		statusMap.put(SUCCESS_NAME, SUCCESS_CH_NAME);
		statusMap.put(FAIL_NAME, FAIL_CN_NAME);
	}
}
