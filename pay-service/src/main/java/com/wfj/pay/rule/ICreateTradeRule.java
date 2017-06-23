package com.wfj.pay.rule;

import com.wfj.pay.dto.OrderRequestDTO;
import com.wfj.pay.dto.RuleResultDTO;

/**
 * Created by wjg on 2017/6/23.
 */
public interface ICreateTradeRule extends  IBaseRule{
    /**
     * md5加密校验
     * @param orderRequestDTO
     * @return
     */
    RuleResultDTO  md5Validate(OrderRequestDTO orderRequestDTO);

    /**
     * 重复创建校验
     * @param orderRequestDTO
     * @return
     */
    RuleResultDTO  repeatCreateValidate(OrderRequestDTO orderRequestDTO);

    /**
     * 重复提交校验
     * @param orderRequestDTO
     * @return
     */
    RuleResultDTO  repeatSubmitValidate(OrderRequestDTO orderRequestDTO);

    /**
     * 收银台配置校验，看是否配置了此种支付方式
     * @param orderRequestDTO
     * @return
     */
    RuleResultDTO  payChannelValidate(OrderRequestDTO orderRequestDTO);
}
