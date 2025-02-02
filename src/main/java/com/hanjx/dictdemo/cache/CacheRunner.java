package com.hanjx.dictdemo.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 缓存热启动
 *
 * @Author 寒江雪
 * @Date 2025-02-01 19:57
 */
@Component
@RequiredArgsConstructor
public class CacheRunner implements CommandLineRunner {

    private final ApplicationContext applicationContext;

    /**
     * 缓存热启动
     *
     * @param args 命令行参数
     */
    @Override
    public void run(String... args) {
        Map<String, CacheProvider> cacheProviderMap =
                applicationContext.getBeansOfType(CacheProvider.class);
        cacheProviderMap.forEach((beanName, cacheProvider) -> {
            cacheProvider.init();
        });
    }

}
