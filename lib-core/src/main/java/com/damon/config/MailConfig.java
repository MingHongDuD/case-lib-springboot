package com.damon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * 邮件相关配置类
 *
 * @author damon du/minghongdud
 */
@Configuration
public class MailConfig {

    // 邮件默认编码
    @Value("${spring.mail.default-encoding}")
    private String defaultEncoding;

    // 邮件服务器地址
    @Value("${spring.mail.host}")
    private String host;

    // 邮件服务器端口
    @Value("${spring.mail.port}")
    private Integer port;

    // 邮件发送账户
    @Value("${spring.mail.username}")
    private String username;

    // 邮件发送授权码
    @Value("${spring.mail.password}")
    private String password;

    // 邮件使用协议
    @Value("${spring.mail.protocol}")
    private String protocol;

    // 连接超时时间（毫秒）
    @Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
    private int connectionTimeout;

    // 超时时间（毫秒）
    @Value("${spring.mail.properties.mail.smtp.timeout}")
    private int timeout;

    // 写入超时时间（毫秒）
    @Value("${spring.mail.properties.mail.smtp.writetimeout}")
    private int writeTimeout;

    // 是否需要认证
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;

    // 是否启用STARTTLS
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttlsEnable;

    // 是否强制要求STARTTLS
    @Value("${spring.mail.properties.mail.smtp.starttls.required}")
    private boolean starttlsRequired;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // 设置基础属性
        mailSender.setDefaultEncoding(defaultEncoding);
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setProtocol(protocol);

        // 设置扩展属性
        mailSender.setJavaMailProperties(getProperties());

        return mailSender;
    }

    /**
     * 设置扩展属性
     */
    private Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.connectiontimeout", String.valueOf(connectionTimeout));
        properties.setProperty("mail.smtp.timeout", String.valueOf(timeout));
        properties.setProperty("mail.smtp.writetimeout", String.valueOf(writeTimeout));
        properties.setProperty("mail.smtp.auth", String.valueOf(auth));
        properties.setProperty("mail.smtp.starttls.enable", String.valueOf(starttlsEnable));
        properties.setProperty("mail.smtp.starttls.required", String.valueOf(starttlsRequired));
        return properties;
    }
}
