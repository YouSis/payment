package com.wfj.pay.rule.impl;

import com.wfj.pay.dto.OrderQueryRequestDTO;
import com.wfj.pay.dto.RefundOrderQueryRequestDTO;
import com.wfj.pay.dto.RuleResultDTO;
import com.wfj.pay.po.PayRefundTradePO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.rule.IQueryRefundTradeRule;
import com.wfj.pay.service.IPayRefundTradeService;
import com.wfj.pay.service.IPayTradeService;
import com.wfj.pay.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wjg on 2017/7/3.
 */
@Component
public class QueryRefundTradeRuleImpl implements IQueryRefundTradeRule {
    @Autowired
    private IPayRefundTradeService refundTradeService;
    @Autowired
    private IPayTradeService tradeService;
    @Override
    public RuleResultDTO refundExistValidate(RefundOrderQueryRequestDTO refundQueryDTO) {
        RuleResultDTO ruleResultDTO = new RuleResultDTO();
        PayTradePO tradePO;
        PayRefundTradePO refundTradePO;
        if(StringUtils.isNotEmpty(refundQueryDTO.getOrderTradeNo())){
            tradePO= tradeService.findByBpIdAndOrderTradeNo(Long.valueOf(refundQueryDTO.getBpId()), refundQueryDTO.getOrderTradeNo());
            if(tradePO == null){
                ruleResultDTO.setSuccess(false);
                ruleResultDTO.setErrorMessage("支付平台订单号为"+refundQueryDTO.getOrderTradeNo()+"不存在，请检查。");
                return ruleResultDTO;
            }
            refundTradePO = refundTradeService.findLastSuccessRefundTrade(refundQueryDTO.getOrderTradeNo());
            if(refundTradePO==null){
                ruleResultDTO.setSuccess(false);
                ruleResultDTO.setErrorMessage("支付平台订单号为"+refundQueryDTO.getOrderTradeNo()+"下目前还没有退款成功的退款单,请检查");
                return ruleResultDTO;
            }
        }else{
            refundTradePO = refundTradeService.findPayRefundTradePo(Long.valueOf(refundQueryDTO.getBpId()), refundQueryDTO.getBpRefundOrderId());
            if(refundTradePO==null){
                ruleResultDTO.setSuccess(false);
                ruleResultDTO.setErrorMessage("退款小票号为"+refundQueryDTO.getBpRefundOrderId()+"的退款单不存在，请检查。");
                return ruleResultDTO;
            }
        }
        tradePO = tradeService.findByOrderTradeNo(refundTradePO.getOrderTradeNo());

        //如果不穿payType则不校验，为了兼容PAD
        if (StringUtils.isNotEmpty(refundQueryDTO.getPayType())) {
            if (!tradePO.getPayType().equals(refundQueryDTO.getPayType())) {
                ruleResultDTO.setErrorMessage("查询的支付方式与实际支付方式不一致");
                ruleResultDTO.setSuccess(false);
            }
        }
        return ruleResultDTO;
    }
}
