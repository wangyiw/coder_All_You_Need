package com.yww.coder.core.customException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yww.coder.core.enums.base.StatusEnum;
import com.yww.coder.core.result.BaseException;
import com.yww.coder.core.result.BaseResponse;
import com.yww.coder.core.result.ResultFactory;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理器
 * 统一处理 BaseException 异常体系
 */
@Hidden
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理 BaseException 异常（core 包的异常体系）
     */
    @ExceptionHandler(BaseException.class)
    public BaseResponse<?> baseExceptionHandler(BaseException e) {
        log.error("BaseException: {}", e.getMessage(), e);
        return ResultFactory.getFailureResult(e.getStatusEnum(), e.getMessage());
    }

    /**
     * 处理 RuntimeException 异常
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException: {}", e.getMessage(), e);
        return ResultFactory.getFailureResult(StatusEnum.INVALID_OPERATION, "系统错误");
    }

    /**
     * 处理 Exception 异常（兜底）
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse<?> exceptionHandler(Exception e) {
        log.error("Exception: {}", e.getMessage(), e);
        return ResultFactory.getFailureResult(StatusEnum.INVALID_OPERATION, "系统内部异常");
    }
}
