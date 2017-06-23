package com.wfj.pay.utils;

import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by wjg on 2017/6/23.
 */
public class StringUtil {
    /**
     * 四舍五入并去掉科学计数法.
     * @param value String, double, Double, BigDecimal
     * @param precision 保留几位小数
     * @return
     * @author admin
     * @date 2016-1-7
     */
    public static String toNuSicen(Object value, int precision) {
        Object result = "";
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(precision);
        df.setMaximumFractionDigits(precision);
        df.setGroupingUsed(false);
        if(ObjectUtils.isEmpty(value)){
            return df.format(0);
        }else if(value instanceof BigDecimal){
            result = value;
        }else if(value instanceof String){
            result = new BigDecimal(String.valueOf(value));
        }else if(value instanceof Number){
            result = Double.parseDouble(value.toString());
        }else{
            throw new IllegalArgumentException(value + "need extends Number or String");
        }
        return df.format(result);
    }

    /**
     * 四舍五入并去掉科学计数法, 默认小数点2位.
     * @param value String, double, Double, BigDecimal
     * @return
     * @author admin
     * @date 2015-1-7
     */
    public static String toNuSicen(Object value) {
        return toNuSicen(value, 2);
    }

    /**
     * 值对象 --> String.
     * @param value
     * @return
     * @author admin
     * @date 2015-12-28
     */
    public static String getString(Object value){
        String result = "";
        if(!ObjectUtils.isEmpty(value)){
            String sValue = value.toString().trim();
            if(value instanceof Number){
                if(value instanceof Double || value instanceof BigDecimal){
                    if(!"Infinity".equals(sValue) && !"NaN".equals(sValue)){
                        result = StringUtil.toNuSicen(value);
                    }else{
                        result = "0";
                    }
                }else{
                    result = sValue;
                }
            }else{
                result = sValue;
            }
        }
        return result.trim();
    }
}
