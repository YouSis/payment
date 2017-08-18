package com.wfj.pay.dubbo;

import java.util.List;
import java.util.Map;

import com.wfj.pay.dto.BankChannelDTO;
import com.wfj.pay.dto.BusinessDTO;
import com.wfj.pay.dto.MerchantRequestDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.PartnerAccountDTO;
import com.wfj.pay.dto.PayChannelFeeRateDTO;
import com.wfj.pay.dto.PayDictionaryDTO;
import com.wfj.pay.dto.PayFeeRateTypeDTO;
import com.wfj.pay.dto.PayPartnerAccountDTO;
import com.wfj.pay.dto.SelectBankDTO;
import com.wfj.pay.dto.SelectOptionDTO;
import com.wfj.pay.po.PayMerchantPO;

/**
 * 对应ops所有操作的dubbo接口
 * @author jh
 *
 */
public interface IOPSOperationDubbo {

	/**
	 * 商户信息分页查询
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
	 */
	List<PayMerchantPO> merchantSelectMerCode();

	/**
	 * 分页查询业务平台
	 * @param businessDTO
	 * @return
	 */
	PaginationDTO<BusinessDTO> selectBusinessByPage(BusinessDTO businessDTO);

	/**
	 * 根据用户id查询权限内业务平台id
	 * @param userId
	 * @return
	 */
	List<Long> selectUserRightsByUserId(String userId);

	/**
	 * 根据id查询平台名
	 * @param bpIds
	 */
	List<SelectOptionDTO> findQueryBusiness(List<Long> bpIds);

	/**
	 * 新增业务平台
	 * @param businessDTO
	 */
	void addBusiness(BusinessDTO businessDTO);

	/**
	 * 修改业务平台
	 * @param businessDTO
	 */
	void editBusiness(BusinessDTO businessDTO);

	/**
	 * 校验业务平台是否存在
	 * @param id
	 * @param name
	 * @return
	 */
	boolean checkBusiness(Long id, String name);

	/**
	 * 业务平台增加支付渠道
	 * @param bankChannelDTO
	 */
	void addPayChannel(BankChannelDTO bankChannelDTO);

	/**
	 * 删除业务平台支付渠道
	 * @param id
	 */
	void deletePayChannel(Long id);

	/**
	 * 业务平台查询银行字典
	 * @param bankDTO
	 */
	List<SelectBankDTO> selectBankByFlag(SelectBankDTO bankDTO);

	/**
	 * 业务平台根据bpid和payservice查询支付渠道
	 * @param bpId
	 * @param payService
	 * @return
	 */
	List<BankChannelDTO> selectPayChannel(Integer bpId, Integer payService);

	/**
	 * 查询支付渠道帐号
	 * @param payType
	 * @return
	 */
	List<PartnerAccountDTO> selectChannelAccount(String payType);

	/**
	 * 修改支付渠道
	 * @param bankChannel
	 */
	void updatePayChannel(BankChannelDTO bankChannel);

	/**
	 * 校验支付渠道是否存在
	 * @param para
	 * @return
	 */
	boolean checkPayChannel(Map<String, Object> para);

	/**
	 * 支付渠道管理页面支付渠道分页查询
	 * @param partnerAccount
	 * @return
	 */
	PaginationDTO<PayPartnerAccountDTO> findPartnerAccountAllByPage(PayPartnerAccountDTO partnerAccount);

	/**
	 * 支付渠道管理页面查询单个支付渠道
	 * @param id
	 * @return
	 */
	PayPartnerAccountDTO findOnePayPartnerAccountById(Long id);

	/**
	 * 支付渠道管理页面新增支付渠道
	 * @param payPartnerAccount
	 */
	void addPayPartnerAccount(PayPartnerAccountDTO payPartnerAccount);

	/**
	 * 支付渠道管理页面修改支付渠道
	 * @param partnerAccount
	 */
	void updatePayPartnerAccount(PayPartnerAccountDTO partnerAccount);

	/**
	 * 支付渠道管理页面查询支付渠道费率
	 * @param channelFeeRate
	 * @return
	 */
	List<PayChannelFeeRateDTO> selectPayChannelFeeRateList(PayChannelFeeRateDTO channelFeeRate);

	/**
	 * 支付渠道管理页面新增支付渠道费率
	 * @param channelFeeRate
	 */
	void addChannelFeeRate(PayChannelFeeRateDTO channelFeeRate);

	/**
	 * 支付渠道管理页面修改支付渠道费率
	 * @param channelFeeRate
	 */
	void updateChannelFeeRate(PayChannelFeeRateDTO channelFeeRate);

	/**
	 * 根据支付渠道类型查询支付渠道费率类型
	 * @param payType
	 * @return
	 */
	List<PayFeeRateTypeDTO> selectFeeRateType(String payType);

	/**
	 * 查询渠道支付类型
	 * @return
	 */
	List<PayDictionaryDTO> selectPayDictionary();

}
