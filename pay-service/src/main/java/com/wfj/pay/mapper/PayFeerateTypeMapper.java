package com.wfj.pay.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wfj.pay.po.PayFeeRateTypePO;

@Repository
public interface PayFeerateTypeMapper {

	List<PayFeeRateTypePO> selectFeeRateType(Map<String, Object> param);

}
