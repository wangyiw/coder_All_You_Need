package com.yww.coder.genapp.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.io.Serial;
import java.util.Date;
import java.io.Serializable;

/**
 * 应用主表
 * @TableName app
 */
@Data
public class App implements Serializable {
    @Serial
    private static final long serialVersionUID = 618479936825154649L;
    
    /**
     * 主键ID
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private Long id;
/**
     * 创建用户ID
     */
    private Long userId;
/**
     * 应用名称
     */
    private String appName;
/**
     * 应用简介
     */
    private String description;
/**
     * 应用封面URL
     */
    private String cover;
/**
     * 唯一部署标识符
     */
    private String deployKey;
/**
     * 最新生成版本ID
     */
    private Long latestVersionId;
/**
     * 当前线上生效版本ID
     */
    private Long deployedVersionId;
/**
     * 最近一次部署记录ID
     */
    private Long latestDeployId;
/**
     * 应用状态（0-待发布, 1-已发布, 2-已下线）
     */
    private Integer status;
/**
     * 优先级（99-精选,999-置顶,数字越大越靠前）
     */
    private Integer priority;
/**
     * 是否删除（0-否,1-是）
     */
    private Integer isDelete;
/**
     * 删除时间（回收站展示用，可还原）
     */
    private Date deleteTime;
/**
     * 创建时间
     */
    private Date createTime;
/**
     * 更新时间
     */
    private Date updateTime;


}

