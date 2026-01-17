package com.yww.coder.common.enums.base;

/**
 * 成功子状态枚举
 */
public enum SuccessSubStatusEnum implements ResultSubStatus {

    OTHER(-1, "未知的子状态"),

    SUCCESS(0, "成功"),
    ;

    /**
     * 子状态
     */
    private final Integer subStatus;

    /**
     * 描述
     */
    private final String description;

    SuccessSubStatusEnum(Integer subStatus, String display) {
        this.subStatus = subStatus;
        this.description = display;
    }

    @Override
    public Integer getSubStatus() {
        return subStatus;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static SuccessSubStatusEnum getSuccessSubStatusEnumBySubStatus(Integer subStatus) {
        for (SuccessSubStatusEnum successSubStatusEnum : values()) {
            if (successSubStatusEnum.getSubStatus().equals(subStatus)) {
                return successSubStatusEnum;
            }
        }
        return OTHER;
    }

}
