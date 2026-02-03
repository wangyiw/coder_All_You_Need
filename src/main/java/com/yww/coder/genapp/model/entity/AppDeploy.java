package com.yww.coder.genapp.model.entity;



import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

/**
 * 应用部署表
 * @TableName app_deploy
 */
@Data
public class AppDeploy implements Serializable {
    /**
     * 主键
     */
    @Id(keyType = KeyType.Generator,value = KeyGenerators.snowFlakeId)
    private Long id;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 应用版本表主键ID
     */
    private Long versionId;

    /**
     * 部署状态（0-待部署,1-已部署,2-部署失败）
     */
    private Integer status;

    /**
     * 部署成功后的访问URL
     */
    private String deployUrl;

    /**
     * 部署失败原因
     */
    private String message;

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