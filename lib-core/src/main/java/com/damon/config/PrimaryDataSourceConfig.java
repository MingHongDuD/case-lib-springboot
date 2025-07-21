package com.damon.config;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
 * PrimaryDataSource 配置类
 *
 * @author damon du/minghongdud
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryPrimary", transactionManagerRef = "primaryTransactionManager", basePackages = "com.damon.repository.primary")
public class PrimaryDataSourceConfig {

    private final JpaProperties jpaProperties;
    private final DataSource primaryDataSource;


    public PrimaryDataSourceConfig(JpaProperties jpaProperties, @Qualifier("primaryDataSource") DataSource primaryDataSource) {
        this.jpaProperties = jpaProperties;
        this.primaryDataSource = primaryDataSource;
    }

    @Bean("entityManagerPrimary")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return Objects.requireNonNull(entityManagerFactoryPrimary(builder).getObject()).createEntityManager();
    }

    /**
     * 配置主数据源的 EntityManagerFactory
     */
    @Primary
    @Bean(name = "entityManagerFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(primaryDataSource).properties(getVendorProperties()).packages("com.damon.entity.primary").persistenceUnit("primaryUnit").build();
    }

    /**
     * 自定义JPA的基础配置
     */
    private Map<String, String> getVendorProperties() {
        Map<String, String> map = new HashMap<>();
//        map.put("hibernate.dialect", mySqlDialect);
        jpaProperties.setProperties(map);
        return jpaProperties.getProperties();
    }

    /**
     * 配置主数据源的事务管理器
     */
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactoryPrimary(builder).getObject()));
    }

}
