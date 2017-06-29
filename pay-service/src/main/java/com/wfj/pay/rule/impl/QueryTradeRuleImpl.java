package com.wfj.pay.rule.impl;

import com.wfj.pay.dto.OrderQueryRequestDTO;
import com.wfj.pay.dto.RuleResultDTO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.rule.IQueryTradeRule;
import com.wfj.pay.service.IPayTradeService;
import com.wfj.pay.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wjg on 2017/6/28.
 */
@Component
public class QueryTradeRuleImpl implements IQueryTradeRule {
    @Autowired
    private IPayTradeService payTradeService;
    @Override
    public RuleResultDTO orderExistValidate(OrderQueryRequestDTO orderQueryRequestDTO) {
        RuleResultDTO ruleResultDTO = new RuleResultDTO();
        PayTradePO payTradePO;
        //校验订单是否存在
        if(StringUtils.isNotEmpty(orderQueryRequestDTO.getOrderTradeNo())){
            payTradePO = payTradeService.findByBpIdAndOrderTradeNo(Long.valueOf(orderQueryRequestDTO.getBpId()), orderQueryRequestDTO.getOrderTradeNo());
            if(payTradePO == null){
                ruleResultDTO.setErrorMessage("该门店不存在订单号为"+orderQueryRequestDTO.getOrderTradeNo()+"的订单");
                ruleResultDTO.setSuccess(false);
            }
        }else{
            payTradePO = payTradeService.findByBpIdAndBpOrderId(Long.valueOf(orderQueryRequestDTO.getBpId()), orderQueryRequestDTO.getBpOrderId());
            if(payTradePO == null){
                ruleResultDTO.setErrorMessage("该门店不存在小票号为"+orderQueryRequestDTO.getOrderTradeNo()+"的订单");
                ruleResultDTO.setSuccess(false);
            }
        }
        //校验支付方式是否一致
        //如果不穿payType则不校验，为了兼容PAD
        if(StringUtils.isNotEmpty(orderQueryRequestDTO.getPayType())){
            if(!payTradePO.getPayType().equals(orderQueryRequestDTO.getPayType())){
                ruleResultDTO.setErrorMessage("查询的支付方式与实际支付方式不一致");
                ruleResultDTO.setSuccess(false);
            }
        }
        return ruleResultDTO;
    }
}
