package com.wfj.pay.controller;

import com.alibaba.fastjson.JSON;
import com.wfj.pay.dto.MqBean;
import com.wfj.pay.dto.MqResponseBean;
import com.wfj.pay.dto.PayDataDTO;
import com.wfj.pay.dto.PayRefundDataDTO;
import com.wfj.pay.service.IPayDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wjg on 2017/7/19.
 */
@RestController
@RequestMapping("collect")
public class PayDataCollectController {
    private Logger logger = LoggerFactory.getLogger(PayDataCollectController.class);
    @Autowired
    private IPayDataService payDataService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(30, r -> {
        Thread t = new Thread(r);
        t.setName("save-pay-data-thread-" + t.getId());
        return t;
    });


    /**
     * 保存支付数据到es
     */
    @RequestMapping("payTrade")
    public MqResponseBean savePayTrade(@RequestBody MqBean<PayDataDTO> mqBean){
        logger.info("--->接到保存订单的MQ报文："+ JSON.toJSONString(mqBean));
        MqResponseBean responseBean = new MqResponseBean(mqBean.getHeader().getMessageID(),
                mqBean.getHeader().getServiceID(),1,"","成功");
        executorService.submit(()->payDataService.savePayTrade(mqBean));
        return responseBean;
    }

    /**
     * 保存退款数据到es
     */
    @RequestMapping("refundTrade")
    public MqResponseBean savePayRefundTrade(@RequestBody MqBean<PayRefundDataDTO> mqBean){
        logger.info("--->接到保存退款的MQ报文："+JSON.toJSONString(mqBean));
        MqResponseBean responseBean = new MqResponseBean(mqBean.getHeader().getMessageID(),
                mqBean.getHeader().getServiceID(),1,"","成功");
        executorService.submit(()->payDataService.saveRefundTrade(mqBean));
        return responseBean;
    }
}
