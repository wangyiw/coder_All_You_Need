create database if not exists coder_ai character set = utf8mb4 collate = utf8mb4_unicode_ci;

use coder_ai;
-- 用户表
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