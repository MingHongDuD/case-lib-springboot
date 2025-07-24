package com.damon.config.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 多数据源配置类，定义四个数据源（主、第二、第三、第四），绑定 application.properties 中的配置
 *
 * @author damon du/minghongdud
 */
@Configuration
public class DataSourceConfig {

    /**
     * 配置主数据源，绑定 spring.datasource.primary 的配置。
     * 使用 HikariCP 作为数据源实现，标记为 @Primary 优先注入。
     *
     * @return 主数据源
     */
    @Primary
    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置第二数据源，绑定 spring.datasource.second 的配置。
     *
     * @return 第二数据源
     */
    @Bean(name = "secondDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.second")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置第三数据源，绑定 spring.datasource.third 的配置。
     * 用于 MyBatis-Plus，通常连接 PostgreSQL 数据库。
     *
     * @return 第三数据源
     */
    @Bean(name = "thirdDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.third")
    public DataSource thirdDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置第四数据源，绑定 spring.datasource.fourth 的配置。
     * 用于 PostgreSQL 数据库。
     *
     * @return 第四数据源
     */
    @Bean(name = "fourthDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.fourth")
    public DataSource fourthDataSource() {
        return DataSourceBuilder.create().build();
    }

}