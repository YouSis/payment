package com.wfj.pay.service;

import java.util.List;

import com.wfj.pay.dto.PayDictionaryDTO;
import com.wfj.pay.po.PayDictionaryPO;

/**
 * 支付渠道字典
 * @author jh
 *
 */
public interface IPayDictionaryService {

	/**
	 * 查询支付渠道字典
	 * @return
	 */
	List<PayDictionaryPO> selectPayDictionary();

}
