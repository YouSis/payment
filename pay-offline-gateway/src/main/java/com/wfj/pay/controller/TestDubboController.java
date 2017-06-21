package com.wfj.pay.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wfj.pay.dubbo.ITestDubbo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wjg on 2017/6/21.
 */
@RestController
public class TestDubboController {
    @Reference(version = "1.0.0",cluster = "failfast")
    private ITestDubbo testDubbo;

    @RequestMapping("test")
    public String test(){
      return testDubbo.test("wjg");
    }
}
