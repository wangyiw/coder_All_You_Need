package com.yww.coder.genapp.mapper;

import com.yww.coder.genapp.model.entity.App;
import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;

/**
* @author yyw
* @description 针对表【app(应用主表)】的数据库操作Mapper
* @createDate 2026-01-22 14:24:53
* @Entity com.yww.coder.genapp.model.entity.App
*/
@Mapper
public interface AppMapper extends BaseMapper<App> {


}
