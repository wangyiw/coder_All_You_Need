package com.yww.coder.user.controller;

import com.yww.coder.user.model.dto.UserRegisterRequestDto;
import com.yww.coder.user.model.dto.UserUpdateRequest;
import com.yww.coder.user.model.entity.User;

import java.util.List;

import org.springframework.context.annotation.Description;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yww.coder.annotation.AuthCheck;
import com.yww.coder.common.customException.ThrowUtils;
import com.yww.coder.common.dto.PageResponseDto;
import com.yww.coder.common.enums.base.InvalidContentSubStatusEnum;
import com.yww.coder.common.enums.base.SuccessSubStatusEnum;
import com.yww.coder.common.result.BaseResponse;
import com.yww.coder.common.result.ResultFactory;
import com.yww.coder.constant.UserConstant;
import com.yww.coder.user.model.dto.DeleteRequest;
import com.yww.coder.user.model.dto.UserAddRequest;
import com.yww.coder.user.model.dto.UserLoginRequestDto;
import com.yww.coder.user.model.dto.UserLoginResponseDto;
import com.yww.coder.user.model.dto.UserQueryRequest;
import com.yww.coder.user.service.UserService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@Description("用户接口")
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    @Description("用户登录")
    public BaseResponse<UserLoginResponseDto> userLogin(@RequestBody UserLoginRequestDto loginDto, HttpServletRequest request) {
        if(ObjectUtils.isEmpty(request)) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "用户名或密码不能为空");
        }
        if(ObjectUtils.isEmpty(loginDto.getId()) || ObjectUtils.isEmpty(loginDto.getPassword())||ObjectUtils.isEmpty(loginDto.getUserAccount())){
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "用户名或密码不能为空");
        }
        UserLoginResponseDto response = userService.userLogin(loginDto.getUserAccount(), loginDto.getPassword(), request);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, response);
    }

    @PostMapping("/logout")
    @Description("用户登出")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if(ObjectUtils.isEmpty(request)) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "请求不能为空");
        }
        boolean result = userService.userLogout(request);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, result);
    }

    @PostMapping("/register")
    @Description("用户注册")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequestDto registerDto) {
        if(ObjectUtils.isEmpty(registerDto)) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "注册信息不能为空");
        }
        long registerId = userService.userRegister(registerDto);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, registerId);
    }

    @PostMapping("/getUserInfo")
    @Description("获取用户信息")
    public BaseResponse<User> getUserInfo(HttpServletRequest request) {
        User loginUser = userService.getUserInfo(request);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS,loginUser);
    }

    @PostMapping("/deleteUser")
    @Description("用户注销")
    public BaseResponse<?> deleteUser(HttpServletRequest request) {
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS);
    }

    @PostMapping("/updateUser")
    @Description("更新用户信息")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<?> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS);
    }

    @PostMapping("/queryUser")
    @Description("查询用户信息")
    public BaseResponse<List<UserLoginResponseDto>> queryUser(@RequestBody UserQueryRequest userQueryRequest) {
        List<User> userList = userService.queryUser(userQueryRequest);
        List<UserLoginResponseDto> safeList = userService.getLoginUserVOList(userList);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, safeList);
    }

    /**
     * 添加用户
     * @param request
     */
    @PostMapping("/add")
    @Description("【管理员】添加用户")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<User> addUser(@RequestBody UserAddRequest request){
        if (ObjectUtils.isEmpty(request)) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "请求不能为空");
        }
        long userId = userService.addUser(request);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, userService.getUserById(userId));
    }

    /**
     * 根据 id 获取用户（仅管理员）
     */
    @GetMapping("/get")
    @Description("根据 id 获取用户（仅管理员）")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<User> getUserById(long id) {
        if (id <= 0) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "用户 id 不合法");
        }
        User user = userService.getUserById(id);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, user);
    }

    /**
     * 删除用户
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    @Description("删除用户")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() == null || deleteRequest.getId() <= 0) {
            throw new com.yww.coder.common.result.InvalidContentException("用户 id 不合法");
        }
        boolean b = userService.deleteUser(deleteRequest.getId());
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, b);
    }

    /**
     * 分页获取用户封装列表（仅管理员）
     *
     * @param userQueryRequest 查询请求参数
     */
    @PostMapping("/list/page/vo")
    @Description("分页获取用户封装列表（仅管理员）")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<PageResponseDto<UserLoginResponseDto>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "查询条件不能为空");
        }
        PageResponseDto<UserLoginResponseDto> pageResponse = userService.userQueryPage(userQueryRequest);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, pageResponse);
    }


}


