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
 * FourthDataSource 配置类
 *
 * @author damon du/minghongdud
 */
@Configuration
@MapperScan(basePackages = "com.damon.mapper.fourth", sqlSessionFactoryRef = "fourthSqlSessionFactory")
public class FourthDataSourceConfig {

    /**
     * 配置 fourth 数据源的 SqlSessionFactory
     */
    @Bean(name = "fourthSqlSessionFactory")
    public SqlSessionFactory mybatisPlusSqlSessionFactory(@Qualifier("fourthDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/fourth/*.xml"));
        factoryBean.setTypeAliasesPackage("com.damon.entity.fourth");
        return factoryBean.getObject();
    }

    /**
     * 配置 fourth 数据源的 SqlSessionTemplate
     */
    @Bean(name = "fourthSqlSessionTemplate")
    public SqlSessionTemplate fourthSqlSessionTemplate(@Qualifier("fourthSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 配置 fourth 数据源的事务管理
     */
    @Bean(name = "fourthTransactionManager")
    public DataSourceTransactionManager transactionManagerFourth(@Qualifier("fourthDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
