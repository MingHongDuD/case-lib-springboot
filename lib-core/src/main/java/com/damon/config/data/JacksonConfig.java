package com.damon.config.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Jackson 配置类，用于自定义 JSON 序列化/反序列化行为
 *
 * @author damon du/minghongdud
 */
@Configuration
public class JacksonConfig {

    private static final Logger logger = LogManager.getLogger(JacksonConfig.class);
    private final ObjectMapper objectMapper;
    private final ObjectMapper jacksonObjectMapper;

    public JacksonConfig(ObjectMapper objectMapper, ObjectMapper jacksonObjectMapper) {
        this.objectMapper = objectMapper;
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        logger.info("Initializing Jackson ObjectMapper with custom configurations");

        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        // 注册 Java 时间模块
        objectMapper.registerModule(new JavaTimeModule());
        // 禁止将日期序列化为时间戳，保持 ISO 格式
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 设置全局日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        objectMapper.setTimeZone(TimeZone.getTimeZone("UTC"));

        // 非空字段序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 忽略未知字段（反序列化时，JSON 中多余字段不报错）
        objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 美化 JSON 输出
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        return objectMapper;
    }
}
