package com.yww.coder.genapp.controller;

import com.yww.coder.annotation.AuthCheck;
import com.yww.coder.common.customException.ThrowUtils;
import com.yww.coder.common.dto.PageResponseDto;
import com.yww.coder.common.enums.base.InvalidContentSubStatusEnum;
import com.yww.coder.common.enums.base.SuccessSubStatusEnum;
import com.yww.coder.common.result.BaseResponse;
import com.yww.coder.common.result.ResultFactory;
import com.yww.coder.constant.UserConstant;
import com.yww.coder.genapp.model.dto.AppAdminUpdateRequestDto;
import com.yww.coder.genapp.model.dto.AppDetailResponseDto;
import com.yww.coder.genapp.model.dto.AppPageQueryRequestDto;
import com.yww.coder.genapp.model.dto.GenerateAppRequestDto;
import com.yww.coder.genapp.model.dto.UpdateAppRequestDto;
import com.yww.coder.genapp.service.AppService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Description;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 应用主表(App)表控制层
 *
 * @author yyw
 * @since 2026-01-22 14:26:07
 */
@RestController
@RequestMapping("/app")
@Description("应用接口")
public class AppController {
    
    @Resource
    private AppService appService;

    /**
     * 【用户】创建应用
     */
    @PostMapping("/create")
    @Description("【用户】创建应用")
    public BaseResponse<Long> createApp(@RequestBody GenerateAppRequestDto generateAppRequestDto, HttpServletRequest request) {
        if (ObjectUtils.isEmpty(generateAppRequestDto)) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "创建应用请求不能为空");
        }
        Long appId = appService.createApp(generateAppRequestDto, request);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, appId);
    }

    /**
     * 【用户】根据 id 修改自己的应用
     */
    @PostMapping("/update")
    @Description("【用户】根据 id 修改自己的应用")
    public BaseResponse<Boolean> updateMyApp(@RequestBody UpdateAppRequestDto updateAppRequestDto, HttpServletRequest request) {
        if (ObjectUtils.isEmpty(updateAppRequestDto) || updateAppRequestDto.getId() == null) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "应用 ID 不能为空");
        }
        Boolean result = appService.updateMyApp(updateAppRequestDto, request);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, result);
    }

    /**
     * 【用户】根据 id 删除自己的应用
     */
    @PostMapping("/delete")
    @Description("【用户】根据 id 删除自己的应用")
    public BaseResponse<Boolean> deleteMyApp(@RequestParam("id") Long id, HttpServletRequest request) {
        if (id == null || id <= 0) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "应用 ID 不合法");
        }
        Boolean result = appService.deleteMyApp(id, request);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, result);
    }

    /**
     * 【用户】根据 id 查看应用详情
     */
    @GetMapping("/detail")
    @Description("【用户】根据 id 查看应用详情")
    public BaseResponse<AppDetailResponseDto> getAppDetail(@RequestParam("id") Long id) {
        if (id == null || id <= 0) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "应用 ID 不合法");
        }
        AppDetailResponseDto detail = appService.getAppDetail(id);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, detail);
    }

    /**
     * 【用户】分页查询自己的应用列表
     */
    @PostMapping("/my/list/page")
    @Description("【用户】分页查询自己的应用列表")
    public BaseResponse<PageResponseDto<AppDetailResponseDto>> listMyAppsByPage(
            @RequestBody AppPageQueryRequestDto queryRequest, 
            HttpServletRequest request) {
        if (ObjectUtils.isEmpty(queryRequest)) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "查询条件不能为空");
        }
        PageResponseDto<AppDetailResponseDto> pageResponse = appService.listMyAppsByPage(queryRequest, request);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, pageResponse);
    }

    /**
     * 【用户】分页查询精选的应用列表
     */
    @PostMapping("/featured/list/page")
    @Description("【用户】分页查询精选的应用列表")
    public BaseResponse<PageResponseDto<AppDetailResponseDto>> listFeaturedAppsByPage(
            @RequestBody AppPageQueryRequestDto queryRequest) {
        if (ObjectUtils.isEmpty(queryRequest)) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "查询条件不能为空");
        }
        PageResponseDto<AppDetailResponseDto> pageResponse = appService.listFeaturedAppsByPage(queryRequest);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, pageResponse);
    }

    /**
     * 【管理员】根据 id 删除任意应用
     */
    @PostMapping("/admin/delete")
    @Description("【管理员】根据 id 删除任意应用")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> adminDeleteApp(@RequestParam("id") Long id) {
        if (id == null || id <= 0) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "应用 ID 不合法");
        }
        Boolean result = appService.deleteAppById(id);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, result);
    }

    /**
     * 【管理员】根据 id 更新任意应用
     */
    @PostMapping("/admin/update")
    @Description("【管理员】根据 id 更新任意应用")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> adminUpdateApp(@RequestBody AppAdminUpdateRequestDto updateRequestDto) {
        if (ObjectUtils.isEmpty(updateRequestDto) || updateRequestDto.getId() == null) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "应用 ID 不能为空");
        }
        Boolean result = appService.adminUpdateApp(updateRequestDto);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, result);
    }

    /**
     * 【管理员】分页查询应用列表
     */
    @PostMapping("/admin/list/page")
    @Description("【管理员】分页查询应用列表")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<PageResponseDto<AppDetailResponseDto>> adminListAppsByPage(
            @RequestBody AppPageQueryRequestDto queryRequest) {
        if (ObjectUtils.isEmpty(queryRequest)) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "查询条件不能为空");
        }
        PageResponseDto<AppDetailResponseDto> pageResponse = appService.adminListAppsByPage(queryRequest);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, pageResponse);
    }

    /**
     * 【管理员】根据 id 查看应用详情
     */
    @GetMapping("/admin/detail")
    @Description("【管理员】根据 id 查看应用详情")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<AppDetailResponseDto> adminGetAppDetail(@RequestParam("id") Long id) {
        if (id == null || id <= 0) {
            ThrowUtils.throwIf(true, InvalidContentSubStatusEnum.PARAMETER_VERIFICATION_FAILED, "应用 ID 不合法");
        }
        AppDetailResponseDto detail = appService.getAppDetail(id);
        return ResultFactory.getSuccessResult(SuccessSubStatusEnum.SUCCESS, detail);
    }
}

