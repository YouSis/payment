package com.wfj.pay.rule;

import com.wfj.pay.dto.OrderQueryRequestDTO;
import com.wfj.pay.dto.RefundOrderQueryRequestDTO;
import com.wfj.pay.dto.RuleResultDTO;

/**
 * Created by wjg on 2017/7/3.
 */
public interface IQueryRefundTradeRule extends IBaseRule{

    /**
     * 检查是否存在退单
     * @param refundQueryDTO
     * @return
     */
    RuleResultDTO refundExistValidate(RefundOrderQueryRequestDTO refundQueryDTO);

}
