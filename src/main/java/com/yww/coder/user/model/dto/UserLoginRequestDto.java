package com.yww.coder.user.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserLoginRequestDto implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * id
     */
    private String id;
    /**
     * 用户名
     */
    private String userAccount;
    /**
     * 密码
     */
    private String password;

}
