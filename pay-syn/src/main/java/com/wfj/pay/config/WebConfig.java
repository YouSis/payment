package com.wfj.pay.config;

import com.wfj.netty.servlet.filter.Monitor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wjg on 2017/7/27.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
    @Value("${server.port}")
    private int port;
    @Bean
    public  FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean registration = new FilterRegistrationBean(new Monitor(port));
        registration.addUrlPatterns("/*");
        return registration;
    }
}
