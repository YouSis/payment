package com.wfj.pay.mapper;

import com.wfj.pay.dto.BankChannelDTO;
import com.wfj.pay.dto.PartnerAccountDTO;
import com.wfj.pay.dto.SelectBankDTO;
import com.wfj.pay.po.PayBusinessPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wjg on 2017/6/22.
 */
@Repository
public interface PayBusinessMapper {

    int insert(PayBusinessPO payBusinessPO);

    void delete(int id);

    PayBusinessPO selectByBpId(@Param("id") Long bpId);

    List<PayBusinessPO> selectAll(Map<String, Object> para);

    void update(PayBusinessPO payBusinessPO);

	void addPayChannel(BankChannelDTO bankChannelDTO);

	void deletePayChannel(Long id);

	List<SelectBankDTO> selectBankByFlag(SelectBankDTO bank);

	List<BankChannelDTO> selectPayChannel(@Param("bpId")Integer bpId, @Param("payService")Integer payService);

	List<PartnerAccountDTO> selectPartnerAccount(@Param("payType")String payType);

	void updatePayChannel(BankChannelDTO bankChannel);

	List<BankChannelDTO> checkPayChannel(Map<String, Object> para);
}
