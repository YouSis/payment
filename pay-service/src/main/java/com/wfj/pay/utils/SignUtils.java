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

        String json = createOrder();
        //String json = closeOrder();
        //String json = createRefundOrder();

        System.out.println(json);
    }

    private static String createOrder(){
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setBpId("100003");
        requestDTO.setAntiPhishingKey("634233c5c70341109b124125b29b3d60");
        requestDTO.setBpOrderId("809711231262");
        requestDTO.setGoodsName("8097122001");
        requestDTO.setContent("8097122001");
        requestDTO.setTotalFee(0.01);
        requestDTO.setInitOrderTerminal("04");
        requestDTO.setPayType("WECHATPAY_OFFLINE");
        requestDTO.setPayService("2");
        requestDTO.setMerCode("21011");
        requestDTO.setCashier("123456654");
        requestDTO.setAuthCode("130416849005577684");

        Map<String, String> createTradeParamsMap = OrderEncryptUtils.getCreateTradeParamsMap(requestDTO);
        String sign = OrderEncryptUtils.getSign(createTradeParamsMap,"5e86d16a9df70b6861bd9698789ef41a");
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
        param.put("antiPhishingKey","ef16ebb590c54e5f8e146edfc9129983");
        param.put("bpId","100003");
        param.put("bpRefundOrderId","123781117");
        param.put("orderTradeNo","2017080000000000939231");
        param.put("payType","ALIPAY_OFFLINE");
        param.put("refundFee","0.01");

        String sign = OrderEncryptUtils.getSign(param,"5e86d16a9df70b6861bd9698789ef41a");
        param.put("sign",sign);

        return JSON.toJSONString(param);
    }
}
