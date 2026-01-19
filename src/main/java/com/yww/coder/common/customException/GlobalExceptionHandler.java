package com.yww.coder.common.customException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

import com.yww.coder.common.result.BaseException;
import com.yww.coder.common.result.BaseResponse;

import io.swagger.v3.oas.annotations.Hidden;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 全局异常处理器
 * 统一处理 BaseException 异常体系
 */
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理 BaseException 异常
     */
    @ExceptionHandler(BaseException.class)
    public BaseResponse<?> baseExceptionHandler(BaseException e) {
        log.error("BaseException: {}", e.getMessage(), e);
        Integer subStatusCode = e.getSubStatusCode();
        String message = e.getErrorMessage();
        
        // 如果异常有自定义消息，使用自定义消息
        if (e.getMessage() != null && !e.getMessage().equals(message)) {
            message = e.getMessage();
        }
        
        return new BaseResponse<>(subStatusCode, message, Collections.emptyList());
    }

    /**
     * 处理 RuntimeException 异常
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException: {}", e.getMessage(), e);
        return new BaseResponse<>(50000, "系统错误", Collections.emptyList());
    }

    /**
     * 处理 Exception 异常（兜底）
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse<?> exceptionHandler(Exception e) {
        log.error("Exception: {}", e.getMessage(), e);
        return new BaseResponse<>(50000, "系统内部异常", Collections.emptyList());
    }
}
