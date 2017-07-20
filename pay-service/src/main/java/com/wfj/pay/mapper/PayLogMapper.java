package com.wfj.pay.mapper;

import com.wfj.pay.po.PayLogPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wjg on 2017/6/22.
 */
@Repository
public interface PayLogMapper {

    void insert(PayLogPO payLogPO);

    List<PayLogPO> selectByOrderTradeNo(@Param("orderTradeNo") String orderTradeNo);
}
