package com.yww.coder.user.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserRegisterRequestDto implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;
    /**
     * 昵称
     */
    private String userName;
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 密码
     */
    private String password;
    /**
     * 确认密码
     */
    private String confirmPassword;
    /**
     * 头像
     */
    private String userAvatar;
    /**
     * 用户简介
     */
    private String userProfile;
    /**
     * 用户角色
     */
    private String userRole;
    
}
