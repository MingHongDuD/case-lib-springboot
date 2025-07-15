package com.damon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * （过滤器和拦截器的配置类）
 *
 * @author damon
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    ObjectMapper mapper;

    //    @Value("${cors.allowed_origins}")
//    private String[] allowedOrigins;
//    @Value("${cors.allowed_methods}")
//    private String[] allowedMethods;
    @Value("${cors.max_age:3600}")
    private Long maxAge;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").maxAge(maxAge).allowedMethods("GET", "POST");
    }
}
