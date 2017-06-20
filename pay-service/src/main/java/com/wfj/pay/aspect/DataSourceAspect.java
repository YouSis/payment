package com.wfj.pay.aspect;

import com.wfj.pay.annotation.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切换数据源
 * @author ghost
 */
@Aspect
@Order(-9999)   //此注解保证切换数据源要在开启事务之前，否则数据源切换不成功
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DataSourceAspect {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Pointcut("execution(* com.wfj.pay.service.impl.*.*(..))")
	public void aspect() {
	}

	/**
	 * 配置前置通知,使用在方法aspect()上注册的切入点
	 */
	@Before("aspect()")
	public void before(JoinPoint point) throws NoSuchMethodException {
		Method proxyMethod = ((MethodSignature)point.getSignature()).getMethod();
		Method sourceMethod = point.getTarget().getClass().getMethod(proxyMethod.getName(),proxyMethod.getParameterTypes());
		if(sourceMethod!=null && sourceMethod.isAnnotationPresent(DataSource.class)){
			DataSource ds = sourceMethod.getAnnotation(DataSource.class);
			HandleDataSource.putDataSource(ds.value());
			if(logger.isDebugEnabled()){
				logger.debug("current methodName is "+sourceMethod.getName()+",Annotation DataSource value is "+ds.value()+",so switch to "+ds.value()+"DataSource");
			}
		}
	}
}
