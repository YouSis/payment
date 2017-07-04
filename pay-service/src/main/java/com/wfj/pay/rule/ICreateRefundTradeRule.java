package com.wfj.pay.rule;

import com.wfj.pay.dto.RefundOrderRequestDTO;
import com.wfj.pay.dto.RuleResultDTO;

/**
 * Created by wjg on 2017/6/30.
 */
public interface ICreateRefundTradeRule extends IBaseRule {
    /**
     * 订单是否存在的校验
     * @param requestDTO
     * @return
     */
    RuleResultDTO orderExistValidate(RefundOrderRequestDTO requestDTO);

    /**
     * 校验退款单是否已经存在
     * @param requestDTO
     * @return
     */
    RuleResultDTO refundOrderExistValidate(RefundOrderRequestDTO requestDTO);
    /**
     * 校验是否有可退金额
     * @param requestDTO
     * @return
     */
    RuleResultDTO feeValidate(RefundOrderRequestDTO requestDTO);

    /**
     * md5加密校验
     * @param requestDTO
     * @return
     */
    RuleResultDTO md5Validate(RefundOrderRequestDTO requestDTO);
}
