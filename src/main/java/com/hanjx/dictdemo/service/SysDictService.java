package com.hanjx.dictdemo.service;

import com.hanjx.dictdemo.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysDictService extends IService<SysDict> {

    /**
     * 重新加载字典缓存
     */
    void reload();

}
