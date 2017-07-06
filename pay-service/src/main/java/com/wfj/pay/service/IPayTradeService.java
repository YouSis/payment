package com.wfj.pay.service;

import com.wfj.pay.dto.OrderRequestDTO;
import com.wfj.pay.dto.OrderResponseDTO;
import com.wfj.pay.dto.PayNotifyInfoDTO;
import com.wfj.pay.dto.PayTradeDTO;
import com.wfj.pay.po.PayTradePO;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wjg on 2017/6/23.
 */
public interface IPayTradeService {
    /**
     * 根据bpId和bpOrderId查找订单
     * @param bpId
     * @param bpOrderId
     * @return
     */
    PayTradePO findByBpIdAndBpOrderId(Long bpId,String bpOrderId);

    /**
     * 根据bpId和orderTradeNo查找订单
     * @param bpId
     * @param orderTradeNo
     * @return
     */
    PayTradePO findByBpIdAndOrderTradeNo(Long bpId,String orderTradeNo);

    /**
     * 如果orderTradeNo不为空用orderTradeNo查，否则用bpOrderId查
     * @param bpId
     * @param bpOrderId
     * @param orderTradeNo
     * @return
     */
    PayTradePO findByBpOrderIdOrOrderTradeNo(Long bpId,String bpOrderId,String orderTradeNo);

    /**
     * 根据支付平台订单号查询订单信息
     * @param orderTradeNo
     * @return
     */
    PayTradePO findByOrderTradeNo(String orderTradeNo);

    /**
     * 根据时间搓的范围查找未支付的订单列表
     * @param beginTimeStamp
     * @param endTimeStamp
     * @return
     */
    List<PayTradePO> findByTime(long beginTimeStamp, long endTimeStamp);

    /**
     * 创建订单
     * @param orderRequestDTO
     * @return
     */
    PayTradeDTO createOrder(OrderRequestDTO orderRequestDTO);

    /**
     * 支付成功之后修改订单状态、支付时间等
     * @param payNotifyInfoDTO
     */
    void updateOrderAfterPaySuccess(PayNotifyInfoDTO payNotifyInfoDTO);

    /**
     * 支付成功之后的实体类转换成OrderResponseDTO
     * @param orderTradeNo
     * @return
     */
    OrderResponseDTO transfer(String orderTradeNo);

    /**
     * 关闭成功之后的处理，更新订单状态、记录日志
     * @param orderTradeNo
     */
    void doAfterCloseSuccess(String orderTradeNo,String operateSource);

    /**
     * 去支付
     * @param payTradeDTO
     * @return
     */
    OrderResponseDTO pay(PayTradeDTO payTradeDTO);

    /**
     * 去查询
     * @param tradePO
     * @return
     */
    OrderResponseDTO query(PayTradePO tradePO);

    /**
     * 去关闭
     * @param tradePO
     * @return
     */
    OrderResponseDTO close(PayTradePO tradePO,String source);
}
