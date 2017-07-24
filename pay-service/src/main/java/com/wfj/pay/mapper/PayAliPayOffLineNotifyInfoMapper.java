package com.wfj.pay.mapper;


import com.wfj.pay.po.PayAliPayOffLineNotifyInfoPO;
import org.springframework.stereotype.Repository;

/**
 * 保存结果信息dao
 * @author Admin
 *
 */
@Repository
public interface PayAliPayOffLineNotifyInfoMapper {

	/**
	 *
	 * @param po
     */
	public void saveNotifyInfo(PayAliPayOffLineNotifyInfoPO po);
}
