package com.yww.coder.user.controller;

import com.yww.coder.user.model.dto.UserRegisterRequestDto;
import org.springframework.context.annotation.Description;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yww.coder.core.customException.ThrowUtils;
import com.yww.coder.core.enums.base.InvalidContentSubStatusEnum;
import com.yww.coder.core.enums.base.SuccessSubStatusEnum;
import com.yww.coder.core.result.BaseResponse;
import com.yww.coder.core.result.ResultFactory;
import com.yww.coder.user.model.dto.UserLoginRequestDto;
import com.yww.coder.user.model.dto.UserLoginResponseDto;
import com.yww.coder.user.service.UserService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @PostMapping("/login")
    @Description("用户登录")
    public BaseResponse<?> userLogin(@RequestBody UserLoginRequestDto loginDto, HttpServletRequest request) {
        if(ObjectUtils.isEmpty(request)) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "用户名或密码不能为空");
        }
        UserLoginResponseDto response = userService.userLogin(loginDto.getUserName(), loginDto.getPassword(), request);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, response);
    }
    @PostMapping("/logout")
    @Description("用户登出")
    public BaseResponse<?> userLogout(HttpServletRequest request) {
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS);
    }

    @PostMapping("/register")
    @Description("用户注册")
    public BaseResponse<?> userRegister(@RequestBody UserRegisterRequestDto registerDto) {
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS);
    }

    @PostMapping("/getUserInfo")
    @Description("获取用户信息")
    public BaseResponse<?> getUserInfo(HttpServletRequest request) {
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS);
    }

    @PostMapping("/deleteUser")
    @Description("用户注销")
    public BaseResponse<?> deleteUser(HttpServletRequest request) {
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS);
    }


}
