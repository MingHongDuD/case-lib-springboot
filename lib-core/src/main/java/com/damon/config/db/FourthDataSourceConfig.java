package com.damon.config.db;

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
 * 第四数据源的 MyBatis-Plus 配置类，定义 SqlSessionFactory、SqlSessionTemplate 和事务管理器。
 * 关联 com.damon.mapper.fourth 包中的 MyBatis 映射器，连接 PostgreSQL 数据库。
 *
 * @author damon du/minghongdud
 */
@Configuration
@MapperScan(basePackages = "com.damon.mapper.fourth", sqlSessionFactoryRef = "fourthSqlSessionFactory")
public class FourthDataSourceConfig {

    /**
     * 配置第四数据源的 SqlSessionFactory，使用 MyBatis-Plus。
     * 加载 XML 映射文件和实体包，设置全局配置（如下划线转驼峰）。
     *
     * @param dataSource 第四数据源（PostgreSQL）
     * @return SqlSessionFactory
     * @throws Exception 如果 XML 映射文件加载失败
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
     * 配置第四数据源的 SqlSessionTemplate，用于 MyBatis 操作。
     *
     * @param sqlSessionFactory 第四数据源的 SqlSessionFactory
     * @return SqlSessionTemplate
     */
    @Bean(name = "fourthSqlSessionTemplate")
    public SqlSessionTemplate fourthSqlSessionTemplate(@Qualifier("fourthSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 配置第四数据源的事务管理器。
     *
     * @param dataSource 第四数据源
     * @return DataSourceTransactionManager
     */
    @Bean(name = "fourthTransactionManager")
    public DataSourceTransactionManager transactionManagerFourth(@Qualifier("fourthDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
