package com.yww.coder.core.result;

import com.yww.coder.core.enums.base.InvalidContentSubStatusEnum;
import com.yww.coder.core.enums.base.StatusEnum;

/**
 * 无效内容异常
 */
public class InvalidContentException extends BaseException {

    /**
     * 子状态异常枚举
     */
    private final InvalidContentSubStatusEnum invalidContentSubStatusEnum;

    /**
     * 子状态异常的详细描述
     */
    protected final String details;

    public InvalidContentException(InvalidContentSubStatusEnum invalidContentSubStatusEnum) {
        super(invalidContentSubStatusEnum.getDescription());
        this.invalidContentSubStatusEnum = invalidContentSubStatusEnum;
        this.details = "";
    }

    public InvalidContentException(InvalidContentSubStatusEnum invalidContentSubStatusEnum, Throwable cause) {
        super(invalidContentSubStatusEnum.getDescription(), cause);
        this.invalidContentSubStatusEnum = invalidContentSubStatusEnum;
        this.details = "";
    }

    public InvalidContentException(InvalidContentSubStatusEnum invalidContentSubStatusEnum, String details) {
        super(invalidContentSubStatusEnum.getDescription());
        this.invalidContentSubStatusEnum = invalidContentSubStatusEnum;
        this.details = details;
    }

    public InvalidContentException(InvalidContentSubStatusEnum invalidContentSubStatusEnum, Throwable cause,
            String details) {
        super(invalidContentSubStatusEnum.getDescription(), cause);
        this.invalidContentSubStatusEnum = invalidContentSubStatusEnum;
        this.details = details;
    }

    /**
     * 子状态获取异常枚举
     *
     * @return 子状态获取异常枚举
     */
    @Override
    public InvalidContentSubStatusEnum getResultSubStatusEnum() {
        return invalidContentSubStatusEnum;
    }

    /**
     * 获取子状态异常的详细描述
     *
     * @return 获取子状态异常的详细描述
     */
    @Override
    public String getDetails() {
        return details;
    }

    /**
     * 获取主状态枚举
     *
     * @return 主状态枚举
     */
    @Override
    public StatusEnum getStatusEnum() {
        return StatusEnum.INVALID_CONTENT;
    }
}
