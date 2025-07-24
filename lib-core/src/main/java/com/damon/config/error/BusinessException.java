package com.damon.config.error;

import lombok.Getter;

/**
 * 自定义业务异常，用于处理特定业务逻辑错误。
 *
 * @author damon du/minghongdud
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
