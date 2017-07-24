package com.wfj.pay.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.wfj.pay.po.PayAliPayOffLineNotifyInfoPO;
import com.wfj.pay.sdk.alipay.AliPayConstants;
import com.wfj.pay.utils.JsonUtil;

import java.util.HashMap;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseObject;
import static org.apache.commons.lang3.CharEncoding.UTF_8;

/**
 * Created by kongqf on 2017/7/7.
 */
public class AliTestMain {
    static AlipayClient alipayClient;

    static {
        //URL支付网关（固定）
        String URL = "https://openapi.alipay.com/gateway.do";
        String APP_ID = "2016070701590272";//APPID即创建应用后生成	获取见上面创建应用并获取APPID
        //私钥(开发者应用私钥，由开发者自己生成	获取详见上面配置密钥)
        String APP_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK+sbjzhzfPZSOKIzsQnVQv6pLc/Wn7DWsrpzrVbjNWYCh29OM+3NqvmwRqJjGnEWeRO7QYH5J0DCRnnzlSXAmIzSZUwIP2dRX4yOSrAGAPcz68WfTdf5arTq+i0IMq4TTo2DZSJOyTDMkzmY/EPmSYzZaDq0w0Ngxt3tCmalx/FAgMBAAECgYBb0OaJAYfQFFfItrJCyT/lE+o1MHKO4HG6rjrwhNn15RIcewKPZfW4INH6mbt+qDZHYoz1Yvi0q5p9JK+tfIOsizuHdX3wCIliTQTEGoTh8l+W5vsUeqM6j7TAaDUuqsHvEcro9N6FwuNKQ/Gg/1U6xQDRwDhgb5MHbIHAu9jzIQJBAOWUiiYgWNz9ai7RBRkqDMutpPbfH8CqtvaZiDUSAj8MlzhTueYvs28uDxa0VrPTJDzCoXa1LVPGrmwcYFBxku0CQQDD48tFwKgNDJ4rweJRiLbOKABnPx6JkOCapG/AKkH5wowrDgLJrFV6TfB+OtUxSIHDfTZRhP/9gy8krl7MaO05AkEAhUKQYgsaqcPrE5u+QtoWrU9w1t+y1aoCI+gAmRWH+kL7r/VhjcyYceuhVIN/ZEgl26L6CCaoVd2YtyZNfcQ/DQJAbjH/0J5fKvAVLmJkMR+qTnCcQXh1ckeowfWM09oxwRGbDkFUeiTS9PlRN4FbGt1B+jm+y774zqD0bTsWIwFHqQJADWfD6xfG9XY+7S1jQ548938HZPy7RFtDSD6lhJOcYZFWGJ+fOm7lz+/CJiKnmOlAHAoVR5JKHm/MehF8LufkJA==";
        String FORMAT = "json";//参数返回格式，只支持json	json（固定）
        String CHARSET = UTF_8;//请求和签名使用的字符编码格式，支持GBK和UTF-8
        //支付宝公钥
        String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
        String SIGN_TYPE = "RSA";//商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
        alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
    }

