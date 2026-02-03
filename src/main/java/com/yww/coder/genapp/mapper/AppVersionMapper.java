package com.yww.coder.genapp.mapper;

import com.yww.coder.genapp.model.entity.AppVersion;
import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;


/**
* @author yyw
* @description 针对表【app_version(应用版本表)】的数据库操作Mapper
* @createDate 2026-01-22 15:11:58
* @Entity com.yww.coder.genapp.model.entity.AppVersion
*/
@Mapper
public interface AppVersionMapper extends BaseMapper<AppVersion> {


}
