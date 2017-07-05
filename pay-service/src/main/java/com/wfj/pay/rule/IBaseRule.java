package com.wfj.pay.rule;

import com.wfj.pay.cache.PayCacheHandle;
import com.wfj.pay.dto.OrderRequestDTO;
import com.wfj.pay.dto.RuleResultDTO;
import com.wfj.pay.po.PayBusinessPO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.service.IPayTradeService;
import com.wfj.pay.spring.SpringContextAware;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by wjg on 2017/6/23.
 */
public interface IBaseRule {

    default RuleResultDTO antiPhishingValidate(String  antiPhishingKey){
        RuleResultDTO ruleResultDTO = new RuleResultDTO();
        StringRedisTemplate redisTemplate = SpringContextAware.getBeanByName("stringRedisTemplate");
        String antiPhishing = redisTemplate.opsForValue().get(antiPhishingKey);
        if(StringUtils.isEmpty(antiPhishing)){
            ruleResultDTO.setErrorMessage("防钓鱼时间戳不存在，请检查");
            ruleResultDTO.setSuccess(false);
        }
        return ruleResultDTO;
    }

    default RuleResultDTO bpIdValidate(String bpId){
        RuleResultDTO ruleResultDTO = new RuleResultDTO();
        PayBusinessPO businessPO = PayCacheHandle.getBusinessPOByBpId(Long.valueOf(bpId));
        if(businessPO==null){
            ruleResultDTO.setErrorMessage("bpId为"+bpId+"的业务平台不存在,请联系管理员配置");
            ruleResultDTO.setSuccess(false);
        }
        return ruleResultDTO;
    }

     default  RuleResultDTO orderExistsValidate(String bpId,String bpOrderId,String orderTradeNo){
         RuleResultDTO ruleResultDTO = new RuleResultDTO();
         IPayTradeService payTradeService = SpringContextAware.getBeanByName("payTradeServiceImpl");
         PayTradePO payTradePO;
         //校验订单是否存在
         if (StringUtils.isNotEmpty(orderTradeNo)) {
             payTradePO = payTradeService.findByBpIdAndOrderTradeNo(Long.valueOf(bpId), orderTradeNo);
             if (payTradePO == null) {
                 ruleResultDTO.setErrorMessage("该门店不存在订单号为" + orderTradeNo + "的订单");
                 ruleResultDTO.setSuccess(false);
             }
         } else {
             payTradePO = payTradeService.findByBpIdAndBpOrderId(Long.valueOf(bpId), bpOrderId);
             if (payTradePO == null) {
                 ruleResultDTO.setErrorMessage("该门店不存在小票号为" + bpOrderId + "的订单");
                 ruleResultDTO.setSuccess(false);
             }
         }
         return ruleResultDTO;
     }
}
