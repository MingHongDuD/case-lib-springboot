package com.damon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;


/**
 * OAuth2 授权服务器配置，定义授权端点、令牌颁发和用户认证规则。
 *
 * @author damon du/minghongdud
 */
@Configuration
@EnableWebSecurity
public class AuthServerConfig {

    /**
     * 配置授权服务器的安全过滤链，处理 OAuth2 端点（如 /oauth2/authorize, /oauth2/token）。
     *
     * @param http HttpSecurity 配置对象
     * @return 安全过滤链
     * @throws Exception 配置异常
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        // 应用 OAuth2 授权服务器默认配置
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http
                .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(oidc -> oidc.clientRegistrationEndpoint())
                .and()
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
        return http.build();
    }
}
