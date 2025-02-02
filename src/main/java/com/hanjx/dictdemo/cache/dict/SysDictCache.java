package com.hanjx.dictdemo.cache.dict;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hanjx.dictdemo.cache.AbstractCacheProvider;
import com.hanjx.dictdemo.entity.SysDict;
import com.hanjx.dictdemo.mapper.SysDictMapper;
import com.hanjx.dictdemo.utils.RedisProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统字典缓存
 *
 * @Author 寒江雪
 * @Date 2025-02-01 18:58
 */
@Slf4j
@Component
@RequiredArgsConstructor
@SuppressWarnings({"unchecked"})
public class SysDictCache extends AbstractCacheProvider<Map<String, List<SysDict>>> {

    private final SysDictMapper sysDictMapper;

    private final RedisProcessor redisProcessor;

    /**
     * 初始化
     */
    @Override
    public void init() {
        List<SysDict> sysDictList = sysDictMapper.selectList(Wrappers.emptyWrapper());
        Map<String, List<SysDict>> dictListMap = sysDictList.stream()
                .collect(Collectors.groupingBy(SysDict::getDictType));
        log.info("初始化系统字典缓存【{}】组", dictListMap.size());
        if (CollectionUtils.isEmpty(dictListMap)) {
            return;
        }
        redisProcessor.set(RedisProcessor.RedisConstant.SYS_DICT, dictListMap);
    }

    @Override
    public Map<String, List<SysDict>> get() {
        return redisProcessor.get(RedisProcessor.RedisConstant.SYS_DICT, Map.class);
    }

    @Override
    public void clear() {
        redisProcessor.clear(RedisProcessor.RedisConstant.SYS_DICT);
    }

}
