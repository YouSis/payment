package com.wfj.pay.mapper;

import com.wfj.pay.dto.PayTradeDTO;
import com.wfj.pay.po.PayTradePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by wjg on 2017/6/22.
 */
@Repository
public interface PayTradeMapper {

    void insert(PayTradePO payTradePO);

    void update(PayTradePO orderPO);

    PayTradePO selectByOrderTradeNo(String orderTradeNo);

    PayTradePO selectByBpIdAndBpOrderId(@Param("bpId") Long bpId, @Param("bpOrderId") String bpOrderId);

    PayTradePO selectByBpIdAndOrderTradeNo(@Param("bpId")Long bpId,@Param("orderTradeNo") String orderTradeNo);

    int updateOrderPayType(Map<String, Object> para);

    void updateOrderAfterPaySuccess(PayTradePO payTradePO);

    void updateOrderStatus(@Param("orderTradeNo") String orderTrade,@Param("status") Long status);
}
