package com.wfj.pay;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.wfj.pay.dto.PayDictionaryDTO;
import com.wfj.pay.po.PayDictionaryPO;

public class CopTest {

	public static void main(String[] args) {
		List<PayDictionaryDTO> dtoList = new ArrayList<PayDictionaryDTO>();
		List<PayDictionaryPO> poList = new ArrayList<PayDictionaryPO>();
		PayDictionaryDTO dto = new PayDictionaryDTO();
		dto.setId(123423L);
		PayDictionaryDTO dto2 = new PayDictionaryDTO();
		dto2.setId(123423L);
		PayDictionaryDTO dto3 = new PayDictionaryDTO();
		dto3.setId(123423L);
		PayDictionaryDTO dto4 = new PayDictionaryDTO();
		dto4.setId(123423L);
		PayDictionaryDTO dto5 = new PayDictionaryDTO();
		dto5.setId(123423L);
		
		dtoList.add(dto);
		dtoList.add(dto2);
		dtoList.add(dto3);
		dtoList.add(dto4);
		dtoList.add(dto5);
		
		BeanUtils.copyProperties(dtoList, poList);
		System.out.println(dtoList);
		System.out.println(poList);
		PayDictionaryPO po = new PayDictionaryPO();
		BeanUtils.copyProperties(dto, po);
		System.out.println(po);
	}
}
