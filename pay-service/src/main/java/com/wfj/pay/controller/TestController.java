package com.wfj.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wjg on 2017/6/19.
 */
@RestController
public class TestController {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @RequestMapping("/test")
    public String test() {
        redisTemplate.opsForValue().set("my_key","oh my god");
       String  value =  redisTemplate.opsForValue().get("my_key");
       return value;
    }


}
