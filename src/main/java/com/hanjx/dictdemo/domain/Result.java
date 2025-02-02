package com.hanjx.dictdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * 返回结果
 *
 * @Author 寒江雪
 * @Date 2025-02-01 20:07
 */
@Data
@AllArgsConstructor
@SuppressWarnings({"unchecked"})
public class Result<T> {

    private Boolean success;

    private Integer code;

    private String message;

    private T data;

    private Result() {
    }

    public static <T> Result<T> success() {
        return of(ResultConstant.SUCCESS, "", null);
    }

    public static <T> Result<T> success(T data) {
        return of(ResultConstant.SUCCESS, "", data);
    }

    public static <T> Result<T> failure(String message) {
        return of(ResultConstant.FAILURE, message, null);
    }

    private static <T> Result<T> of(ResultConstant resultConstant, String message, T data) {
        return new Result<>(resultConstant.getSuccess(), resultConstant.getCode(),
                message.isBlank() ? resultConstant.getMessage() : message, data);
    }

    public void setDataListMap(List<Map<String, Object>> data) {
        this.data = (T) data;
    }

    public void setDataMap(Map<String, Object> data) {
        this.data = (T) data;
    }

    @Getter
    @AllArgsConstructor
    public enum ResultConstant {

        SUCCESS(Boolean.TRUE, 200, "操作成功"),

        FAILURE(Boolean.FALSE, 500, "操作失败");

        private final Boolean success;

        private final Integer code;

        private final String message;

    }

}
