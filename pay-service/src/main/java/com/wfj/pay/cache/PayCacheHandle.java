package com.wfj.pay.cache;

import com.wfj.pay.po.PayBusinessPO;
import com.wfj.pay.po.PayPartnerAccountPO;
import com.wfj.pay.service.IPayBusinessService;
import com.wfj.pay.service.IPayPartnerAccountServive;
import com.wfj.pay.spring.SpringContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by wjg on 2017/6/23.
 */
public class PayCacheHandle {
    public static final String PAY_BUSINESS_CACHE_KEY = "PAY_BUSINESS_%s";
    public static final String REPEAT_SUBMIT__CHECK_CACHE_KEY = "PAY_REPEAT_SUBMIT_CHECK_%s";
    public static final String PAY_PARTNER_ACCOUNT_CACHE_KEY="PAY_PARTNER_%s";
    /**
     * 获取stringRedisTemplate
     * @return
     */
    public static StringRedisTemplate getStringRedisTemplate(){
        return SpringContextAware.getBeanByName("stringRedisTemplate");
    }

    /**
     * 获取redisTemplate
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> RedisTemplate<K,V> getRedisTemplate(){
        return SpringContextAware.getBeanByName("redisTemplate");
    }

    /**
     * 获取缓存中的PayBusinessPO，如果不存在查询数据库，然后放入缓存
     * @param bpId
     * @return
     */
    public static PayBusinessPO getBusinessPOByBpId(Long bpId){
        RedisTemplate<String,PayBusinessPO> redisTemplate = getRedisTemplate();
        PayBusinessPO businessPO= redisTemplate.opsForValue().get(String.format(PAY_BUSINESS_CACHE_KEY,bpId));
        if(businessPO == null){
           IPayBusinessService payBusinessService =  SpringContextAware.getBeanByName("payBusinessServiceImpl");
            businessPO = payBusinessService.findByBpId(bpId);
        }
        if(businessPO!=null){
            redisTemplate.opsForValue().set(String.format(PAY_BUSINESS_CACHE_KEY,bpId),businessPO);
        }
        return businessPO;
    }

    /**
     * 获取缓存中的PayPartnerAccountPO，如果不存在则查询数据库，然后放入缓存
     * @param id
     * @return
     */
    public static PayPartnerAccountPO getPayPartnerAccout(Long id){
        RedisTemplate<String,PayPartnerAccountPO> redisTemplate = getRedisTemplate();
        PayPartnerAccountPO partnerAccountPO = redisTemplate.opsForValue().get(String.format(PAY_PARTNER_ACCOUNT_CACHE_KEY, id));
        if(partnerAccountPO==null){
            IPayPartnerAccountServive partnerAccountServive = SpringContextAware.getBeanByName("payPartnerServiceImpl");
            partnerAccountPO = partnerAccountServive.findPayPartnerAccoutById(id);
        }
        if(partnerAccountPO!=null){
            redisTemplate.opsForValue().set(String.format(PAY_PARTNER_ACCOUNT_CACHE_KEY,id),partnerAccountPO);
        }
        return partnerAccountPO;
    }
}
