package com.wfj.pay.config;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;
import com.baidu.disconf.client.addons.properties.ReloadablePropertiesFactoryBean;
import com.baidu.disconf.client.addons.properties.ReloadingPropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by wjg on 2017/7/28.
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DisConfConfig {

    @Bean(destroyMethod = "destroy")
    public DisconfMgrBean disconfMgrBean(){
        DisconfMgrBean disconfMgrBean = new DisconfMgrBean();
        disconfMgrBean.setScanPackage("com.wfj.pay");
        return disconfMgrBean;
    }

    @Bean(initMethod = "init",destroyMethod = "destroy")
    public DisconfMgrBeanSecond disconfMgrBean2(){
        return new DisconfMgrBeanSecond();
    }

    @Bean
    public ReloadablePropertiesFactoryBean configproperties_disconf(){
        ReloadablePropertiesFactoryBean factoryBean = new ReloadablePropertiesFactoryBean();
        List<String> locations = new ArrayList<>();
        locations.add("classpath:/store.properties");
        factoryBean.setLocations(locations);
        return factoryBean;
    }

    @Bean
    public ReloadingPropertyPlaceholderConfigurer propertyConfigurer(ReloadablePropertiesFactoryBean configproperties_disconf) throws IOException {
        ReloadingPropertyPlaceholderConfigurer configurer = new ReloadingPropertyPlaceholderConfigurer();
        configurer.setIgnoreResourceNotFound(true);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        configurer.setPropertiesArray(new Properties[]{configproperties_disconf.getObject()});
        return configurer;
    }
}
