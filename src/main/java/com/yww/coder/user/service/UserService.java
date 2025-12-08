package com.yww.coder.user.service;

import com.yww.coder.user.model.dto.UserLoginResponseDto;

import jakarta.servlet.http.HttpServletRequest;

/**
* @author yyw
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-12-08 02:06:57
*/
public interface UserService{


    UserLoginResponseDto userLogin(String userName, String password, HttpServletRequest request);
}
