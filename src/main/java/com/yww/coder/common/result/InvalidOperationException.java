package com.yww.coder.common.result;

import com.yww.coder.common.enums.base.InvalidOperationSubStatusEnum;

/**
 * 无效操作异常
 */
public class InvalidOperationException extends BaseException {

    private InvalidOperationSubStatusEnum subStatusEnum;

    public InvalidOperationException(String message) {
        super(message);
        this.subStatusEnum = InvalidOperationSubStatusEnum.OPERATION_FAILED;
    }

    public InvalidOperationException(InvalidOperationSubStatusEnum subStatusEnum) {
        super(subStatusEnum.getDescription());
        this.subStatusEnum = subStatusEnum;
    }

    public InvalidOperationException(InvalidOperationSubStatusEnum subStatusEnum, String message) {
        super(message);
        this.subStatusEnum = subStatusEnum;
    }

    public InvalidOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 获取子状态码
     *
     * @return 子状态码
     */
    @Override
    public Integer getSubStatusCode() {
        return subStatusEnum != null ? subStatusEnum.getSubStatus() : InvalidOperationSubStatusEnum.OPERATION_FAILED.getSubStatus();
    }

    /**
     * 获取错误描述
     *
     * @return 错误描述
     */
    @Override
    public String getErrorMessage() {
        return subStatusEnum != null ? subStatusEnum.getDescription() : InvalidOperationSubStatusEnum.OPERATION_FAILED.getDescription();
    }

    /**
     * 获取子状态枚举
     *
     * @return 子状态枚举
     */
    public InvalidOperationSubStatusEnum getSubStatusEnum() {
        return subStatusEnum;
    }
}
