package com.hanjx.dictdemo.cache;

/**
 * 缓存抽象类
 *
 * @Author 寒江雪
 * @Date 2025-02-01 18:56
 */
public abstract class AbstractCacheProvider<T> implements CacheProvider {

    /**
     * 获取缓存
     *
     * @return 缓存值
     */
    protected abstract T get();

    /**
     * 重新加载缓存
     */
    @Override
    public void reload() {
        clear();
        init();
    }

}
