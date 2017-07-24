package com.wfj.pay.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * Created by kongqf on 2017/7/11.
 */
public class JsonUtil {

    /**
     * 把字符串转换成map
     *
     * @param jsonText
     * @return
     */
    public static Map<String, String> string2Map(String jsonText) {
        return JSONObject.parseObject(jsonText, new TypeReference<Map<String, String>>() {
        });
    }

    /**
     * 根据key获取String值
     *
     * @param object
     * @param Key
     * @return
     */
    public static String getJsonValueS(JSONObject object, String Key) {
        String value = "";
        if (object.containsKey(Key)) {
            value = object.getString(Key);
        }
        return value;
    }

    private boolean getJsonValueB(JSONObject object, String Key) {
        boolean value = false;
        if (object.containsKey(Key)) {
            value = object.getBoolean(Key);
        }
        return value;
    }

    private Integer getJsonValueI(JSONObject object, String Key) {
        Integer value = 0;
        if (object.containsKey(Key)) {
            value = object.getInteger(Key);
        }
        return value;
    }
}
