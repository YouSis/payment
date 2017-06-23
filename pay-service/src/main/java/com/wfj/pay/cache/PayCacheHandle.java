package com.wfj.pay.cache;

import com.wfj.pay.po.PayBusinessPO;
import com.wfj.pay.service.IPayBusinessService;
import com.wfj.pay.spring.SpringContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by wjg on 2017/6/23.
 */
public class PayCacheHandle {
    public static final String PAY_BUSINESS_CACHE_KEY = "PAY_BUSINESS_%s";
    public static final String REPEAT_SUBMIT__CHECK_CACHE_KEY = "PAY_REPEAT_SUBMIT_CHECK_%s";
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
}
