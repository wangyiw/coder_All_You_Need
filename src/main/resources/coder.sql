create database if not exists coder_ai character set = utf8mb4 collate = utf8mb4_unicode_ci;

use coder_ai;
-- 用户表
create table if not exists user
(
    id              bigint auto_increment comment 'id' primary key,
    user_account    varchar(256)                           not null comment '账号',
    user_password   varchar(512)                           not null comment '密码',
    user_name       varchar(256)                           null comment '用户昵称',
    user_avatar     varchar(1024)                          null comment '用户头像',
    user_profile    varchar(512)                           null comment '用户简介',
    user_role       varchar(256) default 'user'            not null comment '用户角色：user/admin',

    -- 新增字段 (已转换为下划线命名)
    vip_expire_time datetime                               null comment '会员过期时间',
    vip_code        varchar(128)                           null comment '会员兑换码',
    vip_number      bigint                                 null comment '会员编号',
    share_code      varchar(20)  default null              comment '分享码',
    invite_user     bigint       default null              comment '邀请用户 id',

    -- 通用字段
    edit_time       datetime     default CURRENT_TIMESTAMP not null comment '编辑时间',
    create_time     datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete       tinyint      default 0                 not null comment '是否删除',

    -- 索引
    UNIQUE KEY uk_user_account (user_account),
    INDEX idx_user_name (user_name),
    INDEX idx_vip_code (vip_code),    -- 建议为兑换码加索引，查询更快
    INDEX idx_share_code (share_code) -- 建议为分享码加索引
) comment '用户' collate = utf8mb4_unicode_ci;
-- 应用表
CREATE TABLE `app` (
                       `id`                  BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                       `user_id`             BIGINT       NOT NULL COMMENT '创建用户ID',
                       `app_name`            VARCHAR(256) NULL     COMMENT '应用名称',
                       `description`         VARCHAR(512) NULL     COMMENT '应用简介',
                       `cover`               VARCHAR(512) NULL     COMMENT '应用封面URL',

                       `deploy_key`          CHAR(10)     NOT NULL COMMENT '唯一部署标识符',

                       `latest_version_id`   BIGINT       NULL     COMMENT '最新生成版本ID',
                       `deployed_version_id` BIGINT       NULL     COMMENT '当前线上生效版本ID',
                       `latest_deploy_id`    BIGINT       NULL     COMMENT '最近一次部署记录ID',

                       `status`              TINYINT      NOT NULL DEFAULT 0 COMMENT '应用状态（0-待发布, 1-已发布, 2-已下线）',
                       `priority`            INT          NOT NULL DEFAULT 0 COMMENT '优先级（99-精选,999-置顶,数字越大越靠前）',

                       `is_delete`           TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除（0-否,1-是）',
                       `delete_time`         DATETIME     NULL     COMMENT '删除时间（回收站展示用，可还原）',

                       `create_time`         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       `update_time`         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                       PRIMARY KEY (`id`),
                       UNIQUE KEY `uk_deploy_key` (`deploy_key`),
                       KEY `idx_user_priority` (`user_id`, `priority`),
                       KEY `idx_user_status` (`user_id`, `status`),
                       KEY `idx_is_delete` (`is_delete`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应用主表';

-- 应用版本表
CREATE TABLE `app_version` (
                               `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `app_id`        BIGINT       NOT NULL COMMENT '关联应用ID',
                               `version`   INT          NOT NULL DEFAULT 1 COMMENT '版本号',

                               `init_prompt`   TEXT         NULL     COMMENT '触发本版本生成的Prompt',
                               `code_gen_type` VARCHAR(64)  NOT NULL COMMENT '代码生成类型（如：HTML/VUE/MULTI_FILE等）',

                               `storage_type`  VARCHAR(16)  NULL     COMMENT '存储类型（可选：LOCAL/OSS）',
                               `storage_path`  VARCHAR(512) NOT NULL COMMENT '源码存储路径（OSS key前缀或本地路径）',

                               `status`        TINYINT      NOT NULL DEFAULT 0 COMMENT '生成状态（0-生成中,1-成功,2-失败）',
                               `error_message` TEXT         NULL     COMMENT '失败原因',

                               `user_id`       BIGINT       NOT NULL COMMENT '创建者ID',
                               `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uk_app_version` (`app_id`, `version`),
                               KEY `idx_app_create_time` (`app_id`, `create_time`),
                               KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应用版本表';
-- 应用部署表
CREATE TABLE `app_deploy` (
                              `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `app_id`       BIGINT       NOT NULL COMMENT '应用ID',
                              `version_id`   BIGINT       NOT NULL COMMENT '应用版本表主键ID',

                              `status`       TINYINT      NOT NULL DEFAULT 0 COMMENT '部署状态（0-待部署,1-已部署,2-部署失败）',
                              `deploy_url`   VARCHAR(512) NULL     COMMENT '部署成功后的访问URL',
                              `message`TEXT         NULL     COMMENT '部署失败原因',

                              `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                              PRIMARY KEY (`id`),
                              KEY `idx_app_create_time` (`app_id`, `create_time`),
                              KEY `idx_version_id` (`version_id`),
                              KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应用部署表';