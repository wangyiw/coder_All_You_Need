package com.yww.coder.core.result;

import com.yww.coder.core.enums.base.StatusEnum;

/**
 * 无效操作异常
 */
public class InvalidOperationException extends BaseException {

    public InvalidOperationException(String message) {
        super(message);
    }

    public InvalidOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 获取状态枚举
     *
     * @return 状态枚举
     */
    @Override
    public StatusEnum getStatusEnum() {
        return StatusEnum.INVALID_OPERATION;
    }
}
