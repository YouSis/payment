package com.wfj.pay.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfj.pay.po.PayDictionaryPO;

@Repository
public interface PayDictionaryMapper {

	List<PayDictionaryPO> selectPayDictionary();

	PayDictionaryPO selectOneByName(String name);

	List<PayDictionaryPO> selectPayDictionaryAll();

	
}
