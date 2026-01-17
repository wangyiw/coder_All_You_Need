package com.yww.coder.common.enums.base;

/**
 * 子状态 - 无效的内容, 传入参数等有异常
 */
public enum InvalidContentSubStatusEnum implements ResultSubStatus {

    PARAMS_ERROR(40000, "请求参数错误"),

    NOT_LOGIN_ERROR(40100, "未登录"),

    NO_AUTH_ERROR(40101, "无权限"),

    NOT_FOUND_ERROR(40400, "请求数据不存在"),

    FORBIDDEN_ERROR(40300, "禁止访问"),

    SYSTEM_ERROR(50000, "系统内部异常"),

    OPERATION_ERROR(50001, "操作失败"),

    OTHER(-1, "未知的异常"),

    AUTHENTICATION_FAILED(1, "认证失败"),

    DATA_NOT_EXIST(2, "数据不存在"),

    DATA_ALREADY_EXISTS(3, "数据已存在"),

    PARAMETER_VERIFICATION_FAILED(4, "参数校验异常"),

    PERMISSION_VERIFICATION_FAILED(5, "权限校验失败"),

    SECONDARY_VERIFICATION_FAILED(6, "二次验证失败"),

    USERNAME_PASSWORD_FAILED(7, "用户名密码错误"),

    PASSWORD_NOT_RESET(8, "密码未重置"),

    PASSWORD_NOT_UPDATED_REGULARLY(9, "密码定时未更新更新"),

    USER_DISABLED(10, "用户已禁用"),

    USER_FREEZE(11, "用户已冻结"),
    OSS_FAIL(12, "文件上传/下载oss失败"),
    DATA_STATICS_FAILED(13, "文件解析失败"),
    JSON_TRANSFORMATION_FAIL(14, "json字符串解析异常"),
    DB_PERSISTENCE_FAILED(15, "数据库持久化失败"),
    EXCEL_EXPORT_FAILED(16, "excel下载失败"), 
    INVALID_CONTENT(17, "无效的内容");

    private final Integer subStatus;

    private final String description;

    InvalidContentSubStatusEnum(Integer subStatus, String display) {
        this.subStatus = subStatus;
        this.description = display;
    }

    /**
     * 根据子状态码获取子状态枚举
     *
     * @param subStatus 子状态码
     * @return 无效的内容子状态枚举
     */
    public static InvalidContentSubStatusEnum getInvalidContentSubStatusEnumBySubStatus(Integer subStatus) {

        for (InvalidContentSubStatusEnum invalidContentSubStatusEnum : values()) {
            if (invalidContentSubStatusEnum.getSubStatus().equals(subStatus)) {
                return invalidContentSubStatusEnum;
            }
        }

        return OTHER;
    }

    @Override
    public Integer getSubStatus() {
        return subStatus;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
