package com.hanjx.dictdemo.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis 操作工具类
 *
 * @Author 寒江雪
 * @Date 2025-02-01 17:42
 */
@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class RedisProcessor {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置缓存
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    public <T> void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存
     *
     * @param key     缓存key
     * @param value   缓存value
     * @param timeout 过期时间（秒）
     */
    public <T> void set(String key, T value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取缓存
     *
     * @param key 缓存key
     * @return 缓存value
     */
    public <T> T get(String key, Class<T> clazz) {
        return clazz.cast(redisTemplate.opsForValue().get(key));
    }

    /**
     * 清除缓存
     *
     * @param key 缓存key
     */
    public void clear(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Redis 常量
     */
    public static final class RedisConstant {

        /**
         * 系统字典
         */
        public static final String SYS_DICT = "sys_dict_list:";

        /**
         * 拼接缓存key
         *
         * @param prefix 前缀
         * @param suffix 后缀
         * @return 缓存key
         */
        public static String build(String prefix, String suffix) {
            return prefix + suffix;
        }

    }

}
