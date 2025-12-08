package com.yww.coder.user.service.impl;

import org.springframework.stereotype.Service;

import com.yww.coder.user.mapper.UserMapper;
import com.yww.coder.user.model.dto.UserLoginResponseDto;
import com.yww.coder.user.service.UserService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author yyw
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-12-08 02:06:57
*/
@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;


    /**
     * 用户登录方法
     * @param userName
     * @param password
     * @param request
     * @return
     */
    @Override
    public UserLoginResponseDto userLogin(String userName, String password, HttpServletRequest request) {
        return null;
    }
}
