package com.yww.coder.user.service.impl;

import org.springframework.stereotype.Service;

import com.yww.coder.user.mapper.UserMapper;
import com.yww.coder.user.service.IUserService;

import jakarta.annotation.Resource;

/**
* @author yyw
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-12-08 02:06:57
*/
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

}
