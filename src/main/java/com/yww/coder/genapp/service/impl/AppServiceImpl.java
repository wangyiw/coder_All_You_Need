package com.yww.coder.genapp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.yww.coder.common.dto.PageResponseDto;
import com.yww.coder.common.result.InvalidContentException;
import com.yww.coder.genapp.mapper.AppMapper;
import com.yww.coder.genapp.mapper.AppVersionMapper;
import com.yww.coder.genapp.model.dto.AppAdminUpdateRequestDto;
import com.yww.coder.genapp.model.dto.AppDetailResponseDto;
import com.yww.coder.genapp.model.dto.AppPageQueryRequestDto;
import com.yww.coder.genapp.model.dto.GenerateAppRequestDto;
import com.yww.coder.genapp.model.dto.UpdateAppRequestDto;
import com.yww.coder.genapp.model.entity.App;
import com.yww.coder.genapp.model.entity.AppVersion;
import com.yww.coder.genapp.service.AppService;
import com.yww.coder.user.model.dto.UserLoginResponseDto;
import com.yww.coder.user.model.entity.User;
import com.yww.coder.user.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 应用主表(App)表服务实现类
 *
 * @author yyw
 * @since 2026-01-22 14:26:12
 */
@Service("appService")
public class AppServiceImpl implements AppService {

    @Resource
    private AppMapper appMapper;

    @Resource
    private AppVersionMapper appVersionMapper;

    @Resource
    private UserService userService;

    @Override
    public Long createApp(GenerateAppRequestDto generateAppRequestDto, HttpServletRequest request) {
        if (generateAppRequestDto == null) {
            throw new InvalidContentException("创建应用请求不能为空");
        }
        if (StrUtil.isBlank(generateAppRequestDto.getInitPrompt())) {
            throw new InvalidContentException("应用初始化提示词不能为空");
        }

        // 获取当前登录用户
        User loginUser = userService.getUserInfo(request);
        if (loginUser == null) {
            throw new InvalidContentException("用户未登录");
        }

        // 创建应用
        App app = new App();
        app.setUserId(loginUser.getId());
        app.setDeployKey(generateUniqueDeployKey());
        app.setStatus(0); // 待发布
        app.setPriority(0);
        app.setIsDelete(0);

        int insertResult = appMapper.insert(app);
        if (insertResult <= 0) {
            throw new InvalidContentException("创建应用失败");
        }

        // 创建初始版本
        AppVersion appVersion = new AppVersion();
        appVersion.setAppId(app.getId());
        appVersion.setVersion(1);
        appVersion.setInitPrompt(generateAppRequestDto.getInitPrompt());
        appVersion.setCodeGenType("HTML"); // 默认类型
        appVersion.setStoragePath(""); // 待生成
        appVersion.setStatus(0); // 生成中
        appVersion.setUserId(loginUser.getId());

        int versionInsertResult = appVersionMapper.insert(appVersion);
        if (versionInsertResult <= 0) {
            throw new InvalidContentException("创建应用版本失败");
        }

        // 更新应用的最新版本ID
        App updateApp = new App();
        updateApp.setId(app.getId());
        updateApp.setLatestVersionId(appVersion.getId());
        appMapper.update(updateApp);

        return app.getId();
    }

    @Override
    public Boolean updateMyApp(UpdateAppRequestDto updateAppRequestDto, HttpServletRequest request) {
        if (updateAppRequestDto == null || updateAppRequestDto.getId() == null) {
            throw new InvalidContentException("应用 ID 不能为空");
        }

        // 获取当前登录用户
        User loginUser = userService.getUserInfo(request);
        if (loginUser == null) {
            throw new InvalidContentException("用户未登录");
        }

        // 校验应用是否存在且属于当前用户
        App existApp = getAppById(updateAppRequestDto.getId());
        if (!existApp.getUserId().equals(loginUser.getId())) {
            throw new InvalidContentException("无权限修改该应用");
        }

        // 只允许修改应用名称
        if (StrUtil.isBlank(updateAppRequestDto.getAppName())) {
            throw new InvalidContentException("应用名称不能为空");
        }

        App updateApp = new App();
        updateApp.setId(updateAppRequestDto.getId());
        updateApp.setAppName(updateAppRequestDto.getAppName());

        int updateResult = appMapper.update(updateApp);
        return updateResult > 0;
    }

