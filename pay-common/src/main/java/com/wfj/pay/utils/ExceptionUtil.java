package com.wfj.pay.utils;

import com.wfj.exception.client.handler.MyExceptionHandler;
import com.wfj.exception.client.util.PropertiesLoad;
import com.wfj.pay.constant.ExceptionTypeEnum;
import com.wfj.pay.dto.BleException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


/**
 * Created by kongqf on 2017/7/25.
 */
@Configuration
public class ExceptionUtil implements EnvironmentAware {
    private static Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    public static void sendException(BleException e) {
        MyExceptionHandler handler = new MyExceptionHandler();
        PropertiesLoad.putProperties("exception.brokerList", env.getProperty("exception.brokerList"));
        PropertiesLoad.putProperties("exception.topic", env.getProperty("exception.topic"));
        PropertiesLoad.putProperties("exception.sysCode", env.getProperty("exception.sysCode"));

        String[] strArr = splitCode(e.getCode());
        handler.putException(strArr[0], ExceptionTypeEnum.getErrorCodeName(strArr[0]), strArr[1],
                e.getMessage(), e, e.getErrLever());
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
