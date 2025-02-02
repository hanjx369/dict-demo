package com.hanjx.dictdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hanjx.dictdemo.annotation.Dict;
import lombok.Data;

import java.io.Serializable;

@TableName(value = "tb_user")
@Data
public class User implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 性别（1-男，2-女）
     */
    @Dict(dictType = "sex")
    private Integer sex;

}