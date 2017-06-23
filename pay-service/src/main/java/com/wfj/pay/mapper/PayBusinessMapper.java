package com.wfj.pay.mapper;

import com.wfj.pay.po.PayBusinessPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wjg on 2017/6/22.
 */
@Repository
public interface PayBusinessMapper {

    int insert(PayBusinessPO payBusinessPO);

    void delete(int id);

    PayBusinessPO selectByBpId(@Param("id") Long bpId);

    List<PayBusinessPO> selectAll(Map<String, Object> para);

    void update(PayBusinessPO payBusinessPO);
}
