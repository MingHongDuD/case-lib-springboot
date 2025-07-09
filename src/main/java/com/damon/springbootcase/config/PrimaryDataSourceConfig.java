package com.damon.springbootcase.config;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * PrimaryDataSource 配置类（Spring Data JPA 的配置）
 *
 * @author damon du/minghongdud
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryPrimary",
        transactionManagerRef = "primaryTransactionManager",
        basePackages = "com.damon.springbootcase.repository.primary"
)
public class PrimaryDataSourceConfig {

    private final JpaProperties jpaProperties;

    private final DataSource primaryDataSource;

    @Value("${spring.jpa.hibernate.primary-dialect}")
    private String primaryDialect;

    public PrimaryDataSourceConfig(JpaProperties jpaProperties, @Qualifier("primaryDataSource") DataSource primaryDataSource) {
        this.jpaProperties = jpaProperties;
        this.primaryDataSource = primaryDataSource;
    }

    @Bean("entityManagerMasterPrimary")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
    }

    /**
     * 配置 JPA 的 EntityManagerFactory
     */
    @Bean(name = "entityManagerFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(primaryDataSource)
                .properties(getVendorProperties())
                .packages("com.damon.springbootcase.entity.primary")
                .persistenceUnit("primaryUnit")
                .build();
    }

    private Map<String, String> getVendorProperties() {
        Map<String, String> map = new HashMap<>();
        map.put("hibernate.dialect", primaryDialect);
        jpaProperties.setProperties(map);
        return jpaProperties.getProperties();
    }

    /**
     * 配置 JPA 的事务管理器
     * 添加空检查以避免 NullPointerException
     */
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
    }


}
