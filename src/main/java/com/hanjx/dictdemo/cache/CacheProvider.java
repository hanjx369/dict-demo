package com.hanjx.dictdemo.cache;

/**
 * 缓存提供者 接口
 *
 * @Author 寒江雪
 * @Date 2025-02-01 18:53
 */
public interface CacheProvider {

    /**
     * 初始化
     */
    void init();

    /**
     * 清空缓存
     */
    void clear();

    /**
     * 重新加载缓存
     */
    void reload();

}
