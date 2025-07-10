package com.damon.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Secondary 数据源的事务管理器配置
 *
 * @author damon du/minghongdud
 */
@Configuration
@EnableTransactionManagement
public class SecondaryTransactionConfig {
    @Bean(name = "transactionManagerSecondary")
    public DataSourceTransactionManager transactionManagerSecondary(
            @Qualifier("secondaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
