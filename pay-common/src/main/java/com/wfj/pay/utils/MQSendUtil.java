package com.wfj.pay.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wfj.pay.dto.MqBean;
import com.wfj.pay.dto.MqHeaderBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wjg on 2017/7/19.
 */
public class MQSendUtil {
    private static Logger logger = LoggerFactory.getLogger(MQSendUtil.class);

    public static boolean sendMsg(String platformId, String serviceId, String destUrl, String callbackUrl, String mqUrl, MqBean mqBean, String destCallType, String count) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
        long currentTimeMillis = System.currentTimeMillis();


        MqHeaderBean headerBean = new MqHeaderBean();
        headerBean.setServiceID(serviceId);
        headerBean.setBizType("15");// 15 代表订单数据
        headerBean.setCount(count);
        headerBean.setDestUrl(destUrl);
        headerBean.setCallbackUrl(callbackUrl);
        headerBean.setRouteKey(serviceId);
        headerBean.setVersion("1");
        headerBean.setSourceSysID(platformId);// 系统代码
        headerBean.setPriority("1");// 优先级 高：2 中：1 低：0
        headerBean.setCreateTime(sdf.format(new Date(currentTimeMillis)));
        headerBean.setDestCallType(destCallType);
        // 组装header
        mqBean.setHeader(headerBean);

        String json = JSON.toJSONString(mqBean);
        // 发送消息到MQ，取得返回结果
        logger.info("--->发送MQ的报文：" + json);
        String respMsg = HttpClientUtil.sendPostJson(mqUrl, json);
        logger.info("--->MQ的响应报文：" + respMsg);
        JSONObject respMsgJO = JSONObject.parseObject(respMsg);
        Object respStatus = respMsgJO.get("respStatus");
        if (respStatus != null && StringUtils.isNotEmpty(respStatus.toString())) {
            int statusCode = Integer.valueOf(respStatus.toString());
            if (statusCode == 1) {
                return true;
            }
        }
        return false;
    }
}
