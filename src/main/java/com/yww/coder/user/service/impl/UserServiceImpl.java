package com.yww.coder.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import com.yww.coder.user.mapper.UserMapper;
import com.yww.coder.user.model.entity.User;
import com.yww.coder.user.service.UserService;

/**
* @author yyw
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-12-08 02:06:57
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
{

}
