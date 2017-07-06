package com.wfj.pay.dubbo;

/**
 * Created by wjg on 2017/7/6.
 */
public interface IPayScheduleDubbo {
    /**
     * 未支付订单的超时关闭
     */
    void doCloseTimeOut();
}
