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

    PayChannelPO getPayChannelByBPDicCodePayService(@Param("bpId") Long bpId,
                                                    @Param("dicCode") String dicCode,
                                                    @Param("clientType") String clientType,
                                                    @Param("payService") Integer payService);
}
