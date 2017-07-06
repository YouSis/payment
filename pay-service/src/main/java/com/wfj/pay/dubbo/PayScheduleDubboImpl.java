package com.wfj.pay.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.wfj.pay.constant.PayLogConstant;
import com.wfj.pay.dto.OrderResponseDTO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.service.IPayTradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wjg on 2017/7/6.
 */
@Service(version = "1.0.0", cluster = "failfast", timeout = 30000)
public class PayScheduleDubboImpl implements IPayScheduleDubbo {
    private Logger logger = LoggerFactory.getLogger(PayScheduleDubboImpl.class);
    @Autowired
    private IPayTradeService payTradeService;
    /**
     * 一个小时时间戳
     */
    private static final long ONE_HOUR_TIMESTAMP = 60 * 60 * 1000;
    /**
     * 三个小时之前的时间戳
     */
    private static final long THREE_HOUR_TIMESTAMP = 3 * 60 * 60 * 1000;

    @Override
    public void doCloseTimeOut() {
        //1、查询前一个小时到前三个小时之前的未支付的订单
        long threeHoursAgo = System.currentTimeMillis() - THREE_HOUR_TIMESTAMP;
        long oneHoursAge = System.currentTimeMillis() - ONE_HOUR_TIMESTAMP;
        List<PayTradePO> trades = payTradeService.findByTime(threeHoursAgo, oneHoursAge);
        //2、发起关闭
        trades.stream().forEach(payTradePO -> {
            logger.info("--->调度执行超时关闭订单号为{}开始。。。",payTradePO.getOrderTradeNo());
            OrderResponseDTO orderResponseDTO = payTradeService.close(payTradePO, PayLogConstant.OPERATE_SOURCE_SCHEDULE_NAME);
            logger.info("--->调度执行超时关闭订单号为{}的结果为。。。", JSON.toJSONString(orderResponseDTO));
            logger.info("--->调度执行超时关闭订单号为{}结束。。。",payTradePO.getOrderTradeNo());
        });
    }
}
