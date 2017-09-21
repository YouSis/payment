package com.wfj.pay.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.cache.PayCacheHandle;
import com.wfj.pay.dto.MerchantRequestDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.mapper.PayMerchantMapper;
import com.wfj.pay.po.PayMerchantPO;
import com.wfj.pay.service.IPayMerchantService;
import com.wfj.pay.utils.ObjectUtil;

/**
 * 商户信息service接口实现类
 * @author jh
 *
 */
@Service
public class PayMerchantServiceImpl implements IPayMerchantService {
	private Logger LOGGER = LoggerFactory.getLogger(PayMerchantServiceImpl.class);
	
	@Autowired
	private PayMerchantMapper merchantMapper;

	/**
	 * 分页查询商户信息
	 */
	@Override
	@DataSource("slave")
	public PaginationDTO<PayMerchantPO> findMerchant(MerchantRequestDTO requestDTO) {
		PaginationDTO<PayMerchantPO> pagination = new PaginationDTO<PayMerchantPO>();
		PageHelper.startPage(requestDTO.getPageNo(), requestDTO.getPageSize());
		List<PayMerchantPO> merchantList = merchantMapper.selectPage(requestDTO.getName());
		
		pagination.setPageNo(requestDTO.getPageNo());
		pagination.setPageSize(requestDTO.getPageSize());
		pagination.setTotalPages(((Page<PayMerchantPO>) merchantList).getPages());
		pagination.setTotalHit((int) ((Page<PayMerchantPO>) merchantList).getTotal());
		pagination.setPageStartRow(((Page<PayMerchantPO>) merchantList).getStartRow());
		pagination.setPageEndRow(((Page<PayMerchantPO>) merchantList).getEndRow());
		pagination.setListData(merchantList);
		pagination.calculatePage();
		return pagination;
	}

	/**
	 * 根据id查询商户信息
	 */
	@Override
	@DataSource("slave")
	public PayMerchantPO findById(Long id) {
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("id", id);
		PayMerchantPO merchantPO = merchantMapper.selectOne(para);
		return merchantPO;
	}
	

	/**
	 * 新增商户信息
	 */
	@Override
	@DataSource("master")
    @Transactional
	public void add(MerchantRequestDTO merchantDTO) {
		PayMerchantPO merchantPO = new PayMerchantPO();
		BeanUtils.copyProperties(merchantDTO, merchantPO);
		try {
			merchantMapper.insert(merchantPO);
		} catch (Exception e) {
			LOGGER.info("ops支付页签约商户管理页面新增商户保存商户操作出错");
			LOGGER.error(e.toString(), e);
		}
		
		
	}

	/**
	 * 修改商户信息
	 */
	@Override
	@DataSource("master")
    @Transactional
	public void update(MerchantRequestDTO merchantDTO) {
		PayMerchantPO merchantPO = new PayMerchantPO();
		BeanUtils.copyProperties(merchantDTO, merchantPO);
		
		try {
			merchantMapper.update(merchantPO);
		} catch (Exception e) {
			LOGGER.info("修改商户信息出错操作出错");
			LOGGER.error(e.toString(), e);
		}
	}

	@Override
	@DataSource("slave")
	public List<PayMerchantPO> selectMerCode() {
		List<PayMerchantPO> merchantPOList = merchantMapper.selectMerCode();
		return merchantPOList;
	}

	@Override
	@DataSource("slave")
	public List<PayMerchantPO> selectMerCodeByMerCode(List<String> merCodes) {
		List<PayMerchantPO> merchantPOList = merchantMapper.selectMerCodeByMerCode(merCodes);
		return merchantPOList;
	}

}
