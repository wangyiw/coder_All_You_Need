package com.yww.coder.common.enums.base;

/**
 * 子状态 - 无效的内容, 传入参数等有异常
 */
public enum InvalidContentSubStatusEnum implements ResultSubStatus {

    // 客户端错误 4xxxx
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),

    // 服务端错误 5xxxx
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败"),

    // 业务错误 - 认证相关 401xx
    AUTHENTICATION_FAILED(40110, "认证失败"),
    USERNAME_PASSWORD_FAILED(40111, "用户名密码错误"),
    PERMISSION_VERIFICATION_FAILED(40112, "权限校验失败"),
    SECONDARY_VERIFICATION_FAILED(40113, "二次验证失败"),

    // 业务错误 - 参数相关 400xx
    PARAMETER_VERIFICATION_FAILED(40001, "参数校验异常"),
    INVALID_CONTENT(40002, "无效的内容"),

    // 业务错误 - 数据相关 404xx
    DATA_NOT_EXIST(40401, "数据不存在"),
    DATA_ALREADY_EXISTS(40402, "数据已存在"),

    // 业务错误 - 用户状态相关 403xx
    USER_DISABLED(40301, "用户已禁用"),
    USER_FREEZE(40302, "用户已冻结"),
    PASSWORD_NOT_RESET(40303, "密码未重置"),
    PASSWORD_NOT_UPDATED_REGULARLY(40304, "密码定时未更新"),

    // 业务错误 - 文件相关 500xx
    OSS_FAIL(50010, "文件上传/下载失败"),
    EXCEL_EXPORT_FAILED(50011, "Excel导出失败"),
    DATA_STATICS_FAILED(50012, "文件解析失败"),

    // 业务错误 - 数据处理相关 501xx
    JSON_TRANSFORMATION_FAIL(50110, "JSON解析异常"),
    DB_PERSISTENCE_FAILED(50111, "数据库持久化失败"),

    OTHER(-1, "未知异常");

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
