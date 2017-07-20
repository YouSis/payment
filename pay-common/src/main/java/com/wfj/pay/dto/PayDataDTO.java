package com.wfj.pay.dto;

import com.wfj.pay.po.PayLogEsPO;
import com.wfj.pay.po.PayLogPO;
import com.wfj.pay.po.PayTradeEsPO;
import com.wfj.pay.po.PayTradePO;

import java.util.List;

/**
 * Created by wjg on 2017/7/19.
 */
public class PayDataDTO {
    private PayTradeEsPO payTradePO;
    private List<PayLogEsPO> payLogPOList;

    public PayDataDTO() {
    }

    public PayDataDTO(PayTradeEsPO payTradePO, List<PayLogEsPO> payLogPOList) {
        this.payTradePO = payTradePO;
        this.payLogPOList = payLogPOList;
    }

    public PayTradeEsPO getPayTradePO() {
        return payTradePO;
    }

    public void setPayTradePO(PayTradeEsPO payTradePO) {
        this.payTradePO = payTradePO;
    }

    public List<PayLogEsPO> getPayLogPOList() {
        return payLogPOList;
    }

    public void setPayLogPOList(List<PayLogEsPO> payLogPOList) {
        this.payLogPOList = payLogPOList;
    }
}
