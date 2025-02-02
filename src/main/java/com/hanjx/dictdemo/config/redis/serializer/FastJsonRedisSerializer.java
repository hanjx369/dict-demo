package com.hanjx.dictdemo.config.redis.serializer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Redis序列化器
 *
 * @Author 寒江雪
 * @Date 2025-02-01 17:10
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final Class<T> clazz;

    public FastJsonRedisSerializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 序列化对象
     *
     * @param t 对象
     * @return 字节数组
     * @throws SerializationException 抛出序列化异常
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        String jsonString = JSON.toJSONString(t,
                JSONWriter.Feature.WriteMapNullValue,
                JSONWriter.Feature.WriteNullListAsEmpty);
        return jsonString.getBytes(DEFAULT_CHARSET);
    }


    /**
     * 反序列化对象
     *
     * @param bytes 字节数组
     * @return T
     * @throws SerializationException 抛出序列化异常
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        // 使用 fastjson2 的 parseObject 方法反序列化对象
        return JSON.parseObject(str, clazz);
    }

}
