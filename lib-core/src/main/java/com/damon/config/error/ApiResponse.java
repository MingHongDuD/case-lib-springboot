package com.damon.config.error;

import lombok.Data;

/**
 * 统一 API 响应类，用于返回成功或错误响应。
 *
 * @author damon du/minghongdud
 */
@Data
public class ApiResponse<T> {

    /**
     * 响应状态码（成功或错误码）
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据（成功时返回数据，错误时可能为错误详情）
     */
    private T data;

    /**
     * 构造成功的响应
     */
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode("200");
        response.setMessage("Success");
        response.setData(data);
        return response;
    }

    /**
     * 构造错误的响应
     */
    public static <T> ApiResponse<T> error(String code, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
