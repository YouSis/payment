package com.wfj.pay.dto;

import com.wfj.pay.po.PayRefundLogEsPO;
import com.wfj.pay.po.PayRefundTradeEsPO;

import java.util.List;

/**
 * Created by wjg on 2017/7/19.
 */
public class PayRefundDataDTO {
    private PayRefundTradeEsPO refundTradePO;
    private List<PayRefundLogEsPO> refundLogs;

    public PayRefundDataDTO() {
    }

    public PayRefundDataDTO(PayRefundTradeEsPO refundTradePO, List<PayRefundLogEsPO> refundLogs) {
        this.refundTradePO = refundTradePO;
        this.refundLogs = refundLogs;
    }

    public PayRefundTradeEsPO getRefundTradePO() {
        return refundTradePO;
    }

    public void setRefundTradePO(PayRefundTradeEsPO refundTradePO) {
        this.refundTradePO = refundTradePO;
    }

    public List<PayRefundLogEsPO> getRefundLogs() {
        return refundLogs;
    }

    public void setRefundLogs(List<PayRefundLogEsPO> refundLogs) {
        this.refundLogs = refundLogs;
    }
}
