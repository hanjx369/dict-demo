package com.hanjx.dictdemo.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis-Plus 配置
 *
 * @Author 寒江雪
 * @Date 2025-02-01 17:04
 */
@Configuration
@MapperScan("com.hanjx.dictdemo.mapper")
public class MybatisPlusConfig {

}
