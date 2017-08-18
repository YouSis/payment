package com.wfj.pay.service;

import java.util.List;
import java.util.Map;

import com.wfj.pay.dto.MerchantRequestDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.po.PayMerchantPO;

/**
 * 商户信息service接口
 * @author jh
 *
 */
public interface IPayMerchantService {

	/**
	 * 分页查询商户信息
	 * @param id
	 * @param name
	 * @return
	 */
	PaginationDTO<PayMerchantPO> findMerchant(MerchantRequestDTO requestDTO);

	/**
	 * 根据id查询商户信息
	 * @param id
	 * @return
	 */
	PayMerchantPO findById(Long id);
	
	/**
	 * 新增商户
	 * @param merchantDTO
	 */
	void add(MerchantRequestDTO merchantDTO);

	/**
	 * 修改商户信息
	 * @param merchantDTO
	 */
	void update(MerchantRequestDTO merchantDTO);

	/**
	 * 查询门店
	 * @return
	 */
	List<PayMerchantPO> selectMerCode();

}
