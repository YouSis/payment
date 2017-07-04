package com.wfj.pay.service.impl;

import com.wfj.pay.service.IPayAntiphishingKeyService;
import com.wfj.pay.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by wjg on 2017/6/30.
 */
@Service
public class PayAntiphishingKeyServiceImpl implements IPayAntiphishingKeyService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public String getAntiPhishingKey() {
        String antiPhishingKey = UUIDUtil.generateUUID();
        stringRedisTemplate.opsForValue().set(antiPhishingKey,antiPhishingKey,2, TimeUnit.MINUTES);
        return antiPhishingKey;
    }
}
