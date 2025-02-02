package com.hanjx.dictdemo.controller;

import com.hanjx.dictdemo.domain.Result;
import com.hanjx.dictdemo.entity.SysDict;
import com.hanjx.dictdemo.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/dict")
public class SysDictController {

    private final SysDictService sysDictService;

    @GetMapping("/list")
    public Result<List<SysDict>> list() {
        return Result.success(sysDictService.list());
    }

    @GetMapping("/reload")
    public Result<Void> reload() {
        sysDictService.reload();
        return Result.success();
    }

}
