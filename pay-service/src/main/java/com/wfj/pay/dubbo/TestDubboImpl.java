package com.wfj.pay.dubbo;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * Created by wjg on 2017/6/21.
 */
@Service(version ="1.0.0",cluster = "failfast")
public class TestDubboImpl implements ITestDubbo {
    @Override
    public String test(String str) {
        return "hello world "+str;
    }
}
