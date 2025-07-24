package com.damon.config.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 全局异常管理类，捕获并处理控制器抛出的异常，返回统一格式的响应。
 * 使用 Log4j2 记录异常日志，集成 com.damon Logger 配置。
 *
 * @author damon du/minghongdud
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理验证异常（如 @NotEmpty、@Valid 失败）
     *
     * @param ex MethodArgumentNotValidException
     * @return 包含验证错误详情的响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : Objects.requireNonNull(ex.getBindingResult()).getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        logger.warn("Validation failed: {}", errors, ex);
        ApiResponse<Map<String, String>> response = ApiResponse.error("400", "Validation failed", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理数据库异常（如 JPA 或 MyBatis-Plus 操作失败）
     *
     * @param ex DataAccessException
     * @return 包含数据库错误信息的响应
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<String>> handleDataAccessException(DataAccessException ex) {
        logger.error("Database error occurred: {}", ex.getMessage(), ex);
        ApiResponse<String> response = ApiResponse.error("500", "Database error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理自定义业务异常
     *
     * @param ex BusinessException
     * @return 包含业务错误信息的响应
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<String>> handleBusinessException(BusinessException ex) {
        logger.error("Business error occurred: code={}, message={}", ex.getCode(), ex.getMessage(), ex);
        ApiResponse<String> response = ApiResponse.error(ex.getCode(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理未授权异常
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<String>> handleUnauthorizedException(UnauthorizedException ex) {
        logger.error("Unauthorized error occurred: code={}, message={}", ex.getCode(), ex.getMessage(), ex);
        ApiResponse<String> response = ApiResponse.error(ex.getCode(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 处理未捕获的通用异常（兜底处理）
     *
     * @param ex Exception
     * @return 包含通用错误信息的响应
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
        logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        ApiResponse<String> response = ApiResponse.error("500", "Internal server error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
