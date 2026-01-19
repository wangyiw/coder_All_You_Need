package com.yww.coder.common.result;

import com.yww.coder.common.enums.base.InvalidContentSubStatusEnum;

/**
 * 无效内容异常
 */
public class InvalidContentException extends BaseException {

    private InvalidContentSubStatusEnum subStatusEnum;

    public InvalidContentException(String message) {
        super(message);
        this.subStatusEnum = InvalidContentSubStatusEnum.PARAMS_ERROR;
    }

    public InvalidContentException(InvalidContentSubStatusEnum subStatusEnum) {
        super(subStatusEnum.getDescription());
        this.subStatusEnum = subStatusEnum;
    }

    public InvalidContentException(InvalidContentSubStatusEnum subStatusEnum, String message) {
        super(message);
        this.subStatusEnum = subStatusEnum;
    }

    public InvalidContentException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 获取子状态码
     *
     * @return 子状态码
     */
    @Override
    public Integer getSubStatusCode() {
        return subStatusEnum != null ? subStatusEnum.getSubStatus() : InvalidContentSubStatusEnum.PARAMS_ERROR.getSubStatus();
    }

    /**
     * 获取错误描述
     *
     * @return 错误描述
     */
    @Override
    public String getErrorMessage() {
        return subStatusEnum != null ? subStatusEnum.getDescription() : InvalidContentSubStatusEnum.PARAMS_ERROR.getDescription();
    }

    /**
     * 获取子状态枚举
     *
     * @return 子状态枚举
     */
    public InvalidContentSubStatusEnum getSubStatusEnum() {
        return subStatusEnum;
    }
}
