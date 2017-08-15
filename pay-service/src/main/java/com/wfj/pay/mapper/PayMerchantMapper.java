package com.wfj.pay.mapper;

import com.wfj.pay.po.PayMerchantPO;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wjg on 2017/6/22.
 */
@Repository
public interface PayMerchantMapper {

    int insert(PayMerchantPO payMerchantPO);

    PayMerchantPO selectOne(Map<String, Object> para);

    List<PayMerchantPO> selectPage(@Param("name") String name);

    int update(PayMerchantPO payMerchantPO);
    
    List<PayMerchantPO> selectMerCode();
}