    @Override
    public Boolean deleteMyApp(Long id, HttpServletRequest request) {
        if (id == null || id <= 0) {
            throw new InvalidContentException("应用 ID 不合法");
        }

        // 获取当前登录用户
        User loginUser = userService.getUserInfo(request);
        if (loginUser == null) {
            throw new InvalidContentException("用户未登录");
        }

        // 校验应用是否存在且属于当前用户
        App existApp = getAppById(id);
        if (!existApp.getUserId().equals(loginUser.getId())) {
            throw new InvalidContentException("无权限删除该应用");
        }

        // 逻辑删除
        App updateApp = new App();
        updateApp.setId(id);
        updateApp.setIsDelete(1);
        updateApp.setDeleteTime(new java.util.Date());

        int updateResult = appMapper.update(updateApp);
        return updateResult > 0;
    }

    @Override
    public AppDetailResponseDto getAppDetail(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidContentException("应用 ID 不合法");
        }

        App app = getAppById(id);
        return getAppDetailVO(app);
    }

    @Override
    public PageResponseDto<AppDetailResponseDto> listMyAppsByPage(AppPageQueryRequestDto queryRequest, HttpServletRequest request) {
        if (queryRequest == null) {
            throw new InvalidContentException("查询条件不能为空");
        }

        // 获取当前登录用户
        User loginUser = userService.getUserInfo(request);
        if (loginUser == null) {
            throw new InvalidContentException("用户未登录");
        }

        // 构建查询条件：只查询当前用户的应用
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", loginUser.getId());
        queryWrapper.eq("is_delete", 0);

        // 支持按名称模糊查询
        if (StrUtil.isNotBlank(queryRequest.getAppName())) {
            queryWrapper.like("app_name", queryRequest.getAppName());
        }

        // 排序：按创建时间降序
        queryWrapper.orderBy("create_time", false);

        List<App> allList = appMapper.selectListByQuery(queryWrapper);
        return buildPageResponse(allList, queryRequest, 20);
    }

    @Override
    public PageResponseDto<AppDetailResponseDto> listFeaturedAppsByPage(AppPageQueryRequestDto queryRequest) {
        if (queryRequest == null) {
            throw new InvalidContentException("查询条件不能为空");
        }

        // 构建查询条件：只查询精选应用（priority >= 99）且已发布
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.ge("priority", 99);
        queryWrapper.eq("status", 1); // 已发布
        queryWrapper.eq("is_delete", 0);

        // 支持按名称模糊查询
        if (StrUtil.isNotBlank(queryRequest.getAppName())) {
            queryWrapper.like("app_name", queryRequest.getAppName());
        }

        // 排序：按优先级降序，创建时间降序
        queryWrapper.orderBy("priority", false);
        queryWrapper.orderBy("create_time", false);

        List<App> allList = appMapper.selectListByQuery(queryWrapper);
        return buildPageResponse(allList, queryRequest, 20);
    }

    @Override
    public Boolean deleteAppById(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidContentException("应用 ID 不合法");
        }

        // 逻辑删除
        App updateApp = new App();
        updateApp.setId(id);
        updateApp.setIsDelete(1);
        updateApp.setDeleteTime(new java.util.Date());

        int updateResult = appMapper.update(updateApp);
        return updateResult > 0;
    }

    @Override
    public Boolean adminUpdateApp(AppAdminUpdateRequestDto updateRequestDto) {
        if (updateRequestDto == null || updateRequestDto.getId() == null) {
            throw new InvalidContentException("应用 ID 不能为空");
        }

        // 校验应用是否存在
        getAppById(updateRequestDto.getId());

        // 构建更新对象
        App updateApp = new App();
        updateApp.setId(updateRequestDto.getId());

        // 更新应用名称
        if (StrUtil.isNotBlank(updateRequestDto.getAppName())) {
            updateApp.setAppName(updateRequestDto.getAppName());
        }

        // 更新应用封面
        if (StrUtil.isNotBlank(updateRequestDto.getCover())) {
            updateApp.setCover(updateRequestDto.getCover());
        }

        // 更新优先级
        if (updateRequestDto.getPriority() != null) {
            updateApp.setPriority(updateRequestDto.getPriority());
        }

        int updateResult = appMapper.update(updateApp);
        return updateResult > 0;
    }

    @Override
    public PageResponseDto<AppDetailResponseDto> adminListAppsByPage(AppPageQueryRequestDto queryRequest) {
        if (queryRequest == null) {
            throw new InvalidContentException("查询条件不能为空");
        }

        // 构建动态查询条件
        QueryWrapper queryWrapper = new QueryWrapper();

        // 根据 ID 查询
        if (queryRequest.getId() != null) {
            queryWrapper.eq("id", queryRequest.getId());
        }

        // 根据应用名称模糊查询
        if (StrUtil.isNotBlank(queryRequest.getAppName())) {
            queryWrapper.like("app_name", queryRequest.getAppName());
        }

        // 根据封面查询
        if (StrUtil.isNotBlank(queryRequest.getCover())) {
            queryWrapper.like("cover", queryRequest.getCover());
        }

        // 根据部署标识查询
        if (StrUtil.isNotBlank(queryRequest.getDeployKey())) {
            queryWrapper.eq("deploy_key", queryRequest.getDeployKey());
        }

        // 根据优先级查询
        if (queryRequest.getPriority() != null) {
            queryWrapper.eq("priority", queryRequest.getPriority());
        }

        // 根据用户ID查询
        if (queryRequest.getUserId() != null) {
            queryWrapper.eq("user_id", queryRequest.getUserId());
        }

        // 排除已删除的应用
        queryWrapper.eq("is_delete", 0);

        // 排序：按创建时间降序
        queryWrapper.orderBy("create_time", false);

        List<App> allList = appMapper.selectListByQuery(queryWrapper);
        return buildPageResponse(allList, queryRequest, null);
    }

    @Override
    public App getAppById(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidContentException("应用 ID 不合法");
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        queryWrapper.eq("is_delete", 0);

        App app = appMapper.selectOneByQuery(queryWrapper);
        if (app == null) {
            throw new InvalidContentException("应用不存在");
        }
        return app;
    }

    @Override
    public AppDetailResponseDto getAppDetailVO(App app) {
        if (app == null) {
            return null;
        }

        AppDetailResponseDto detailVO = new AppDetailResponseDto();
        BeanUtil.copyProperties(app, detailVO);

        // 获取最新版本的 initPrompt 和 codeGenType
        if (app.getLatestVersionId() != null) {
            AppVersion latestVersion = appVersionMapper.selectOneById(app.getLatestVersionId());
            if (latestVersion != null) {
                detailVO.setInitPrompt(latestVersion.getInitPrompt());
                detailVO.setCodeGenType(latestVersion.getCodeGenType());
            }
        }

        // 获取创建用户信息
        if (app.getUserId() != null) {
            User user = userService.getUserById(app.getUserId());
            if (user != null) {
                UserLoginResponseDto userVO = userService.getLoginUserVO(user);
                detailVO.setUserDetail(userVO);
            }
        }

        return detailVO;
    }

    @Override
    public List<AppDetailResponseDto> getAppDetailVOList(List<App> appList) {
        if (appList == null || appList.isEmpty()) {
            return List.of();
        }
        return appList.stream().map(this::getAppDetailVO).collect(Collectors.toList());
    }

    /**
     * 生成唯一的部署标识
     * @return 10位随机字符串
     */
    private String generateUniqueDeployKey() {
        String deployKey;
        int maxRetries = 10;
        int retries = 0;

        do {
            // 生成10位随机字符串（数字+字母）
            deployKey = RandomUtil.randomString(RandomUtil.BASE_CHAR_NUMBER, 10);

            // 检查是否已存在
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("deploy_key", deployKey);
            long count = appMapper.selectCountByQuery(queryWrapper);

            if (count == 0) {
                return deployKey;
            }

            retries++;
        } while (retries < maxRetries);

        throw new InvalidContentException("生成部署标识失败，请重试");
    }

    /**
     * 构建分页响应
     * @param allList 全量列表
     * @param queryRequest 查询请求
     * @param maxSize 最大每页数量（null表示不限制）
     * @return 分页响应
     */
    private PageResponseDto<AppDetailResponseDto> buildPageResponse(List<App> allList, AppPageQueryRequestDto queryRequest, Integer maxSize) {
        int total = allList == null ? 0 : allList.size();

        Integer current = queryRequest.getCurrent();
        Integer size = queryRequest.getSize();

        if (current == null || current < 1) {
            current = 1;
        }
        if (size == null || size < 1) {
            size = 10;
        }

        // 限制最大每页数量
        if (maxSize != null && size > maxSize) {
            size = maxSize;
        }

        int fromIndex = Math.min((current - 1) * size, total);
        int toIndex = Math.min(fromIndex + size, total);

        List<App> pageList = allList == null ? List.of() : allList.subList(fromIndex, toIndex);
        List<AppDetailResponseDto> voList = getAppDetailVOList(pageList);

        PageResponseDto<AppDetailResponseDto> pageResponse = new PageResponseDto<>();
        pageResponse.setCurrent(current);
        pageResponse.setSize(size);
        pageResponse.setTotal(total);
        pageResponse.setList(voList);

        return pageResponse;
    }
}
