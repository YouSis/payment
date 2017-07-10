package com.wfj.pay.rule.impl;

import com.wfj.pay.constant.PayTradeStatus;
import com.wfj.pay.dto.OrderCloseRequestDTO;
import com.wfj.pay.dto.RuleResultDTO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.rule.ICloseTradeRule;
import com.wfj.pay.service.IPayTradeService;
import com.wfj.pay.utils.OrderEncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 关闭订单的规则校验实现类
 * Created by wjg on 2017/6/29.
 */
@Component
public class CloseTradeRuleImpl implements ICloseTradeRule {
    @Autowired
    private IPayTradeService tradeService;
    @Override
    public RuleResultDTO orderExistValidate(OrderCloseRequestDTO orderCloseRequestDTO) {
        return orderExistsValidate(orderCloseRequestDTO.getBpId(), orderCloseRequestDTO.getBpOrderId(), orderCloseRequestDTO.getOrderTradeNo());
    }

    @Override
    public RuleResultDTO md5Validate(OrderCloseRequestDTO orderCloseRequestDTO) {
        RuleResultDTO ruleResultDTO = new RuleResultDTO();
        if (!OrderEncryptUtils.checkCloseOrderSign(orderCloseRequestDTO)) {
            ruleResultDTO.setErrorMessage("签名错误,请检查参数");
            ruleResultDTO.setSuccess(false);
        }
        return ruleResultDTO;
    }

    @Override
    public RuleResultDTO orderStatusValidate(OrderCloseRequestDTO orderCloseRequestDTO) {
        RuleResultDTO ruleResultDTO = new RuleResultDTO();
        PayTradePO tradePO = tradeService.findByBpOrderIdOrOrderTradeNo(Long.valueOf(orderCloseRequestDTO.getBpId()), orderCloseRequestDTO.getBpOrderId(), orderCloseRequestDTO.getOrderTradeNo());
        if(PayTradeStatus.PAYED.equals(tradePO.getStatus())){
            ruleResultDTO.setErrorMessage("订单已经支付，不能关闭");
            ruleResultDTO.setSuccess(false);
        }
        return ruleResultDTO;
    }
}
