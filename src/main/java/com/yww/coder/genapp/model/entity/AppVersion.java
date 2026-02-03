package com.yww.coder.genapp.model.entity;


import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

/**
 * 应用版本表
 * @TableName app_version
 */
@Data
public class AppVersion implements Serializable {
    /**
     * 主键
     */
    @Id(keyType = KeyType.Generator,value = KeyGenerators.snowFlakeId)
    private Long id;

    /**
     * 关联应用ID
     */
    private Long appId;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 触发本版本生成的Prompt
     */
    private String initPrompt;

    /**
     * 代码生成类型（如：HTML/VUE/MULTI_FILE等）
     */
    private String codeGenType;

    /**
     * 存储类型（可选：LOCAL/OSS）
     */
    private String storageType;

    /**
     * 源码存储路径（OSS key前缀或本地路径）
     */
    private String storagePath;

    /**
     * 生成状态（0-生成中,1-成功,2-失败）
     */
    private Integer status;

    /**
     * 失败原因
     */
    private String errorMessage;

    /**
     * 创建者ID
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @Serial
    private static final long serialVersionUID = 1L;
}