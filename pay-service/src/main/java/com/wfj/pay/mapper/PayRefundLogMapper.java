package com.wfj.pay.mapper;

import com.wfj.pay.po.PayRefundLogPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wjg on 2017/6/22.
 */
@Repository
public interface PayRefundLogMapper {

    void insert(PayRefundLogPO payRefundLogPO);

    List<PayRefundLogPO> selectByRefundTradeNo(@Param("refundTradeNo") String refundTradeNo);
}
