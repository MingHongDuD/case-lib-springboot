package com.damon.config.ware;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.PostgreKeyGenerator;
import org.apache.ibatis.session.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;


/**
 * MyBatis-Plus 全局配置类，定义主键生成策略和全局 Configuration。
 * 适用于第三和第四数据源（PostgreSQL）。
 *
 * @author damon du/minghongdud
 */
@org.springframework.context.annotation.Configuration
public class MybatisPlusConfig {

    private static final Logger logger = LogManager.getLogger(MybatisPlusConfig.class);

    /**
     * 配置 MyBatis-Plus 全局 Configuration，设置下划线转驼峰和 SQL 日志。
     *
     * @return MyBatis Configuration 对象
     */
    @Bean(name = "mybatisConfiguration")
    public Configuration mybatisConfiguration() {
        Configuration configuration = new Configuration();
        // 开启下划线转驼峰映射
        configuration.setMapUnderscoreToCamelCase(true);
        // 启用 SQL 日志
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        // 启用缓存
        configuration.setCacheEnabled(true);
        logger.info("Initialized MyBatis-Plus global configuration with map-underscore-to-camel-case and SQL logging");
        return configuration;
    }

    /**
     * 配置 PostgreSQL 主键生成策略，使用序列（Sequence）生成主键。
     * 适用于 PostgreSQL 数据库的第三和第四数据源。
     *
     * @return IKeyGenerator
     */
    @Bean
    public IKeyGenerator keyGenerator() {
        logger.info("Initialized PostgreSqlKeyGenerator for MyBatis-Plus");
        return new PostgreKeyGenerator();
    }
}
