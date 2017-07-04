package com.wfj.pay.rule;

import com.oracle.tools.packager.JreUtils;
import com.wfj.pay.dto.OrderQueryRequestDTO;
import com.wfj.pay.dto.RuleResultDTO;

/**
 * Created by wjg on 2017/6/28.
 */
public interface IQueryTradeRule extends IBaseRule {

    /**
     * 校验查询的订单是否存在
     * @param orderQueryRequestDTO
     * @return
     */
    RuleResultDTO orderExistValidate(OrderQueryRequestDTO orderQueryRequestDTO);

    /**
     * 校验payType是否与订单一致
     * @param orderQueryRequestDTO
     * @return
     */
    RuleResultDTO payTypeValidate(OrderQueryRequestDTO orderQueryRequestDTO);
}
