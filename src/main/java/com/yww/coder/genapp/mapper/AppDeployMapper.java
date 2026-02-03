package com.yww.coder.genapp.mapper;

import com.yww.coder.genapp.model.entity.AppDeploy;
import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;

/**
* @author yyw
* @description 针对表【app_deploy(应用部署表)】的数据库操作Mapper
* @createDate 2026-01-22 14:33:31
* @Entity com.yww.coder.genapp.model.entity.AppDeploy
*/
@Mapper
public interface AppDeployMapper extends BaseMapper<AppDeploy> {


}
