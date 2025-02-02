package com.hanjx.dictdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanjx.dictdemo.entity.Order;
import com.hanjx.dictdemo.service.OrderService;
import com.hanjx.dictdemo.mapper.OrderMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




