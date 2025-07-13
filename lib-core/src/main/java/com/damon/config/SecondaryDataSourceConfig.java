package com.damon.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * secondary 数据库配置
 *
 * @author damon du/minghongdud
 */
@Configuration
@MapperScan(basePackages = "com.damon.mapper.secondary")
public class SecondaryDataSourceConfig {

    /**
     * 配置 secondary 数据源的 SqlSessionFactory
     */
    @Bean(name = "secondarySqlSessionFactory")
    public SqlSessionFactory mybatisPlusSqlSessionFactory(@Qualifier("secondaryDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/secondary/*.xml"));
        factoryBean.setTypeAliasesPackage("com.damon.entity.secondary");
        return factoryBean.getObject();
    }

    /**
     * 配置 secondary 数据源的 SqlSessionTemplate
     */
    @Bean(name = "secondarySqlSessionTemplate")
    public SqlSessionTemplate secondarySqlSessionTemplate(@Qualifier("secondarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "secondaryTransactionManager")
    public DataSourceTransactionManager transactionManagerSecondary(@Qualifier("secondaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
