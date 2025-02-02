package com.hanjx.dictdemo.controller;

import com.hanjx.dictdemo.domain.Result;
import com.hanjx.dictdemo.entity.Order;
import com.hanjx.dictdemo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/list")
    public Result<List<Order>> list() {
        return Result.success(orderService.list());
    }

    @GetMapping("/getById")
    public Object getById(Long id) {
        return Result.success(orderService.getById(id));
    }

}
