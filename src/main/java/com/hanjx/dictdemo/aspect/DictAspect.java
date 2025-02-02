package com.hanjx.dictdemo.aspect;

import com.alibaba.fastjson2.JSONArray;
import com.hanjx.dictdemo.annotation.Dict;
import com.hanjx.dictdemo.cache.dict.SysDictCache;
import com.hanjx.dictdemo.domain.Result;
import com.hanjx.dictdemo.entity.SysDict;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 字典处理切面
 *
 * @Author 寒江雪
 * @Date 2025-02-01 17:35
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DictAspect {

    private final SysDictCache sysDictCache;

    static {
        log.info("【{}】切面被加载", DictAspect.class.getName());
    }

    @Pointcut("execution(* com.hanjx.dictdemo.controller.*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = joinPoint.proceed();

        if (obj instanceof Result<?> result) {
            Object data = result.getData();
            if (data instanceof List) {
                result.setDataListMap(transformToListMap((List<?>) data));
            } else if (data != null) {
                result.setDataMap(transformToMap(Collections.singletonList(data)));
            }
        }

        return obj;
    }

    /**
     * 将对象转换为Map
     *
     * @param dataList 对象列表
     * @return List<Map < String, Object>>
     */
    private List<Map<String, Object>> transformToListMap(List<?> dataList) {
        List<Map<String, Object>> dataListMap = dataList.stream().map(this::convertObjectToMap).toList();
        // 获取类名
        String className = dataList.getFirst().getClass().getName();
        // 设置字段
        setDictFields(className, dataListMap);

        return dataListMap;
    }

    /**
     * 将对象转换为Map
     *
     * @param dataList 对象
     * @return Map<String, Object>
     */
    private Map<String, Object> transformToMap(List<?> dataList) {
        List<Map<String, Object>> dataListMap = dataList.stream().map(this::convertObjectToMap).toList();
        // 获取类名
        String className = dataList.getFirst().getClass().getName();
        // 设置字段
        setDictFields(className, dataListMap);

        return dataListMap.getFirst();
    }

    /**
     * 将对象转换为Map
     *
     * @param item 对象
     * @return Map<String, Object>
     */
    private Map<String, Object> convertObjectToMap(Object item) {
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = item.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            try {
                field.setAccessible(true);
                map.put(field.getName(), field.get(item));
            } catch (IllegalAccessException e) {
                log.error("访问字段时出错: {}", field.getName(), e);
                throw new RuntimeException(e);
            }
        }

        return map;
    }

    /**
     * 获取被Dict注解的字段
     *
     * @param className 类名
     * @param item      对象列表
     */
    private void setDictFields(String className, List<Map<String, Object>> item) {
        Field[] declaredFields;
        try {
            declaredFields = Class.forName(className).getDeclaredFields();
            item.forEach(o -> {
                for (Field field : declaredFields) {
                    // 判断是否被Dict注解标注
                    if (field.isAnnotationPresent(Dict.class)) {
                        // 获取Dict注解
                        Dict dict = field.getAnnotation(Dict.class);
                        // 获取字段名
                        String fieldName = field.getName();
                        // 获取字典类型
                        String dictType = dict.dictType();
                        if (!dictType.isBlank()) {
                            Map<String, List<SysDict>> dictListMap = sysDictCache.get();
                            List<SysDict> sysDictList = JSONArray.parseArray(dictListMap.get(dictType).toString(), SysDict.class);
                            Map<String, String> dictMap = sysDictList.stream()
                                    .collect(Collectors.toMap(SysDict::getCode, SysDict::getLabel));
                            // 获取label字段名
                            String labelName = fieldName + "Label";
                            // 获取label字段值
                            String fieldValue = String.valueOf(o.get(fieldName));
                            String labelValue = dictMap.get(fieldValue);
                            o.put(labelName, labelValue);
                        }
                    }
                }
            });
        } catch (ClassNotFoundException e) {
            log.error("获取类时出错: {}", className, e);
            throw new RuntimeException(e);
        }
    }

}
