package com.wfj.pay.mapper;


import com.wfj.pay.po.RefundAliOffLineNotifyInfoPO;
import org.springframework.stereotype.Repository;

/**
 * 线下ali退款结果持久化Dao
 *
 * @author Admin
 */
@Repository
public interface RefundAliOffLineNotifyInfoMapper {

    public void saveNotifyInfo(RefundAliOffLineNotifyInfoPO po);
}
