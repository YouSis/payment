package com.wfj.pay.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wjg on 2017/6/23.
 */
public class ObjectUtil {
    private static Logger logger = LoggerFactory.getLogger(ObjectUtil.class);
    
    /**
	 * One of the following conditions isEmpty = true, else = false :
	 * 满足下列一个条件则为空<br>
	 * 1. null : 空<br>
	 * 2. "" or " " : 空串<br>
	 * 3. no item in [] or all item in [] are null : 数组中没有元素, 数组中所有元素为空<br>
	 * 4. no item in (Collection, Map, Dictionary) : 集合中没有元素<br>
	 * 
	 * @param value
	 * @return
	 * @author admin
	 * @date May 6, 2010 4:21:56 PM
	 */
	public static boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		}
		if ((value instanceof String)
				&& ((((String) value).trim().length() <= 0) || "null"
						.equalsIgnoreCase((String) value))) {
			return true;
		}
		if ((value instanceof Object[]) && (((Object[]) value).length <= 0)) {
			return true;
		}
		if (value instanceof Object[]) { // all item in [] are null :
			// 数组中所有元素为空
			Object[] t = (Object[]) value;
			for (int i = 0; i < t.length; i++) {
				if (t[i] != null) {
					if (t[i] instanceof String) {
						if (((String) t[i]).trim().length() > 0
								|| "null".equalsIgnoreCase((String) t[i])) {
							return false;
						}
					} else {
						return false;
					}
				}
			}
			return true;
		}
		if ((value instanceof Collection)
				&& ((Collection<?>) value).size() <= 0) {
			return true;
		}
		if ((value instanceof Dictionary)
				&& ((Dictionary<?, ?>) value).size() <= 0) {
			return true;
		}
		if ((value instanceof Map) && ((Map<?, ?>) value).size() <= 0) {
			return true;
		}
		return false;
	}

    /**
     * 实体类转换成Map
     * @param obj
     * @return
     */
    public static Map<String, Object> beanToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            logger.error("beanToMap 转换错误"+e.toString(), e);
        }
        return map;
    }


    /**
     * 实体类转换成Map
     * @param obj
     * @return
     */
    public static Map<String, String> beanToMap2(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, String.valueOf(value));
                }
            }
        } catch (Exception e) {
            logger.error("beanToMap 转换错误"+e.toString(), e);
        }
        return map;
    }

    /**
     * Map转换成Bean
     * @param type
     * @param map
     * @return
     */
    public static Object mapToBean(Class<?> type,Map<String,? extends Object> map){
        Object obj=null;
        try{
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            obj = type.newInstance();
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i< propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (map.containsKey(propertyName)) {
                    Object value = map.get(propertyName);
                    Object[] args = new Object[1];
                    args[0] = value;
                    descriptor.getWriteMethod().invoke(obj, args);
                }
            }
        }catch (Exception e){
            logger.error("mapToBean 转换错误"+e.toString(), e);
            throw new RuntimeException(e);
        }
        return obj;
    }
}
