package com.damon.config;

import com.damon.config.security.PermissionCheckInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置类，定义拦截器。
 *
 * @author damon du/minghongdud
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ObjectMapper mapper;

    /**
     * 配置 PermissionCheckInterceptor
     */
    @Bean
    PermissionCheckInterceptor permissionCheckInterceptor() {
        return new PermissionCheckInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionCheckInterceptor()).addPathPatterns("/damon/**");
    }
}
