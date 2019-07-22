package com.zhuoyuan.wxshop.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.zhuoyuan.wxshop.mapper")
public class DataSourceConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        System.out.println("DataSource");
        return new DruidDataSource();
    }

    @Bean
    public DataSourceTransactionManager firstTransactionManager() {
        return new DataSourceTransactionManager(druid());
    }

}
