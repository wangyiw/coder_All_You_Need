package com.yww.coder.common.enums.base;

import java.util.Objects;

/**
 * 子状态 - 无效操作, 业务上存在异常
 */
public enum InvalidOperationSubStatusEnum implements ResultSubStatus {

    OTHER(-1, "未知的异常"),

    PERMISSION_VERIFICATION_FAILED(1, "权限校验未通过"),

    DATA_UPDATE_FAIL(2, "数据修改失败"),

    DATA_ADD_FAIL(3, "数据添加失败"),

    DATA_REMOVE_FAIL(4, "数据删除失败"),

    SERVER_QUERY_FAIL(5, "数据查询失败"),

    ENCODE_FAILURE(6, "加密失败"),

    DECODE_FAILURE(7, "解密失败"),

    PASSWORD_FAIL(8, "密码错误"),

    DATA_STATUS_IS_ERROR(9, "数据状态错误"),

    METHOD_NEED_PARAM_IS_NULL(10, "方法所需参数为空"),

    ENUM_NO_ESIST(11, "枚举不存在"),

    FILE_UPLOAD_ERR(12, "文件上传失败"),

    IS_BIND_WECHAT(13, "当前用户已经绑定了小程序"),

    QRCODE_INVALID(14, "二维码失效"),

    OPERATION_FAILED(15, "操作失败"),

    SYSTEM_ERROR(16, "系统错误"),

    ;

    private final Integer subStatus;

    private final String description;

    InvalidOperationSubStatusEnum(Integer subStatus, String description) {
        this.subStatus = subStatus;
        this.description = description;
    }

    /**
     * 根据子状态码获取无效的操作子状态枚举
     *
     * @param subStatus 子状态码
     * @return 无效的操作子状态枚举
     */
    public static InvalidOperationSubStatusEnum getInvalidOperationSubStatusEnumBySubStatus(Integer subStatus) {
        for (InvalidOperationSubStatusEnum invalidOperationSubStatusEnum : values()) {
            if (Objects.equals(invalidOperationSubStatusEnum.getSubStatus(), subStatus)) {
                return invalidOperationSubStatusEnum;
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
