package com.wfj.pay.constant;

/**
 * Created by wjg on 2017/6/26.
 */
public class PayLogConstant {
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
}
