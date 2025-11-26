package com.yww.coder.core.result;

import com.yww.coder.core.enums.base.InvalidOperationSubStatusEnum;
import com.yww.coder.core.enums.base.StatusEnum;

/**
 * 无效操作异常
 */
public class InvalidOperationException extends BaseException {
    /**
     * 子状态异常枚举
     */
    private final InvalidOperationSubStatusEnum invalidOperationSubStatusEnum;

    /**
     * 子状态异常的详细描述
     */
    protected final String details;

    public InvalidOperationException(InvalidOperationSubStatusEnum invalidOperationSubStatusEnum) {
        super(invalidOperationSubStatusEnum.getDescription());
        this.invalidOperationSubStatusEnum = invalidOperationSubStatusEnum;
        this.details = "";
    }

    public InvalidOperationException(InvalidOperationSubStatusEnum invalidOperationSubStatusEnum, Throwable cause) {
        super(invalidOperationSubStatusEnum.getDescription(), cause);
        this.invalidOperationSubStatusEnum = invalidOperationSubStatusEnum;
        this.details = "";
    }

    public InvalidOperationException(InvalidOperationSubStatusEnum invalidOperationSubStatusEnum, String details) {
        super(invalidOperationSubStatusEnum.getDescription());
        this.invalidOperationSubStatusEnum = invalidOperationSubStatusEnum;
        this.details = details;
    }

    public InvalidOperationException(InvalidOperationSubStatusEnum invalidOperationSubStatusEnum, Throwable cause,
            String details) {
        super(invalidOperationSubStatusEnum.getDescription(), cause);
        this.invalidOperationSubStatusEnum = invalidOperationSubStatusEnum;
        this.details = details;
    }

    /**
     * 子状态获取异常枚举
     *
     * @return 子状态获取异常枚举
     */
    @Override
    public InvalidOperationSubStatusEnum getResultSubStatusEnum() {

        return invalidOperationSubStatusEnum;
    }

    /**
     * 获取子状态异常的详细描述
     *
     * @return 获取子状态异常的详细描述
     */
    @Override
    public String getDetails() {
        return this.details;
    }

    /**
     * 获取主状态枚举
     *
     * @return 主状态枚举
     */
    @Override
    public StatusEnum getStatusEnum() {
        return StatusEnum.INVALID_OPERATION;
    }

}
