package com.damon.springbootcase.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * MyBatis-Plus 配置类，专用于 MyBatis-Plus 的配置
 *
 * @author damon du/minghongdud
 */
@Configuration
@MapperScan(basePackages = "com.damon.springbootcase.entity.secondary")
public class MybatisPlusConfig {

    @Bean(name = "mybatisPlusSqlSessionFactory")
    public SqlSessionFactory mybatisPlusSqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResource("classpath*:mapper/mybatis/*.xml"));
        return factoryBean.getObject();
    }
}
