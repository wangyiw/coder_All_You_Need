package com.yww.coder.common.result;

/**
 * 基础自定义异常
 */
public abstract class BaseException extends RuntimeException {

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * 获取子状态码
     * 
     * @return 子状态码
     */
    public abstract Integer getSubStatusCode();

    /**
     * 获取错误描述
     * 
     * @return 错误描述
     */
    public abstract String getErrorMessage();
}
