package com.yww.coder.user.model.dto;

import lombok.Data;

@Data
public class UserLoginRequestDto {
    /**
     * 登录请求入参
     */
    private String id;
    private String userName;
    private String password;
}
