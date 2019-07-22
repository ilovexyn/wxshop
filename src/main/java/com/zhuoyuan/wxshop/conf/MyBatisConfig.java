package com.zhuoyuan.wxshop.conf;

import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhao.li
 */
@Configuration
public class MyBatisConfig {

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType(DBType.MYSQL.name());
        // 开启 PageHelper 的支持
        page.setLocalPage(true);
        return page;
    }
}
