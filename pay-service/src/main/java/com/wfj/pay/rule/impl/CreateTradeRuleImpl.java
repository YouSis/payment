package com.wfj.pay.rule.impl;

import com.wfj.pay.cache.PayCacheHandle;
import com.wfj.pay.dto.OrderRequestDTO;
import com.wfj.pay.dto.RuleResultDTO;
import com.wfj.pay.po.PayChannelPO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.rule.ICreateTradeRule;
import com.wfj.pay.service.IPayChannelService;
import com.wfj.pay.service.IPayTradeService;
import com.wfj.pay.spring.SpringContextAware;
import com.wfj.pay.utils.OrderEncryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by wjg on 2017/6/23.
 */
@Component
public class CreateTradeRuleImpl implements ICreateTradeRule {
    @Autowired
    private IPayTradeService payTradeService;
    @Autowired
    private IPayChannelService payChannelService;

    @Override
    public RuleResultDTO md5Validate(OrderRequestDTO orderRequestDTO) {
        RuleResultDTO ruleResultDTO = new RuleResultDTO();
        if (!OrderEncryptUtils.checkSign(orderRequestDTO)) {
            ruleResultDTO.setErrorMessage("签名错误,请检查参数");
            ruleResultDTO.setSuccess(false);
        }
        return ruleResultDTO;
    }

    @Override
    public RuleResultDTO repeatCreateValidate(OrderRequestDTO orderRequestDTO) {
        RuleResultDTO ruleResultDTO = new RuleResultDTO();
        PayTradePO tradePO = payTradeService.findByBpIdAndBpOrderId(Long.valueOf(orderRequestDTO.getBpId()), orderRequestDTO.getBpOrderId());
        if (tradePO != null) {
            ruleResultDTO.setErrorMessage("bpId为" + orderRequestDTO.getBpId() + ",bpOrderId为" + orderRequestDTO.getBpOrderId() + "的订单已经存在，请勿重复创建");
            ruleResultDTO.setSuccess(false);
        }
        return ruleResultDTO;
    }

    @Override
    public RuleResultDTO repeatSubmitValidate(OrderRequestDTO orderRequestDTO) {
        RuleResultDTO ruleResultDTO = new RuleResultDTO();
        StringRedisTemplate stringRedisTemplate = PayCacheHandle.getStringRedisTemplate();
        String key = String.format(PayCacheHandle.REPEAT_SUBMIT__CHECK_CACHE_KEY, orderRequestDTO.getBpId() + orderRequestDTO.getBpOrderId());
        String bpOrder = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(bpOrder)) {
            stringRedisTemplate.opsForValue().set(key, key, 30);
        } else {
            ruleResultDTO.setErrorMessage(key + "该订单重复支付");
            ruleResultDTO.setSuccess(false);
        }
        return ruleResultDTO;
    }

    @Override
    public RuleResultDTO payChannelValidate(OrderRequestDTO orderRequestDTO) {
        RuleResultDTO ruleResultDTO = new RuleResultDTO();
        PayChannelPO payChannelPO = payChannelService.findByCashierParams(Integer.valueOf(orderRequestDTO.getPayService()), orderRequestDTO.getPayType(), orderRequestDTO.getInitOrderTerminal(), Long.valueOf(orderRequestDTO.getBpId()));
        if (payChannelPO == null) {
            ruleResultDTO.setErrorMessage("该业务平台不支持此种支付方式");
            ruleResultDTO.setSuccess(false);
        }
        return ruleResultDTO;
    }
}
