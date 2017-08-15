package com.wfj.pay.service.impl;

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
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.PayChannelFeeRateDTO;
import com.wfj.pay.dto.PayFeeRateTypeDTO;
import com.wfj.pay.dto.PayPartnerAccountDTO;
import com.wfj.pay.mapper.PayChannelFeeRateMapper;
import com.wfj.pay.mapper.PayFeerateTypeMapper;
import com.wfj.pay.mapper.PayPartnerAccountMapper;
import com.wfj.pay.po.PayChannelFeeRatePO;
import com.wfj.pay.po.PayFeeRateTypePO;
import com.wfj.pay.po.PayPartnerAccountPO;
import com.wfj.pay.service.IPayPartnerAccountServive;

/**
 * Created by wjg on 2017/6/26.
 */
@Service
public class PayPartnerServiceImpl implements IPayPartnerAccountServive {
	@Autowired
	private PayPartnerAccountMapper partnerAccountMapper;
	@Autowired
	private PayChannelFeeRateMapper channelFeeRateMapper;
	@Autowired
	private PayFeerateTypeMapper feeRateTypeMapper;

	@Override
	@DataSource("slave")
	public PayPartnerAccountPO findPayPartnerAccoutById(Long id) {
		return partnerAccountMapper.selectById(id);
	}

	/**
	 * 分页查询支付渠道帐号
	 */
	@Override
	@DataSource("slave")
	public PaginationDTO<PayPartnerAccountPO> findAllByPage(PayPartnerAccountDTO partnerAccount) {
		
		PaginationDTO<PayPartnerAccountPO>  pagePO = new PaginationDTO<PayPartnerAccountPO>();
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("payType", partnerAccount.getPayType());
		para.put("partner", partnerAccount.getPartner());
		PageHelper.startPage(partnerAccount.getPageNo(), partnerAccount.getPageSize());
		List<PayPartnerAccountPO> partnerAccountList = partnerAccountMapper.findAllPage(para);
		
		pagePO.setPageNo(partnerAccount.getPageNo());
		pagePO.setPageSize(partnerAccount.getPageSize());
		pagePO.setTotalPages(((Page<PayPartnerAccountPO>) partnerAccountList).getPages());
		pagePO.setTotalHit((int) ((Page<PayPartnerAccountPO>) partnerAccountList).getTotal());
		pagePO.setPageStartRow(((Page<PayPartnerAccountPO>) partnerAccountList).getStartRow());
		pagePO.setPageEndRow(((Page<PayPartnerAccountPO>) partnerAccountList).getEndRow());
		pagePO.setListData(partnerAccountList);
		pagePO.calculatePage();
		return pagePO;
	}

	/**
	 * 新增支付渠道
	 */
	@Override
	@DataSource("master")
    @Transactional
	public void add(PayPartnerAccountDTO payPartnerAccount) {
		PayPartnerAccountPO po = new PayPartnerAccountPO();
		BeanUtils.copyProperties(payPartnerAccount, po);
		partnerAccountMapper.insert(po);
	}

	/**
	 * 修改支付渠道
	 */
	@Override
	@DataSource("master")
    @Transactional
	public void update(PayPartnerAccountDTO partnerAccount) {
		PayPartnerAccountPO po = new PayPartnerAccountPO();
		BeanUtils.copyProperties(partnerAccount, po);
		partnerAccountMapper.update(po);
		
		PayCacheHandle.setPayPartnerAccout(po);
	}

	/**
	 * 查询渠道费率
	 */
	@Override
	@DataSource("slave")
	public List<PayChannelFeeRatePO> selectPayChannelFeeRateList(PayChannelFeeRateDTO channelFeeRate) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("payPartner", channelFeeRate.getPayPartner());
		param.put("payType", channelFeeRate.getPayType());
		return channelFeeRateMapper.selectPayChannelFeeRateList(param);
	}

	/**
	 * 新增支付费率
	 */
	@Override
	@DataSource("master")
    @Transactional
	public void addChannelFeeRate(PayChannelFeeRateDTO channelFeeRate) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("payPartner", channelFeeRate.getPayPartner());
		param.put("payType", channelFeeRate.getPayType());
		List<PayChannelFeeRatePO> list = channelFeeRateMapper.selectPayChannelFeeRateList(param);
		if (list != null && list.size() > 0) {
			throw new RuntimeException("费率类别已经存在");
		}
		
		PayChannelFeeRatePO channelFeeRatePO = new PayChannelFeeRatePO();
		BeanUtils.copyProperties(channelFeeRate, channelFeeRatePO);
		channelFeeRateMapper.addChannelFeeRate(channelFeeRatePO);
	}

	/**
	 * 修改支付渠道
	 * @param channelFeeRate
	 */
	@Override
	public void updateChannelFeeRate(PayChannelFeeRateDTO channelFeeRate) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("payPartner", channelFeeRate.getPayPartner());
		param.put("payType", channelFeeRate.getPayType());
		List<PayChannelFeeRatePO> list = channelFeeRateMapper.selectPayChannelFeeRateList(param);
		if (list != null && list.size() > 0) {
			list.forEach(po -> {
				if (channelFeeRate.getId().equals(po.getId())) {
					channelFeeRateMapper.updateChannelFeeRate(po);
				}
			});
		} else {
			throw new RuntimeException("该支付渠道没有设置费率");
		}
	}

	/**
	 * 根据支付类型查询支付渠道类型
	 */
	@Override
	public List<PayFeeRateTypeDTO> selectFeeRateType(String payType) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("payType", payType);
		List<PayFeeRateTypePO> poList = feeRateTypeMapper.selectFeeRateType(param);
		List<PayFeeRateTypeDTO> dtoList = new ArrayList<PayFeeRateTypeDTO>();
		BeanUtils.copyProperties(poList, dtoList);
		return dtoList;
	}
}
