package com.wfj.pay.mapper;

import com.wfj.pay.po.PayPartnerAccountPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wjg on 2017/6/22.
 */
@Repository
public interface PayPartnerAccountMapper {

    void insert(PayPartnerAccountPO payPartnerAccountPO);

    void update(PayPartnerAccountPO payPartnerAccountPO);

    PayPartnerAccountPO selectById(@Param("id") Long id);
}
