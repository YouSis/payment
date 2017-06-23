package com.wfj.pay.rule;

import com.wfj.pay.dto.OrderRequestDTO;
import com.wfj.pay.dto.RuleResultDTO;

/**
 * Created by wjg on 2017/6/23.
 */
public interface IOrderRule {

     RuleResultDTO doCheck(OrderRequestDTO orderRequestDTO,RuleResultDTO ruleResultDTO);

}
