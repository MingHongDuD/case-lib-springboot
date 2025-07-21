package com.damon.config;

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
import java.util.Objects;

/**
 * SecondDataSource 配置类
 *
 * @author damon du/minghongdud
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactorySecond",
        transactionManagerRef = "secondTransactionManager",
        basePackages = "com.damon.repository.second"
)
public class SecondDataSourceConfig {

    private final JpaProperties jpaProperties;
    private final DataSource secondDataSource;

    // 数据库方言
//    @Value("${spring.jpa.database.mysql-platform}")
//    public String mySqlDialect;

    public SecondDataSourceConfig(JpaProperties jpaProperties,
                                  @Qualifier("secondDataSource") DataSource secondDataSource) {
        this.jpaProperties = jpaProperties;
        this.secondDataSource = secondDataSource;
    }

    @Bean("entityManagerSecond")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return Objects.requireNonNull(entityManagerFactorySecond(builder).getObject()).createEntityManager();
    }

    /**
     * 配置第二数据源的 EntityManagerFactory
     */
    @Bean(name = "entityManagerFactorySecond")
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySecond(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(secondDataSource)
                .properties(getVendorProperties())
                .packages("com.damon.entity.Second")
                .persistenceUnit("SecondUnit")
                .build();
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
     * 配置第二数据源的事务管理器
     */
    @Bean(name = "secondTransactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactorySecond(builder).getObject()));
    }

}
