package com.damon.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * 数据库连接相关配置类
 *
 * @author damon du/minghongdud
 */
@Configuration
public class EnvSetupHelper {

    /**
     * 创建数据库连接池
     */
    public DataSource dataSource(String userName, String password, String url, String diverClass) {
        return DataSourceBuilder.create()
                .username(userName)
                .password(password)
                .url(url)
                .driverClassName(diverClass)
                .build();
    }

    /**
     * Spring JDBC 的工具类，简化数据库操作
     */
    public JdbcTemplate getJdbcTemplate(String userName, String password, String url, String diverClass) {
        return new JdbcTemplate(dataSource(userName, password, url, diverClass));
    }
}
