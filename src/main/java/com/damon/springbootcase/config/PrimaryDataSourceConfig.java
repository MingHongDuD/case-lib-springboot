package com.damon.springbootcase.config;

import jakarta.persistence.EntityManager;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author ...
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager",
        basePackages = {PrimaryDataSourceConfig.REPOSITORY_PACKAGE}
)
public class PrimaryDataSourceConfig {

    static final String REPOSITORY_PACKAGE = "com.damon.springbootcase.dao.jpa.primary";
    static final String ENTITY_PACKAGE = "com.damon.springbootcase.entity.jpa.primary";

    private final DataSource primaryDataSource;

    private final JpaProperties jpaProperties;

    private LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(EntityManagerFactoryBuilder builder){
        return builder.data
    }

    public PrimaryDataSourceConfig(@Qualifier(value = "primaryDataSource") DataSource primaryDataSource, JpaProperties jpaProperties) {
        this.primaryDataSource = primaryDataSource;
        this.jpaProperties = jpaProperties;
    }

    public EntityManager primaryEntityManager(EntityManagerFactoryBuilder builder){
        return
    }
}
