package com.wfj.pay.service;

import com.wfj.pay.dto.RefundNotifyInfoDTO;
import com.wfj.pay.dto.RefundOrderQueryRequestDTO;
import com.wfj.pay.dto.RefundOrderRequestDTO;
import com.wfj.pay.dto.RefundOrderResponseDTO;
import com.wfj.pay.po.PayRefundTradePO;

import java.util.List;
import java.util.Map;

/**
 * Created by wjg on 2017/6/30.
 */
public interface IPayRefundTradeService {
    /**
     * 根据bpId和bpRefundOrderId查询退款单
     *
     * @param bpId
     * @param bpRefundOrderId
     * @return
     */
    PayRefundTradePO findPayRefundTradePo(Long bpId, String bpRefundOrderId);

    /**
     * 找到最后一笔退款成功的退款单
     *
     * @param orderTradeNo
     * @return
     */
    PayRefundTradePO findLastSuccessRefundTrade(String orderTradeNo);

    /**
     * 根据支付平台的退款单号查询退单
     *
     * @param refundTradeNo
     * @return
     */
    PayRefundTradePO findPayRefundTradePO(String refundTradeNo);

    /**
     * 查询一个正向订单的所有退款单,包括未退款成功的
     *
     * @param orderTradeNo
     * @return
     */
    List<PayRefundTradePO> findByOrderTradeNo(String orderTradeNo);

    /**
     * 创建退款单
     *
     * @param refundOrderRequestDTO
     * @return
     */
    PayRefundTradePO createRefundTrade(RefundOrderRequestDTO refundOrderRequestDTO);

    /**
     * 退款成功之后修改退款单状态已经保存日志
     *
     * @param notifyInfoDTO
     */
    void updateAfterRefundSuccess(RefundNotifyInfoDTO notifyInfoDTO);

    /**
     * 将退款的实体类转转化为DTO
     *
     * @param refundTradeNo
     * @return
     */
    RefundOrderResponseDTO transfer(String refundTradeNo);

    /**
     * 去退款
     *
     * @param refundTradePO
     * @return
     */
    RefundOrderResponseDTO refund(PayRefundTradePO refundTradePO);

    /**
     * 根据正向订单号获取退款的明细
     * @param orderTradeNo
     * @return
     */
    String getRefundDetail(String orderTradeNo);

    /**
     * 退款查询
     * @param refundQueryDTO
     * @return
     */
    RefundOrderResponseDTO refundQuery(RefundOrderQueryRequestDTO refundQueryDTO);
}
