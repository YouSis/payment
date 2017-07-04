package com.wfj.pay.mapper;

import com.wfj.pay.po.PayRefundWeChatOfNotifyInfoPO;
import org.springframework.stereotype.Repository;

/**
 * Created by wjg on 2017/7/3.
 */
@Repository
public interface PayRefundWeChatOfNotifyInfoMapper {
    void insert(PayRefundWeChatOfNotifyInfoPO refundWeChatOfNotifyInfoPO);
}
