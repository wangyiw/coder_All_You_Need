package com.yww.coder.core.customException;

import com.yww.coder.core.enums.base.InvalidOperationSubStatusEnum;
import com.yww.coder.core.enums.base.ResultSubStatus;
import com.yww.coder.core.enums.base.StatusEnum;
import com.yww.coder.core.result.BaseException;
import com.yww.coder.core.result.InvalidContentException;
import com.yww.coder.core.result.InvalidOperationException;

/**
 * 异常抛出工具类
 * 支持 BaseException 异常体系
 */
public class ThrowUtils {

    // ==================== BaseException 体系方法 ====================

    /**
     * 条件成立则抛出 InvalidOperationException
     *
     * @param condition 条件
     * @param subStatus 子状态枚举
     */
    public static void throwIf(boolean condition, InvalidOperationSubStatusEnum subStatus) {
        if (condition) {
            throw new InvalidOperationException(subStatus);
        }
    }

    /**
     * 条件成立则抛出 InvalidOperationException（带详细描述）
     *
     * @param condition 条件
     * @param subStatus 子状态枚举
     * @param details   详细描述
     */
    public static void throwIf(boolean condition, InvalidOperationSubStatusEnum subStatus, String details) {
        if (condition) {
            throw new InvalidOperationException(subStatus, details);
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
     * 条件成立则抛出指定状态的异常
     *
     * @param condition  条件
     * @param statusEnum 主状态枚举
     * @param subStatus  子状态枚举
     * @param details    详细描述
     */
    public static void throwIf(boolean condition, StatusEnum statusEnum, ResultSubStatus subStatus, String details) {
        if (condition) {
            switch (statusEnum) {
                case INVALID_CONTENT:
                    throw new InvalidContentException(
                            (com.yww.coder.core.enums.base.InvalidContentSubStatusEnum) subStatus,
                            details);
                case INVALID_OPERATION:
                    throw new InvalidOperationException(
                            (InvalidOperationSubStatusEnum) subStatus,
                            details);
                default:
                    throw new InvalidOperationException(
                            InvalidOperationSubStatusEnum.OTHER,
                            details);
            }
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
}
