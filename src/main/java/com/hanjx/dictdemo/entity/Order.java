package com.hanjx.dictdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hanjx.dictdemo.annotation.Dict;
import lombok.Data;

@TableName(value = "tb_order")
@Data
public class Order implements Serializable {
    /**
     * 订单ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 状态 1-待支付 2-已支付
     */
    @Dict(dictType = "order_status")
    private Integer status;
}