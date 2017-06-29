package com.wfj.pay.rule;

import com.wfj.pay.dto.OrderQueryRequestDTO;
import com.wfj.pay.dto.RuleResultDTO;

/**
 * Created by wjg on 2017/6/28.
 */
public interface IQueryTradeRule extends IBaseRule {

    RuleResultDTO orderExistValidate(OrderQueryRequestDTO orderQueryRequestDTO);
}