    public static void main(String[] args) {
//        testRefundQuery();
//        testQuery("2017070000000000005558");


       /* String str="{\"alipay_trade_refund_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"buyer_logon_id\":\"kon***@126.com\",\"buyer_user_id\":\"2088102905607963\",\"fund_change\":\"Y\",\"gmt_refund_pay\":\"2017-07-20 14:34:11\",\"open_id\":\"20881042425906901090540450015896\",\"out_trade_no\":\"2017070000000000005561\",\"refund_detail_item_list\":[{\"amount\":\"0.01\",\"fund_channel\":\"ALIPAYACCOUNT\"}],\"refund_fee\":\"0.01\",\"send_back_fee\":\"0.01\",\"trade_no\":\"2017072021001004960273683298\"},\"sign\":\"q3P9MapS5IklzcpOE/XEuHdavlREY0dHCU0awqaHefrhpvLzFFCykMcx5ORLTYowJJffljdjEMWjrlmJOW0OzV/ZlGPM5ho1UEL84YSd3SAzkbu+weHHNjOxxOGzlhGFi1tU2mTQWaHMqUI8ODenfaEMiwMk5zpDe/ed3pZqY78=\"}";

        Map<String, String> responseMap = JsonUtil.string2Map(str);
        String reslut = responseMap.get(AliPayInterface.AlipayTrade_Refund.getMethod());
        Map<String, String> resultMap = JsonUtil.string2Map(reslut);
        System.out.println(JSON.toJSONString(resultMap));
        System.out.println("============================");

        RefundAliOffLineNotifyInfoPO refundNotifyPO = JSONObject.parseObject(JSON.toJSONString(resultMap), RefundAliOffLineNotifyInfoPO.class);
//        RefundAliOffLineNotifyInfoPO refundNotifyPO = (RefundAliOffLineNotifyInfoPO) ObjectUtil.mapToBean(RefundAliOffLineNotifyInfoPO.class, resultMap);
        System.out.println(JSON.toJSONString(refundNotifyPO));*/



        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        request.setBizContent("{" +
                "\"trade_no\":\"2017072021001004960274320406\"," +
//                "\"out_trade_no\":\"2017070000000000005569\"," +
                "\"out_request_no\":\"2017070000000000005569R1\"" +
                "  }");
        AlipayTradeFastpayRefundQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
            System.out.println(JSON.toJSONString(response));
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }

    }

    //测试查询
    static boolean testQuery(String seriaNo) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("out_trade_no", seriaNo);
        String bizContent = JSON.toJSONString(paramMap);
        request.setBizContent(bizContent);
//        request.setBizContent("{\"out_trade_no\":\"" + seriaNo + "\"}");// +  //out_trade_no 支付时传入的商户订单号，与trade_no必填一个
        //"\"trade_no\":\"" + trade_no + "\"}"); //trade_no支付宝交易号,与商户订单号out_trade_no不能同时为空
        try {
            System.out.println(request.getBizContent());
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            System.out.println("111:" + response.getBody());
            System.out.println(response.getParams());
            Map<String, String> resultMap = parseObject(response.getBody(), new TypeReference<Map<String, String>>() {
            });

            String responseResult = resultMap.get("alipay_trade_query_response");
            String resultSign = resultMap.get("sign");

            System.out.println("sign===========");
            System.out.println(resultSign);

            System.out.println("======================================================================");
            System.out.println(JSONObject.toJSONString(response));

            AlipayTradeQueryResponse re = parseObject(JSONObject.toJSONString(response), AlipayTradeQueryResponse.class);
            System.out.println(re.getBody());

            Map<String, String> resultMap2 = JsonUtil.string2Map(response.getBody());
            String content = resultMap2.get("alipay_trade_query_response");
            System.out.println("--------------------------------------------------------------------------");
            System.out.println(content);
            PayAliPayOffLineNotifyInfoPO aliPayOffLineNotifyInfoPO = JSONObject.parseObject(content, PayAliPayOffLineNotifyInfoPO.class);

            System.out.println("***************************************");
            System.out.println(JSONObject.toJSONString(aliPayOffLineNotifyInfoPO));
            String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

            String sign = resultMap.get("sign");
            boolean s = AlipaySignature.rsaCheckContent(content, sign, ALIPAY_PUBLIC_KEY, "UTF-8");
            System.out.println("签名校验结果：" + s);


            if (response.getCode().equals(AliPayConstants.SUCCESS)) {
                return true;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return false;
    }


    static boolean testRefundQuery() {
        //1、组织查询报文
        Map<String, String> data = new HashMap<String, String>();
//        data.put("trade_no", "2017071921001004960272060868");
        data.put("out_trade_no", "2017070000000000005558");
        data.put("out_request_no", "2017070000000000005558R3");
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        request.setBizContent(JSON.toJSONString(data));
        System.out.println("支付参数：");
        System.out.println(JSON.toJSONString(data));

        //2、调用支付宝退款查询接口
        long beginTime = System.currentTimeMillis();
        AlipayTradeFastpayRefundQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
            System.out.println(JSON.toJSONString(response));
        } catch (AlipayApiException e) {

        }
        return false;
    }

    /**
     * 解析返回json 获得map集合
     *
     * @param result
     * @return
     */
    public Object parseJSON(String result) {
        Map<String, Object> resultMap = parseObject(result, new TypeReference<Map<String, Object>>() {
        });

        return resultMap;
    }


}
