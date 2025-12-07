package com.yww.coder.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yww.coder.user.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yyw
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2025-12-08 02:06:57
* @Entity com.yww.coder.user.entity.model.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {


}
