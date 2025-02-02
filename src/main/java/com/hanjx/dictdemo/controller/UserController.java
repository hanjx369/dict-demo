package com.hanjx.dictdemo.controller;

import com.hanjx.dictdemo.domain.Result;
import com.hanjx.dictdemo.entity.User;
import com.hanjx.dictdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    public Result<List<User>> list() {
        return Result.success(userService.list());
    }

    @GetMapping("/getById")
    public Result<User> getById(Long id) {
        return Result.success(userService.getById(id));
    }

}
