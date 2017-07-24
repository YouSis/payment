package com.wfj.pay.utils;

import com.alibaba.fastjson.JSON;
import com.wfj.pay.cache.PayCacheHandle;
import com.wfj.pay.dto.KeyValue;
import com.wfj.pay.dto.OrderCloseRequestDTO;
import com.wfj.pay.dto.OrderRequestDTO;
import com.wfj.pay.dto.RefundOrderRequestDTO;
import com.wfj.pay.po.PayBusinessPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用于业务系统对接支付平台加密使用
 *
 * @author wjg
 * @version sdk-0.0.1
 * @date 2016-1-7
 */
public class OrderEncryptUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEncryptUtils.class);
    /**
     * 参与创建订单加密参数数组.
     */
    public final static String[] CREATE_ORDER_MD5_PARAMS = {"bpId",
            "bpOrderId", "goodsName", "totalFee", "content", "remark",
            "initOrderTerminal", "antiPhishingKey", "merCode", "goodsContent",
            "cashier", "authCode", "payType", "payService"};
    /**
     * 参与关闭订单的加密参数数组
     */
    public final static String[] CLOSE_ORDER_MD5_PARAMS ={"bpId","bpOrderId","orderTradeNo","antiPhishingKey"};
    /**
     * 参与创建爱你退款单的参数数组
     */
    public final static String[] CREATE_REFUND_ORDER_MD5_PARAMS={"bpId","bpOrderId","orderTradeNo","antiPhishingKey","bpRefundOrderId","payType","refundFee"};
    /**
     * 根据接口参数和业务平台秘钥获取加密参数
     *
     * @param paramMap 参数组MAP
     * @param bpKey    王府井支付平台分配给业务平台的秘钥
     * @return 生成的加密参数sign 注：参数中的totalFee字段  单位为RMB元，范围[0.01，99999999.99]，精确到小数点后两位
     * 请在传递之前自行转换，否则解密时候校验不通过
     * 比如 订单金额为1元      totalFee = 1.00
     * 订单金额为0.1元  totalFee = 0.10
     */
    public static String getSign(Map<String, String> paramMap, String bpKey) {
        //除去数组中的空值和签名参数
        Map<String, String> para = paraFilter(paramMap);
        //加密
        String sign = buildMysign(para, bpKey);
        return sign;
    }

    /**
     * 获得加密参数
     *
     * @param paramMap
     * @param bpId
     * @return
     */
    public static String getSign(Map<String, String> paramMap, Long bpId) {
        //除去数组中的空值和签名参数
        Map<String, String> para = paraFilter(paramMap);
        PayBusinessPO businessPO = PayCacheHandle.getBusinessPOByBpId(bpId);
        //加密
        String sign = buildMysign(para, businessPO.getBpKey());
        return sign;
    }

    /**
     *根据实体类获取创建订单需要加密的MAP
     * @param orderRequestDTO
     * @return
     */
    private static Map<String,String> getCreateTradeParamsMap(OrderRequestDTO orderRequestDTO){
        Map<String, Object> map = ObjectUtil.beanToMap(orderRequestDTO);
        Map<String, String> collectMap = Arrays.stream(CREATE_ORDER_MD5_PARAMS).
                map(s -> new KeyValue(s, StringUtil.getString(map.get(s))))
                .collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue));
        return collectMap;
    }

    /**
     * 校验签名是否正确
     * @param orderRequestDTO
     * @return
     */
    public static boolean checkCreateOrderSign(OrderRequestDTO orderRequestDTO){
        Map<String, String> createTradeParamsMap = getCreateTradeParamsMap(orderRequestDTO);
       return  checkSign(createTradeParamsMap,Long.valueOf(orderRequestDTO.getBpId()));
    }

    /**
     * 校验关闭订单签名是否正月
     * @param orderCloseRequestDTO
     * @return
     */
    public static boolean checkCloseOrderSign(OrderCloseRequestDTO orderCloseRequestDTO){
        Map<String, Object> map = ObjectUtil.beanToMap(orderCloseRequestDTO);
        Map<String, String> collectMap = Arrays.stream(CLOSE_ORDER_MD5_PARAMS).
                map(s -> new KeyValue(s, StringUtil.getString(map.get(s))))
                .collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue));
        return checkSign(collectMap,Long.valueOf(orderCloseRequestDTO.getBpId()));
    }

    /**
     * 校验创建退单签名是否正月
     * @param refundOrderRequestDTO
     * @return
     */
    public static boolean checkCreateRefundOrderSign(RefundOrderRequestDTO refundOrderRequestDTO){
        Map<String, Object> map = ObjectUtil.beanToMap(refundOrderRequestDTO);
        Map<String, String> collectMap = Arrays.stream(CREATE_REFUND_ORDER_MD5_PARAMS).
                map(s -> new KeyValue(s, StringUtil.getString(map.get(s))))
                .collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue));
        return checkSign(collectMap,Long.valueOf(refundOrderRequestDTO.getBpId()));
    }
    /**
     * 判断加密信息是否正确
     *
     * @param paramMap
     * @param bpId
     * @return
     */
    public static boolean checkSign(Map<String, String> paramMap, Long bpId) {
        boolean result = false;
        String sign = getSign(paramMap, bpId);
        if (sign.equals(paramMap.get("sign"))) {
            result = true;
        }
        return result;
    }


    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    private static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * ali线下除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> aliOfflineParaFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 生成签名结果
     *
     * @param sArray 要签名的数组
     * @param key    为王府井支付平台分配给业务平台的密匙
     * @return 签名结果字符串
     */
    public static String buildMysign(Map<String, String> sArray, String key) {
        //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String prestr = createLinkString(sArray);
        //把拼接后的字符串再与安全校验码直接连接起来
        prestr = prestr + key;
        //生成md5加密串
        String mysign = MD5Util.md5(prestr);
        return mysign;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    private static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("bpId","10045");
        map.put("bpOrderId","809712200162");
        map.put("antiPhishingKey","36274c2ee2f74353a077cc10a9186153");
        String sign  = getSign(map,"7adfc5eb0899b601a4e56821bf3a49e0");
        map.put("sign",sign);
        String s = JSON.toJSONString(map);
        System.out.println(s);

    }
}
