package com.damon.config.error;

import lombok.Getter;

/**
 * 自定义未授权异常，用于处理认证失败。
 *
 * @author damon du/minghongdud
 */
@Getter
public class UnauthorizedException extends RuntimeException {

    private final String code;

    public UnauthorizedException(String code, String message) {
        super(message);
        this.code = code;
    }

    public UnauthorizedException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
