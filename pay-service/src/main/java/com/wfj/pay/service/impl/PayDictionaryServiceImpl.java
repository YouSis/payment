package com.wfj.pay.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfj.pay.mapper.PayDictionaryMapper;
import com.wfj.pay.po.PayDictionaryPO;
import com.wfj.pay.service.IPayDictionaryService;

@Service
public class PayDictionaryServiceImpl implements IPayDictionaryService{
	private Logger LOGGER = LoggerFactory.getLogger(PayDictionaryServiceImpl.class);

	@Autowired
	private PayDictionaryMapper dictionaryMapper;
	/**
	 * 查询支付渠道字典
	 */
	@Override
	public List<PayDictionaryPO> selectPayDictionary() {
		List<PayDictionaryPO> poList = dictionaryMapper.selectPayDictionary();
		return poList;
	}

}
