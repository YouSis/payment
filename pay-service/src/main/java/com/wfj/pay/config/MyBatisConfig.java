package com.wfj.pay.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.wfj.pay.aspect.ChooseDataSource;
import com.wfj.pay.aspect.HandleDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置双数据源，配置mybatis扫描，配置事务
 * Created by wjg on 2017/6/20.
 */
@Configuration  //相当于spring配置文件
public class MyBatisConfig implements EnvironmentAware {
    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    /**
     * 写数据源
     *
     * @return
     */
    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("spring.master.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.master.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.master.datasource.password"));
        dataSource.setInitialSize(20);
        dataSource.setMaxActive(200);
        dataSource.setMinIdle(20);
        dataSource.setMaxWait(60000);
        dataSource.setValidationQuery("SELECT 1 FROM DUAL");
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        return dataSource;
    }

    /**
     * 读数据源
     *
     * @return
     */
    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource slaveDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("spring.slave.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.slave.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.slave.datasource.password"));
        dataSource.setInitialSize(20);
        dataSource.setMaxActive(200);
        dataSource.setMinIdle(20);
        dataSource.setMaxWait(60000);
        dataSource.setValidationQuery("SELECT 1 FROM DUAL");
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        return dataSource;
    }

    @Bean
    @Primary
    public ChooseDataSource dataSource(@Qualifier("masterDataSource") DruidDataSource masterDataSource,
                                       @Qualifier("slaveDataSource") DruidDataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(HandleDataSource.MASTER_DS,masterDataSource);
        targetDataSources.put(HandleDataSource.SLAVE_DS,slaveDataSource);

        ChooseDataSource chooseDataSource = new ChooseDataSource();
        chooseDataSource.setTargetDataSources(targetDataSources);
        chooseDataSource.setDefaultTargetDataSource(masterDataSource);
        return chooseDataSource;

    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.wfj.pay.mapper");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(ChooseDataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package"));
        return  factoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(ChooseDataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

}
