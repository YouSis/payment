package com.wfj.pay.service;

import java.util.List;
import java.util.Map;

import com.wfj.pay.dto.BankChannelDTO;
import com.wfj.pay.dto.BusinessDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.PartnerAccountDTO;
import com.wfj.pay.dto.SelectBankDTO;
import com.wfj.pay.dto.SelectOptionDTO;
import com.wfj.pay.po.PayBusinessPO;

/**
 * Created by wjg on 2017/6/23.
 */
public interface IPayBusinessService {
    PayBusinessPO findByBpId(Long bpId);

    /**
     * 分页查询业务平台
     * @param businessDTO
     * @return
     */
	PaginationDTO<PayBusinessPO> selectBusinessByPage(BusinessDTO businessDTO);

	/**
	 * 根据id集合查询平台名
	 * @param bpIds
	 * @return
	 */
	List<SelectOptionDTO> findOptionByBpId(List<Long> bpIds);

	/**
	 * 新增业务平台
	 * @param businessDTO
	 */
	void add(BusinessDTO businessDTO);

	/**
	 * 修改业务平台
	 * @param businessDTO
	 */
	void edit(BusinessDTO businessDTO);

	/**
	 * 校验业务平台是否存在
	 * @param id
	 * @param name
	 * @return
	 */
	boolean check(Long id, String name);

	/**
	 * 业务平台新增支付渠道
	 */
	void addPayChannel(BankChannelDTO bankChannelDTO);

	/**
	 * 业务平台删除支付渠道
	 * @param id
	 */
	void deletePayChannel(Long id);

	/**
	 * 业务平台查询银行字典列表
	 * @param bankDTO
	 * @return
	 */
	List<SelectBankDTO> selectBankByFlag(SelectBankDTO bankDTO);

	/**
	 * 根据bpid和payservice查询支付渠道
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
	List<PartnerAccountDTO> selectPartnerAccount(String payType);

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

}
