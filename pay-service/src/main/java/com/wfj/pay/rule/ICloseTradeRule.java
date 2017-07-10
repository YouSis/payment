package com.wfj.pay.rule;

import com.wfj.pay.dto.OrderCloseRequestDTO;
import com.wfj.pay.dto.OrderQueryRequestDTO;
import com.wfj.pay.dto.RuleResultDTO;

/**
 * Created by wjg on 2017/6/29.
 */
public interface ICloseTradeRule extends IBaseRule {

    RuleResultDTO orderExistValidate(OrderCloseRequestDTO orderCloseRequestDTO);

    RuleResultDTO md5Validate(OrderCloseRequestDTO orderCloseRequestDTO);

    RuleResultDTO orderStatusValidate(OrderCloseRequestDTO orderCloseRequestDTO);
}
