package com.yww.coder.common.customException;

import com.yww.coder.common.enums.base.InvalidContentSubStatusEnum;
import com.yww.coder.common.result.BaseException;
import com.yww.coder.common.result.InvalidContentException;
import com.yww.coder.common.result.InvalidOperationException;

/**
 * 异常抛出工具类
 * 支持 BaseException 异常体系
 */
public class ThrowUtils {

    /**
     * 条件成立则抛出 InvalidOperationException
     *
     * @param condition 条件
     * @param message   错误消息
     */
    public static void throwIf(boolean condition, String message) {
        if (condition) {
            throw new InvalidOperationException(message);
        }
    }

    /**
     * 条件成立则抛出自定义 BaseException
     *
     * @param condition     条件
     * @param baseException 自定义异常
     */
    public static void throwIf(boolean condition, BaseException baseException) {
        if (condition) {
            throw baseException;
        }
    }

    /**
     * 条件成立则抛出指定子状态的异常
     *
     * @param condition     条件
     * @param subStatusEnum 子状态枚举
     */
    public static void throwIf(boolean condition, InvalidContentSubStatusEnum subStatusEnum) {
        if (condition) {
            throw new InvalidContentException(subStatusEnum);
        }
    }

    /**
     * 条件成立则抛出 RuntimeException
     *
     * @param condition        条件
     * @param runtimeException 运行时异常
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛出指定子状态的异常（自定义消息）
     *
     * @param condition     条件
     * @param subStatusEnum 子状态枚举
     * @param message       错误消息
     */
    public static void throwIf(boolean condition, InvalidContentSubStatusEnum subStatusEnum, String message) {
        if (condition) {
            throw new InvalidContentException(subStatusEnum, message);
        }
    }
}
