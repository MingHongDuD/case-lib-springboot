package com.damon.exception;

/**
 * 自定义异常类，用于参数格式化错误
 */

public class ParameterFormattingException extends RuntimeException {
    public ParameterFormattingException(String message, Throwable cause) {
        super(message, cause);
    }
}
