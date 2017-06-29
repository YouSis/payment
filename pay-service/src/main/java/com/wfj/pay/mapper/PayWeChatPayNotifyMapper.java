package com.wfj.pay.mapper;

import com.wfj.pay.po.PayWeChatNotifyInfoPO;
import org.springframework.stereotype.Repository;

/**
 * Created by wjg on 2017/6/28.
 */
@Repository
public interface PayWeChatPayNotifyMapper {

    void insert(PayWeChatNotifyInfoPO payWeChatNotifyInfoPO);
}
