package com.wfj.pay.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 用于静态方法获取spring的对象
 * Created by wjg on 2017/6/23.
 */
@Component
public class SpringContextAware implements ApplicationContextAware {
    private static Logger logger = LoggerFactory.getLogger(SpringContextAware.class);
    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 获取spring的上下文对象
     * @return
     */
    public static ApplicationContext getApplicationContext(){
        if(context == null){
            logger.error("当前context为空,可能是Spring配置文件中没有配置加载本类[{}]!",SpringContextAware.class.getName());
            throw new IllegalStateException("当前没有Spring的applicationContext注入,请确定是否有配置Spring,并在Spring中配置了本类的注入!" + SpringContextAware.class.getName());
        }
        return context;
    }

    /**
     * 根据bean名称查找bean
     * @param beanName
     * @param <T>
     * @return
     */
    public static <T> T getBeanByName(String beanName){
        try{
            return (T)getApplicationContext().getBean(beanName);
        }catch (Exception e){
            logger.error(e.toString(),e);
            throw new IllegalStateException("在Spring上下文查找对象出错:" + beanName);
        }
    }

    /**
     * 根据bean的类型找到bean
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> T getBeanByType(Class<? extends T> beanType){
        try {
            String[] beanNames = getApplicationContext().getBeanNamesForType(beanType);
            if (beanNames != null && beanNames.length == 1) {
                return (T) getApplicationContext().getBean(beanNames[0]);
            }

            if (beanNames == null || beanNames.length == 0) {
                throw new IllegalStateException("未找到指定类型的Bean定义.");
            }

            throw new IllegalStateException("找到多个同类型的Bean定义.");

        } catch (Throwable th) {
            logger.error(th.toString(), th);
            throw new IllegalStateException("根据类型在Spring上下文查找对象出错:" + beanType);
        }
    }
}
