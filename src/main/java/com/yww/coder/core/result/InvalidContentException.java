package com.yww.coder.core.result;

import com.yww.coder.core.enums.base.StatusEnum;

/**
 * 无效内容异常
 */
public class InvalidContentException extends BaseException {

    public InvalidContentException(String message) {
        super(message);
    }

    public InvalidContentException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 获取状态枚举
     *
     * @return 状态枚举
     */
    @Override
    public StatusEnum getStatusEnum() {
        return StatusEnum.INVALID_CONTENT;
    }
}
