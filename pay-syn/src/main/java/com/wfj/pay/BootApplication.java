package com.wfj.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by wjg on 2017/6/19.
 */
@SpringBootApplication(scanBasePackages = "com.wfj.pay")
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class,args);
    }


}
