package com.hanjx.dictdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanjx.dictdemo.cache.dict.SysDictCache;
import com.hanjx.dictdemo.entity.SysDict;
import com.hanjx.dictdemo.service.SysDictService;
import com.hanjx.dictdemo.mapper.SysDictMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict>
        implements SysDictService {

    private final SysDictCache sysDictCache;

    /**
     * 重新加载字典缓存
     */
    @Override
    public void reload() {
        sysDictCache.reload();
    }

}




