package com.hanjx.dictdemo.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 寒江雪
 * @CreateTime: 2025-02-06
 * @Description: 序列化配置
 * @Version: 1.0
 */
@Slf4j
@Configuration
public class SerializationConfig {

    @Bean
    public ObjectMapper objectMapper() {

        log.info("加载序列化配置...");

        ObjectMapper objectMapper = new ObjectMapper();
        // 反序列化时，如果json字符串中存在未知属性，则忽略不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }

}
