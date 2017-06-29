package com.wfj.pay.service;

import com.wfj.pay.constant.PayTypeEnum;
import com.wfj.pay.dto.OrderResponseDTO;
import com.wfj.pay.dto.PayTradeDTO;
import com.wfj.pay.po.PayTradePO;

/**
 * Created by wjg on 2017/6/26.
 */
public interface IPayStrategyService {
    /**
     * 是否策略匹配
     * @param payTypeEnum
     * @return
     */
    boolean match(PayTypeEnum payTypeEnum);

    /**
     * 去支付返回支付结果
     * @param payTradeDTO
     * @return
     */
    OrderResponseDTO toPay(PayTradeDTO payTradeDTO);

    /**
     * 查询订单
     * @param payTradePO
     * @return
     */
    OrderResponseDTO queryOrder(PayTradePO payTradePO);
}
