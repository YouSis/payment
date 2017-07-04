package com.wfj.pay.controller;

import com.wfj.pay.service.IPayAntiphishingKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wjg on 2017/6/30.
 */
@RestController
public class ApiController {
    @Autowired
    private IPayAntiphishingKeyService payAntiphishingKeyService;

    @RequestMapping("/api/generateAntiPhishingKey")
    @ResponseBody
    public String generateAntiPhishingKey() {
        return payAntiphishingKeyService.getAntiPhishingKey();
    }
}
