package com.damon.config.db;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 主数据源的 JPA 配置类，定义实体管理器、实体管理器工厂和事务管理器。
 * 关联 com.damon.repository.primary 包中的 JPA 仓库。
 *
 * @author damon du/minghongdud
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryPrimary"
        , transactionManagerRef = "primaryTransactionManager"
        , basePackages = "com.damon.repository.primary")
public class PrimaryDataSourceConfig {

    private static final String DEFAULT_DIALECT = "org.hibernate.dialect.MySQLDialect";
    private final JpaProperties jpaProperties;
    private final DataSource primaryDataSource;

    /**
     * 构造函数注入 JpaProperties 和主数据源。
     *
     * @param jpaProperties     JPA 配置属性
     * @param primaryDataSource 主数据源
     */
    public PrimaryDataSourceConfig(JpaProperties jpaProperties, @Qualifier("primaryDataSource") DataSource primaryDataSource) {
        this.jpaProperties = jpaProperties;
        this.primaryDataSource = primaryDataSource;
    }

    /**
     * 创建主数据源的 EntityManager。
     *
     * @param builder EntityManagerFactoryBuilder
     * @return EntityManager 实例
     */
    @Bean("entityManagerPrimary")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return Objects.requireNonNull(entityManagerFactoryPrimary(builder).getObject()).createEntityManager();
    }

    /**
     * 配置主数据源的 EntityManagerFactory，绑定主数据源和实体包。
     *
     * @param builder EntityManagerFactoryBuilder
     * @return EntityManagerFactoryBean
     */
    @Primary
    @Bean(name = "entityManagerFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(primaryDataSource)
                .properties(getVendorProperties())
                .packages("com.damon.entity.primary")
                .persistenceUnit("primaryUnit")
                .build();
    }

    /**
     * 配置 JPA 属性（如 Hibernate 方言、DDL 策略等）。
     *
     * @return JPA 属性映射
     */
    private Map<String, String> getVendorProperties() {
        Map<String, String> map = new HashMap<>(jpaProperties.getProperties());
        // 设置 Hibernate 方言，从配置文件读取，默认为 MySQL
        map.putIfAbsent("hibernate.dialect", jpaProperties.getProperties().getOrDefault("hibernate.dialect", DEFAULT_DIALECT));
        // 设置 DDL 策略， update
        map.putIfAbsent("hibernate.hbm2ddl.auto", "update");
        return map;
    }

    /**
     * 配置主数据源的事务管理器。
     *
     * @param builder EntityManagerFactoryBuilder
     * @return 事务管理器
     */
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactoryPrimary(builder).getObject()));
    }

}
