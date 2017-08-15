package com.wfj.pay.service;

import java.util.List;

import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.PayChannelFeeRateDTO;
import com.wfj.pay.dto.PayFeeRateTypeDTO;
import com.wfj.pay.dto.PayPartnerAccountDTO;
import com.wfj.pay.po.PayChannelFeeRatePO;
import com.wfj.pay.po.PayPartnerAccountPO;

/**
 * Created by wjg on 2017/6/26.
 */
public interface IPayPartnerAccountServive {
    /**
     * 根据主键查找对应的账户信息
     * @param id
     * @return
     */
    PayPartnerAccountPO findPayPartnerAccoutById(Long id);

    /**
     * 支付渠道帐号分页查询
     * @param partnerAccount
     */
    PaginationDTO<PayPartnerAccountPO> findAllByPage(PayPartnerAccountDTO partnerAccount);

    /**
     * 新增支付渠道
     * @param payPartnerAccount
     */
	void add(PayPartnerAccountDTO payPartnerAccount);

	/**
	 * 修改支付渠道
	 * @param partnerAccount
	 */
	void update(PayPartnerAccountDTO partnerAccount);

	/**
	 * 查询渠道费率
	 * @param channelFeeRate
	 * @return
	 */
	List<PayChannelFeeRatePO> selectPayChannelFeeRateList(PayChannelFeeRateDTO channelFeeRate);

	/**
	 * 新增支付渠道费率
	 * @param channelFeeRate
	 * @return
	 */
	void addChannelFeeRate(PayChannelFeeRateDTO channelFeeRate);

	/**
	 * 修改支付渠道费率
	 * @param channelFeeRate
	 */
	void updateChannelFeeRate(PayChannelFeeRateDTO channelFeeRate);

	/**
	 * 根据支付类型查询支付渠道类型
	 * @param payType
	 * @return
	 */
	List<PayFeeRateTypeDTO> selectFeeRateType(String payType);
}
