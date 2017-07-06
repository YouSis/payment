package com.wfj.pay.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wfj.pay.dubbo.IPayScheduleDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 对当前时间往前推1个小时以前未支付订单，进行关闭
 * Created by wjg on 2017/7/6.
 */
@Component
public class PayTradeCloseTask {
    private static final Logger logger = LoggerFactory.getLogger(PayTradeCloseTask.class);
    @Reference(version = "1.0.0", cluster = "failfast")
    private IPayScheduleDubbo payScheduleDubbo;

    @Scheduled(cron = "0 */1 * * * ?")
    public void tradeColse(){
        logger.info("--->启动任务,开始调用关闭超时订单的服务...");
        try{
            payScheduleDubbo.doCloseTimeOut();
        }catch (Exception e){
            logger.error("订单超时关闭的调度失败"+e.toString(),e);
            //此处可能是dubbo提供者出异常了,应抛异常到异常监控
        }
        logger.info("--->任务结束,停止调用关闭超时订单的服务...");
    }
}
