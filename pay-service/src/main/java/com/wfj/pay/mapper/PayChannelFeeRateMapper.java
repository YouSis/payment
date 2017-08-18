package com.wfj.pay.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wfj.pay.dto.PayChannelFeeRateDTO;
import com.wfj.pay.po.PayChannelFeeRatePO;

/**
 * 支付渠道费率
 * @author jh
 *
 */
@Repository
public interface PayChannelFeeRateMapper {

	List<PayChannelFeeRatePO> selectPayChannelFeeRateList(Map<String, Object> param);

	void addChannelFeeRate(PayChannelFeeRatePO channelFeeRate);

	void updateChannelFeeRate(PayChannelFeeRatePO po);

}
