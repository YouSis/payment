package com.wfj.pay.sdk.alipay;

/**
 * Created by kongqf on 2017/7/7.
 */
public class AliPayConstants {
    public static final String DOMAIN_API = "https://openapi.alipay.com/gateway.do";

    public static final String ALIPAY_TRADE_PAY = "alipay.trade.pay";


    /**
     * 阿里异步通知sign签名算法类型
     */
    public static final String ALIPAY_SIGN_TYPE = "RSA";
    /**
     * 阿里异步通知sign签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA256WithRSA";

    public static final String SUCCESS = "10000"; // 支付成功
    public static final String PAYING = "10003"; // 用户支付中（等待用户付款）
    public static final String FAILED = "40004"; // 支付失败
    public static final String ERROR = "20000"; // 系统未知异常


}
