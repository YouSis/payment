package com.wfj.pay.service;

import com.wfj.pay.po.PayLogPO;
import com.wfj.pay.po.PayRefundLogPO;

import java.util.List;
import java.util.Map;

/**
 * Created by wjg on 2017/6/26.
 */
public interface IPayLogService {
    /**
     * 保存日志
     * @param logMap
     * @param modelKey
     * @param status
     */
    void saveLog(Map<String,Object> logMap, String modelKey, String status);

    /**
     * 保存退款日志
     * @param logMap
     * @param modelKey
     * @param status
     */
    void saveRefundLog(Map<String,Object> logMap, String modelKey, String status);

    /**
     * 根据订单号查询日志
     * @param orderTradeNo
     * @return
     */
    List<PayLogPO> findByOrderTradeNo(String orderTradeNo);

    /**
     * 根据退款单号查询退款日志
     * @param refundTradeNo
     * @return
     */
    List<PayRefundLogPO> findByRefundTradeNo(String refundTradeNo);
}
