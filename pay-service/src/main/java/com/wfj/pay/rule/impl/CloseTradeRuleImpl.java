package com.wfj.pay.rule.impl;

import com.wfj.pay.dto.OrderCloseRequestDTO;
import com.wfj.pay.dto.RuleResultDTO;
import com.wfj.pay.rule.ICloseTradeRule;
import com.wfj.pay.service.IPayTradeService;
import com.wfj.pay.utils.OrderEncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 关闭订单的规则校验实现类
 * Created by wjg on 2017/6/29.
 */
public class CloseTradeRuleImpl implements ICloseTradeRule {

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
}
