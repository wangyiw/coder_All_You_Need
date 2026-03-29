package com.yww.coder.core.data;

/**
 * 数据为空异常
 */
public class NullDataException extends Exception {

    public NullDataException(String message) {
        super(message);
    }

    public NullDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullDataException(Throwable cause) {
        super(cause);
    }

    protected NullDataException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
