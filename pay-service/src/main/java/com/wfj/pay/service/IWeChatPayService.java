package com.wfj.pay.service;

import com.wfj.pay.dto.OrderResponseDTO;
import com.wfj.pay.dto.RefundOrderResponseDTO;

import java.util.Map;

/**
 * Created by wjg on 2017/7/5.
 */
public interface IWeChatPayService {
    /**
     * 微信支付成功之后的操作
     * @param resultMap
     * @return
     */
    OrderResponseDTO doAfterWeChatSuccess(Map<String, String> resultMap);

    /**
     * 微信退款成功之后的操作
     * @param resultMap
     * @return
     */
    RefundOrderResponseDTO doAfterRefundSuccess(Map<String, String> resultMap);
}
