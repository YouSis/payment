package com.wfj.pay.sdk.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayResponse;
import com.alipay.api.internal.util.AlipaySignature;
import com.wfj.pay.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by kongqf on 2017/7/11.
 */
public class AliPayUtil {
    private Logger logger = LoggerFactory.getLogger(AliPayUtil.class);


    public static boolean verify(AlipayResponse response, AliPayConfig aliPayConfig) throws AlipayApiException {
        Map<String, String> resultMap = JsonUtil.string2Map(response.getBody());
        String content = resultMap.get("alipay_trade_query_response");
        String sign = resultMap.get("sign");
        return AlipaySignature.rsaCheckContent(JSON.toJSONString(response), sign, aliPayConfig.getALIPAY_PUBLIC_KEY(), aliPayConfig.getCHARSET());
    }
}
