package com.yww.coder.genapp.service;

import com.yww.coder.common.dto.PageResponseDto;
import com.yww.coder.genapp.model.dto.AppAdminUpdateRequestDto;
import com.yww.coder.genapp.model.dto.AppDetailResponseDto;
import com.yww.coder.genapp.model.dto.AppPageQueryRequestDto;
import com.yww.coder.genapp.model.dto.GenerateAppRequestDto;
import com.yww.coder.genapp.model.dto.UpdateAppRequestDto;
import com.yww.coder.genapp.model.entity.App;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 应用主表(App)表服务接口
 *
 * @author yyw
 * @since 2026-01-22 14:26:11
 */
public interface AppService {

    /**
     * 【用户】创建应用
     * @param generateAppRequestDto 创建应用请求
     * @param request HTTP请求
     * @return 应用ID
     */
    Long createApp(GenerateAppRequestDto generateAppRequestDto, HttpServletRequest request);

    /**
     * 【用户】根据 id 修改自己的应用
     * @param updateAppRequestDto 更新应用请求
     * @param request HTTP请求
     * @return 是否成功
     */
    Boolean updateMyApp(UpdateAppRequestDto updateAppRequestDto, HttpServletRequest request);

    /**
     * 【用户】根据 id 删除自己的应用
     * @param id 应用ID
     * @param request HTTP请求
     * @return 是否成功
     */
    Boolean deleteMyApp(Long id, HttpServletRequest request);

    /**
     * 【用户】根据 id 查看应用详情
     * @param id 应用ID
     * @return 应用详情
     */
    AppDetailResponseDto getAppDetail(Long id);

    /**
     * 【用户】分页查询自己的应用列表
     * @param queryRequest 查询请求
     * @param request HTTP请求
     * @return 分页结果
     */
    PageResponseDto<AppDetailResponseDto> listMyAppsByPage(AppPageQueryRequestDto queryRequest, HttpServletRequest request);

    /**
     * 【用户】分页查询精选的应用列表
     * @param queryRequest 查询请求
     * @return 分页结果
     */
    PageResponseDto<AppDetailResponseDto> listFeaturedAppsByPage(AppPageQueryRequestDto queryRequest);

    /**
     * 【管理员】根据 id 删除任意应用
     * @param id 应用ID
     * @return 是否成功
     */
    Boolean deleteAppById(Long id);

    /**
     * 【管理员】根据 id 更新任意应用
     * @param updateRequestDto 更新请求
     * @return 是否成功
     */
    Boolean adminUpdateApp(AppAdminUpdateRequestDto updateRequestDto);

    /**
     * 【管理员】分页查询应用列表
     * @param queryRequest 查询请求
     * @return 分页结果
     */
    PageResponseDto<AppDetailResponseDto> adminListAppsByPage(AppPageQueryRequestDto queryRequest);

    /**
     * 根据 id 获取应用实体
     * @param id 应用ID
     * @return 应用实体
     */
    App getAppById(Long id);

    /**
     * 将应用实体转换为详情DTO
     * @param app 应用实体
     * @return 应用详情DTO
     */
    AppDetailResponseDto getAppDetailVO(App app);

    /**
     * 将应用实体列表转换为详情DTO列表
     * @param appList 应用实体列表
     * @return 应用详情DTO列表
     */
    List<AppDetailResponseDto> getAppDetailVOList(List<App> appList);
}
