package com.hanjx.dictdemo.config.aop;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * aop 配置
 *
 * @Author 寒江雪
 * @Date 2025-02-01 23:30
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopConfig {

}
