package com.yww.coder.core.enums.base;

/**
 * 结果返回 主状态
 */
public enum StatusEnum {

    OTHER(-1, "未知的主状态"),

    /**
     * 执行成功
     */
    SUCCESS(0, "执行成功"),

    /**
     * 无效的内容
     */
    INVALID_CONTENT(1, "无效的内容"),

    /**
     * 无效的操作
     */
    INVALID_OPERATION(2, "无效的操作"),
    ;

    /**
     * 状态
     */
    private final Integer status;

    /**
     * 描述
     */
    private final String description;

    StatusEnum(Integer status, String display) {
        this.status = status;
        this.description = display;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public static StatusEnum getStatusEnumByStatus(Integer status) {
        for (StatusEnum statusEnum : values()) {
            if (statusEnum.getStatus().equals(status)) {
                return statusEnum;
            }
        }
        return OTHER;
    }
}
