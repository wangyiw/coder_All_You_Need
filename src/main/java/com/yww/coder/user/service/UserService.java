package com.yww.coder.user.service;

import java.util.List;

import com.yww.coder.user.model.dto.UserAddRequest;
import com.yww.coder.user.model.dto.UserLoginResponseDto;
import com.yww.coder.user.model.dto.UserQueryRequest;
import com.yww.coder.user.model.dto.UserRegisterRequestDto;
import com.yww.coder.user.model.dto.UserUpdateRequest;
import com.yww.coder.user.model.entity.User;

import jakarta.servlet.http.HttpServletRequest;

/**
* @author yyw
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-12-08 02:06:57
*/
public interface UserService{


    UserLoginResponseDto userLogin(String userAccount, String password, HttpServletRequest request);


    String getEncryptPassword(String userPassword);

    UserLoginResponseDto getLoginUserVO(User user);


    long userRegister(UserRegisterRequestDto registerDto);


    User getUserInfo(HttpServletRequest request);

    boolean userLogout(HttpServletRequest request);


    List<User> queryUser(UserQueryRequest userQueryRequest);


    void updateUser(UserUpdateRequest userUpdateRequest);


     long addUser(UserAddRequest userAddRequest);


     User getUserById(Long id);


     boolean deleteUser(Long id);


     List<UserLoginResponseDto> getLoginUserVOList(List<User> userList);


}
