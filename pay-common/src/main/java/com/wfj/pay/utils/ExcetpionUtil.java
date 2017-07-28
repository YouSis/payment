package com.wfj.pay.utils;

import com.wfj.exception.client.common.ErrorLevel;
import com.wfj.exception.client.handler.MyExceptionHandler;
import com.wfj.exception.client.util.PropertiesLoad;
import com.wfj.pay.constant.ExceptionTypeEnum;
import com.wfj.pay.framework.exception.BleException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


/**
 * Created by kongqf on 2017/7/25.
 */
@Configuration
public class ExcetpionUtil implements EnvironmentAware {
    private static Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    public static void sendException(BleException e) {

        MyExceptionHandler handler = new MyExceptionHandler();
        System.out.println(env.getProperty("exception.brokerList"));
        System.out.println(env.getProperty("exception.topic"));
        System.out.println(env.getProperty("exception.sysCode"));
        PropertiesLoad.putProperties("exception.brokerList", env.getProperty("exception.brokerList"));
        PropertiesLoad.putProperties("exception.topic", env.getProperty("exception.topic"));
        PropertiesLoad.putProperties("exception.sysCode", env.getProperty("exception.sysCode"));
        new MyExceptionHandler("110", "订单", "112", "订单号为空", e, com.wfj.ea.common.ErrorLevel.ERROR.getCode());


        String[] strArr = splitCode(((BleException) e).getCode());
        System.out.println(strArr[0]);
        System.out.println(strArr[1]);
        System.out.println(ExceptionTypeEnum.getErrorCodeName(strArr[0]));
        System.out.println(((BleException) e).getMessage());
        System.out.println(e);
        System.out.println(ErrorLevel.ERROR.getCode());
     /*   handler.putException("110", ExceptionTypeEnum.getErrorCodeName(strArr[0]), "001", "test",
                e, ErrorLevel.WARING.getCode());

        handler.putException(strArr[0], ExceptionTypeEnum.getErrorCodeName(strArr[0]), strArr[1],
                ((BleException) e).getMessage(), e, ErrorLevel.ERROR.getCode());*/
    }

    public static void splitExcetpion(BleException e) {
        try {


            PropertiesLoad.putProperties("exception.brokerList", env.getProperty("exception.brokerList"));
            PropertiesLoad.putProperties("exception.topic", env.getProperty("exception.topic"));
            PropertiesLoad.putProperties("exception.sysCode", env.getProperty("exception.sysCode"));


            String[] strArr = splitCode(((BleException) e).getCode());
//            boolean flag = ErrorCodeConstants.ErrorCode.vaildErrorCode(e.getCode());
            throw new MyExceptionHandler(strArr[0], ExceptionTypeEnum.getErrorCodeName(strArr[0]), strArr[1], ((BleException) e).getMessage(), e, ErrorLevel.ERROR.getCode());
        } catch (MyExceptionHandler e1) {
//			e1.printStackTrace();
        }
    }

    public static String[] splitCode(String code) {
        String bussCode = code.substring(2, 5);
        String errorCode = code.substring(5);
        if (errorCode.length() < 3) {
            errorCode = errorCode + "0";
        }
        String[] codeArr = new String[2];
        codeArr[0] = bussCode;
        codeArr[1] = errorCode;
        return codeArr;
    }
}
