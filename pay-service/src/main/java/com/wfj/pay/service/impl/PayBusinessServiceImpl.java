package com.wfj.pay.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.cache.PayCacheHandle;
import com.wfj.pay.dto.BankChannelDTO;
import com.wfj.pay.dto.BusinessDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.PartnerAccountDTO;
import com.wfj.pay.dto.SelectBankDTO;
import com.wfj.pay.dto.SelectOptionDTO;
import com.wfj.pay.mapper.PayBusinessMapper;
import com.wfj.pay.po.PayBusinessPO;
import com.wfj.pay.service.IPayBusinessService;
import com.wfj.pay.utils.MD5Util;

/**
 * Created by wjg on 2017/6/23.
 */
@Service
public class PayBusinessServiceImpl implements IPayBusinessService {
    @Autowired
    private PayBusinessMapper businessMapper;
    @Override
    @DataSource("slave")
    public PayBusinessPO findByBpId(Long bpId) {
        return businessMapper.selectByBpId(bpId);
    }
    
    /**
     * 分页查询业务平台
     */
	@Override
	@DataSource("slave")
	public PaginationDTO<PayBusinessPO> selectBusinessByPage(BusinessDTO businessDTO) {
		PaginationDTO<PayBusinessPO> pagination = new PaginationDTO<PayBusinessPO>();
		
		PageHelper.startPage(businessDTO.getPageNo(), businessDTO.getPageSize());
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("bpName", businessDTO.getBpName());
		para.put("status", businessDTO.getStatus());
		para.put("id", businessDTO.getId());
		List<PayBusinessPO> businessPOList = businessMapper.selectAll(para);
		
		pagination.setPageNo(businessDTO.getPageNo());
		pagination.setPageSize(businessDTO.getPageSize());
		pagination.setTotalPages(((Page<PayBusinessPO>) businessPOList).getPages());
		pagination.setTotalHit((int) ((Page<PayBusinessPO>) businessPOList).getTotal());
		pagination.setPageStartRow(((Page<PayBusinessPO>) businessPOList).getStartRow());
		pagination.setPageEndRow(((Page<PayBusinessPO>) businessPOList).getEndRow());
		pagination.setListData(businessPOList);
		pagination.calculatePage();

		return pagination;
	}

	/**
	 * 根据bpid集合查询业务平台
	 */
	@Override
	@DataSource("slave")
	public List<SelectOptionDTO> findOptionByBpId(List<Long> bpIds) {
		List<SelectOptionDTO> optionList = new ArrayList<SelectOptionDTO>();
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("status", "1");
		para.put("ids", bpIds);
		List<PayBusinessPO> businessPOList = businessMapper.selectAll(para);
		
		if(businessPOList.size() > 1) {
			SelectOptionDTO optionDTO = new SelectOptionDTO();
			optionDTO.setId("0");
			optionDTO.setName("全部");
			optionList.add(optionDTO);
		}
		businessPOList.forEach(business -> {
			SelectOptionDTO optionDTO = new SelectOptionDTO();
			optionDTO.setId(business.getId().toString());
			optionDTO.setName(business.getBpName().toString());
			optionList.add(optionDTO);
		});
		
		return optionList;
	}

	/**
	 * 新增业务平台
	 */
	@Override
	@DataSource("master")
    @Transactional
	public void add(BusinessDTO businessDTO) {
		Timestamp lastDate = new Timestamp(System.currentTimeMillis());
		businessDTO.setBpKey(MD5Util.md5(businessDTO.getBpName() + lastDate.toString()));
		businessDTO.setLastDate(lastDate);
		businessDTO.setCreateDate(lastDate);
		
		PayBusinessPO businessPO = new PayBusinessPO();
		BeanUtils.copyProperties(businessDTO, businessPO);
		
		businessMapper.insert(businessPO);
		
		PayCacheHandle.setBusinessPO(businessPO);
	}

	/**
	 * 修改业务平台
	 */
	@Override
	@DataSource("master")
    @Transactional
	public void edit(BusinessDTO businessDTO) {
		PayBusinessPO businessPO = new PayBusinessPO();
		BeanUtils.copyProperties(businessDTO, businessPO);
		businessMapper.update(businessPO);
		
		PayCacheHandle.setBusinessPO(businessPO);
	}

	/**
	 * 校验业务平台是否存在
	 * @return 
	 */
	@Override
	@DataSource("slave")
	public boolean check(Long id, String name) {
		boolean result = false;
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("bpName", name);
		List<PayBusinessPO> businessList = businessMapper.selectAll(para);
		if (businessList == null || businessList.size() <= 0) {
			result = true;
		} else {
			for (PayBusinessPO business : businessList) {
				if (business.getId().equals(id)) {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * 业务平台关联支付渠道
	 */
	@Override
	@DataSource("master")
    @Transactional
	public void addPayChannel(BankChannelDTO bankChannelDTO) {
		businessMapper.addPayChannel(bankChannelDTO);
	}

	/**
	 * 删除支付渠道
	 */
	@Override
	@DataSource("master")
    @Transactional
	public void deletePayChannel(Long id) {
		businessMapper.deletePayChannel(id);
	}

	/**
	 * 业务平台查询银行字典
	 */
	@Override
	@DataSource("slave")
	public List<SelectBankDTO> selectBankByFlag(SelectBankDTO bank) {
		if(bank.getThird_bank_flag()==null&&bank.getThird_channel_flag()==null&&bank.getOnline_bank_flag()==null){
			return null;
		}
		return businessMapper.selectBankByFlag(bank);
	}

	/**
	 * 根据bpid和payservice查询支付渠道
	 */
	@Override
	@DataSource("slave")
	public List<BankChannelDTO> selectPayChannel(Integer bpId, Integer payService) {
		return businessMapper.selectPayChannel(bpId, payService);
	}

	/**
	 * 查询支付渠道帐号
	 */
	@Override
	@DataSource("slave")
	public List<PartnerAccountDTO> selectPartnerAccount(String payType) {
		return businessMapper.selectPartnerAccount(payType);
	}

	/**
	 * 修改支付渠道
	 */
	@Override
	@DataSource("master")
    @Transactional
	public void updatePayChannel(BankChannelDTO bankChannel) {
		businessMapper.updatePayChannel(bankChannel);
		
	}

	/**
	 * 校验支付渠道是否存在
	 */
	@Override
	@DataSource("slave")
	public boolean checkPayChannel(Map<String, Object> para) {
		List<BankChannelDTO> rs = businessMapper.checkPayChannel(para);
		if (rs != null && rs.size() > 0) {
			for (BankChannelDTO channel : rs) {
				if (channel.getId().equals(para.get("id"))) {
					return true;
				}
			}
		} else {
			return true;
		}
		return false;
	}
}
