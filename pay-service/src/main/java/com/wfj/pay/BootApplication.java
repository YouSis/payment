package com.wfj.pay;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * Created by wjg on 2017/6/19.
 */
@SpringBootApplication(scanBasePackages = "com.wfj.pay",exclude ={DataSourceAutoConfiguration.class})
public class BootApplication {
    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class,args);
    }


}
