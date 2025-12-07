package com.yww.coder.core.result;

import com.yww.coder.core.enums.base.StatusEnum;

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
     * 获取状态枚举
     * 
     * @return 状态枚举
     */
    public abstract StatusEnum getStatusEnum();
}
