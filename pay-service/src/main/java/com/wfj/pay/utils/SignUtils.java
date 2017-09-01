package com.wfj.pay.utils;

import com.alibaba.fastjson.JSON;
import com.wfj.pay.dto.OrderRequestDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wjg on 2017/8/21.
 */
public class SignUtils {
    public static void main(String[] args) {

        //String json = createOrder();
        //String json = closeOrder();
        String json = createRefundOrder();

        System.out.println(json);
    }

    private static String createOrder(){
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setBpId("10045");
        requestDTO.setAntiPhishingKey("8cb0feaca9324462b346cc8bfb80938a");
        requestDTO.setBpOrderId("809711231263");
        requestDTO.setGoodsName("8097122001");
        requestDTO.setContent("8097122001");
        requestDTO.setTotalFee(0.01);
        requestDTO.setInitOrderTerminal("04");
        requestDTO.setPayType("WECHATPAY_OFFLINE");
        requestDTO.setPayService("2");
        requestDTO.setMerCode("21011");
        requestDTO.setCashier("123456654");
        requestDTO.setAuthCode("134879232524280784");

        Map<String, String> createTradeParamsMap = OrderEncryptUtils.getCreateTradeParamsMap(requestDTO);
        String sign = OrderEncryptUtils.getSign(createTradeParamsMap,"7adfc5eb0899b601a4e56821bf3a49e0");
        requestDTO.setSign(sign);
        return JSON.toJSONString(requestDTO);
    }

    private static String closeOrder(){
        Map<String,String> param = new HashMap<>();
        param.put("antiPhishingKey","b9170db82d2a48d9988fd0f6b7c383bd");
        param.put("bpId","100003");
        param.put("bpOrderId","80971220043");

        String sign = OrderEncryptUtils.getSign(param,"5e86d16a9df70b6861bd9698789ef41a");
        param.put("sign",sign);

        return JSON.toJSONString(param);
    }

    private static String createRefundOrder(){
        Map<String,String> param = new HashMap<>();
        param.put("antiPhishingKey","4cd26077576c4b0e8c3092ec05ba4647");
        param.put("bpId","10045");
        param.put("bpRefundOrderId","123781117111");
        param.put("orderTradeNo","2017090000000000005617");
        param.put("payType","WECHATPAY_OFFLINE");
        param.put("refundFee","0.01");

        String sign = OrderEncryptUtils.getSign(param,"7adfc5eb0899b601a4e56821bf3a49e0");
        param.put("sign",sign);

        return JSON.toJSONString(param);
    }
}
