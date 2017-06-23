package com.wfj.pay.mapper;

import com.wfj.pay.po.PayChannelPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wjg on 2017/6/22.
 */
@Repository
public interface PayChannelMapper {

    void insert(PayChannelPO payChannelPO);

    List<PayChannelPO> queryChannelList(Map<String, Object> paramMap);

    PayChannelPO getPayChannelByBPDicCodePayService(@Param("bp_id") Long bpId,
                                                    @Param("dic_code") String dicCode,
                                                    @Param("client_type") String clientType,
                                                    @Param("pay_service") Integer payService);
}
