package com.yww.coder.user.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.mybatisflex.core.query.QueryWrapper;
import com.yww.coder.user.mapper.UserMapper;
import com.yww.coder.user.model.dto.UserAddRequest;
import com.yww.coder.user.model.dto.UserLoginResponseDto;
import com.yww.coder.user.model.dto.UserQueryRequest;
import com.yww.coder.user.model.dto.UserRegisterRequestDto;
import com.yww.coder.user.model.dto.UserUpdateRequest;
import com.yww.coder.user.model.entity.User;
import com.yww.coder.user.model.enums.UserRoleEnum;
import com.yww.coder.user.service.UserService;
import com.yww.coder.core.result.InvalidContentException;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import static com.yww.coder.constant.UserConstant.USER_LOGIN_STATE;

import java.util.List;
import java.util.stream.Collectors;

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
     * @param userAccount 用户账号
     * @param password 密码
     * @param request 请求
     * @return 登录用户信息
     */
    @Override
    public UserLoginResponseDto userLogin(String userAccount, String password, HttpServletRequest request) {
        // 1. 校验
        if(StrUtil.hasBlank(userAccount, password)){
            throw new InvalidContentException("用户名或密码不能为空");
        }
        if (userAccount.length() < 4) {
            throw new InvalidContentException("用户账号过短");
        }
        if (password.length() < 8) {
            throw new InvalidContentException("用户密码过短");
        }
        
        // 2. 加密
        String encryptPassword = getEncryptPassword(password);
        
        // 3. 查询用户是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_account", userAccount).eq("user_password", encryptPassword);
        User user = this.userMapper.selectOneByQuery(queryWrapper);
        if(ObjectUtil.isEmpty(user)){
            throw new InvalidContentException("用户不存在或密码错误");
        }

        // 4. 记录用户登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return this.getLoginUserVO(user);
    }
    /**
     * 用户注册
     * @param registerDto 注册信息
     * @return 新用户 id
     */
    @Override
    public long userRegister(UserRegisterRequestDto registerDto) {
        if (registerDto == null) {
            throw new InvalidContentException("注册信息不能为空");
        }
        
        String userAccount = registerDto.getUserAccount();
        String password = registerDto.getPassword();
        String confirmPassword = registerDto.getConfirmPassword();
        
        // 1. 校验
        if (StrUtil.hasBlank(userAccount, password, confirmPassword)) {
            throw new InvalidContentException("账号或密码不能为空");
        }
        if (userAccount.length() < 4) {
            throw new InvalidContentException("用户账号过短，至少 4 位");
        }
        if (password.length() < 8 || confirmPassword.length() < 8) {
            throw new InvalidContentException("用户密码过短，至少 8 位");
        }
        if (!password.equals(confirmPassword)) {
            throw new InvalidContentException("两次输入的密码不一致");
        }
        
        // 2. 检查账号是否重复
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_account", userAccount);
        long count = this.userMapper.selectCountByQuery(queryWrapper);
        if (count > 0) {
            throw new InvalidContentException("账号已存在");
        }
        
        // 3. 加密密码
        String encryptPassword = getEncryptPassword(password);
        
        // 4. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        
        // 设置用户名，如果没有提供则使用默认值
        user.setUserName(StrUtil.isNotBlank(registerDto.getUserName()) ? registerDto.getUserName() : "默认用户");
        
        // 设置头像和简介（可选）
        if (StrUtil.isNotBlank(registerDto.getUserAvatar())) {
            user.setUserAvatar(registerDto.getUserAvatar());
        }
        if (StrUtil.isNotBlank(registerDto.getUserProfile())) {
            user.setUserProfile(registerDto.getUserProfile());
        }
        
        // 设置用户角色，如果没有提供或无效则使用默认角色
        if (StrUtil.isNotBlank(registerDto.getUserRole()) && UserRoleEnum.getEnumByValue(registerDto.getUserRole()) != null) {
            user.setUserRole(registerDto.getUserRole());
        } else {
            user.setUserRole(UserRoleEnum.USER.getValue());
        }
        
        int insertResult = this.userMapper.insert(user);
        if (insertResult <= 0) {
            throw new InvalidContentException("注册失败，数据库错误");
        }
        return user.getId();
    }
    /**
     * 加密密码
     * @param userPassword 原始密码
     * @return 加密后的密码
     */
    @Override
    public String getEncryptPassword(String userPassword) {
        // 盐值，混淆密码
        final String SALT = "password";
        return DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
    }
    /**
     * 获取脱敏的用户登录信息
     * @param user 用户实体
     * @return 脱敏后的用户登录信息
     */
    @Override
    public UserLoginResponseDto getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserLoginResponseDto loginUserVO = new UserLoginResponseDto();
        BeanUtil.copyProperties(user, loginUserVO);
        return loginUserVO;
    }
    /**
     * 获取当前登录用户信息
     * @param request 请求
     * @return 当前登录用户
     */
    @Override
    public User getUserInfo(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new InvalidContentException("未登录");
        }
        // 从数据库查询（追求性能的话可以注释，直接返回上述结果）
        long userId = currentUser.getId();
        currentUser = this.userMapper.selectOneById(userId);
        if (currentUser == null) {
            throw new InvalidContentException("用户不存在");
        }
        return currentUser;
    }

    /**
     * 用户注销
     * @param request 请求
     * @return 是否成功
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new InvalidContentException("未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }
    /**
     * 用户列表查询
     * @param userQueryRequest 查询条件
     * @return 用户列表
     */
    @Override
    public List<User> queryUser(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new InvalidContentException("查询条件不能为空");
        }
        
        // 构建动态查询条件
        QueryWrapper queryWrapper = new QueryWrapper();
        
        // 根据 ID查询
        if (userQueryRequest.getId() != null) {
            queryWrapper.eq("id", userQueryRequest.getId());
        }
        
        // 根据用户名模糊查询
        if (StrUtil.isNotBlank(userQueryRequest.getUserName())) {
            queryWrapper.like("user_name", userQueryRequest.getUserName());
        }
        
        // 根据账号精确查询
        if (StrUtil.isNotBlank(userQueryRequest.getUserAccount())) {
            queryWrapper.eq("user_account", userQueryRequest.getUserAccount());
        }
        
        // 根据简介模糊查询
        if (StrUtil.isNotBlank(userQueryRequest.getUserProfile())) {
            queryWrapper.like("user_profile", userQueryRequest.getUserProfile());
        }
        
        // 根据角色查询
        if (StrUtil.isNotBlank(userQueryRequest.getUserRole())) {
            queryWrapper.eq("user_role", userQueryRequest.getUserRole());
        }
        
        // 排除已删除的用户
        queryWrapper.eq("is_delete", 0);
        
        // 执行查询
        return this.userMapper.selectListByQuery(queryWrapper);
    }
    /**
     * 更新用户信息
     * @param userUpdateRequest 更新信息
     */
    @Override
    public void updateUser(UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new InvalidContentException("用户 ID 不能为空");
        }
        
        // 1. 校验用户是否存在
        User existUser = this.userMapper.selectOneById(userUpdateRequest.getId());
        if (existUser == null) {
            throw new InvalidContentException("用户不存在");
        }
        
        // 2. 构建更新对象
        User updateUser = new User();
        updateUser.setId(userUpdateRequest.getId());
        
        // 3. 只更新提供的字段
        if (StrUtil.isNotBlank(userUpdateRequest.getUserName())) {
            updateUser.setUserName(userUpdateRequest.getUserName());
        }
        if (StrUtil.isNotBlank(userUpdateRequest.getUserAvatar())) {
            updateUser.setUserAvatar(userUpdateRequest.getUserAvatar());
        }
        if (StrUtil.isNotBlank(userUpdateRequest.getUserProfile())) {
            updateUser.setUserProfile(userUpdateRequest.getUserProfile());
        }
        if (StrUtil.isNotBlank(userUpdateRequest.getUserRole())) {
            // 校验角色是否合法
            if (UserRoleEnum.getEnumByValue(userUpdateRequest.getUserRole()) == null) {
                throw new InvalidContentException("用户角色不合法");
            }
            updateUser.setUserRole(userUpdateRequest.getUserRole());
        }
        
        // 4. 执行更新
        int updateResult = this.userMapper.update(updateUser);
        if (updateResult <= 0) {
            throw new InvalidContentException("更新失败");
        }
    }

    /**
     * 【管理员】添加用户
     * @param userAddRequest 添加信息
     * @return 新用户 id
     */
    @Override
    public long addUser(UserAddRequest userAddRequest) {
        if (userAddRequest == null) {
            throw new InvalidContentException("添加信息不能为空");
        }
        if (StrUtil.isBlank(userAddRequest.getUserAccount())) {
            throw new InvalidContentException("用户账号不能为空");
        }

        // 检查账号是否重复
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_account", userAddRequest.getUserAccount());
        long count = this.userMapper.selectCountByQuery(queryWrapper);
        if (count > 0) {
            throw new InvalidContentException("账号已存在");
        }

        User user = new User();
        BeanUtil.copyProperties(userAddRequest, user);

        // 默认密码 12345678
        final String DEFAULT_PASSWORD = "12345678";
        String encryptPassword = getEncryptPassword(DEFAULT_PASSWORD);
        user.setUserPassword(encryptPassword);

        // 角色兜底
        if (StrUtil.isBlank(user.getUserRole()) || UserRoleEnum.getEnumByValue(user.getUserRole()) == null) {
            user.setUserRole(UserRoleEnum.USER.getValue());
        }
        if (StrUtil.isBlank(user.getUserName())) {
            user.setUserName("默认用户");
        }

        int insertResult = this.userMapper.insert(user);
        if (insertResult <= 0) {
            throw new InvalidContentException("添加用户失败");
        }
        return user.getId();
    }

    /**
     * 【管理员】根据 id 获取用户
     * @param id 用户 id
     * @return 用户
     */
    @Override
    public User getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidContentException("用户 id 不合法");
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id).eq("is_delete", 0);
        User user = this.userMapper.selectOneByQuery(queryWrapper);
        if (user == null) {
            throw new InvalidContentException("用户不存在");
        }
        return user;
    }

    /**
     * 【管理员】删除用户
     * @param id 用户 id
     * @return 是否成功
     */
    @Override
    public boolean deleteUser(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidContentException("用户 id 不合法");
        }
        // 逻辑删除：is_delete = 1
        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setIsDelete(1);
        int updateResult = this.userMapper.update(updateUser);
        return updateResult > 0;
    }

    @Override
    public List<UserLoginResponseDto> getLoginUserVOList(List<User> userList) {
        if (userList == null || userList.isEmpty()) {
            return List.of();
        }
        return userList.stream().map(this::getLoginUserVO).collect(Collectors.toList());
    }
}

