package com.wfj.pay.constant;

/**
 * Created by wjg on 2017/6/30.
 */
public class PayRefundTradeStatus {
    /**
     * 等待退款.
     */
    public static final Long WAIT_REFUND = 1L;
    public static final String WAIT_REFUND_NAME = "等待退款";
    /**
     * 已退款.
     */
    public static final Long REFUND = 2L;
    public static final String REFUND_NAME = "已退款";
    /**
     * 退款失败.
     */
    public static final Long REFUND_FAIL = 3L;
    public static final String REFUND_FAIL_NAME = "退款失败";
}
