-- =============================================
-- App 模块 Mock 数据脚本
-- 用于测试应用管理功能
-- =============================================

USE coder_ai;

-- =============================================
-- 1. 插入测试应用数据
-- =============================================

-- 精选应用（priority >= 99）
INSERT INTO `app` (`id`, `user_id`, `app_name`, `description`, `cover`, `deploy_key`, `latest_version_id`, `deployed_version_id`, `latest_deploy_id`, `status`, `priority`, `is_delete`, `delete_time`, `create_time`, `update_time`) VALUES
(1001, 1, '【置顶】AI 代码生成平台', '基于 AI 的智能代码生成平台，支持多种编程语言和框架', 'https://picsum.photos/400/300?random=1', 'A1B2C3D4E5', 10001, 10001, NULL, 1, 999, 0, NULL, NOW(), NOW()),
(1002, 1, '【精选】在线博客系统', '功能完善的个人博客系统，支持 Markdown 编辑', 'https://picsum.photos/400/300?random=2', 'F6G7H8I9J0', 10002, 10002, NULL, 1, 99, 0, NULL, NOW(), NOW()),
(1003, 1, '【精选】任务管理工具', '高效的任务管理和协作工具，支持团队协作', 'https://picsum.photos/400/300?random=3', 'K1L2M3N4O5', 10003, 10003, NULL, 1, 99, 0, NULL, NOW(), NOW()),
(1004, 1, '【精选】图片分享社区', '基于社交的图片分享平台，支持点赞评论', 'https://picsum.photos/400/300?random=4', 'P6Q7R8S9T0', 10004, 10004, NULL, 1, 99, 0, NULL, NOW(), NOW()),
(1005, 1, '【精选】在线音乐播放器', '支持多种音频格式的在线音乐播放器', 'https://picsum.photos/400/300?random=5', 'U1V2W3X4Y5', 10005, 10005, NULL, 1, 99, 0, NULL, NOW(), NOW());

-- 普通应用（priority < 99）
INSERT INTO `app` (`id`, `user_id`, `app_name`, `description`, `cover`, `deploy_key`, `latest_version_id`, `deployed_version_id`, `latest_deploy_id`, `status`, `priority`, `is_delete`, `delete_time`, `create_time`, `update_time`) VALUES
(1006, 1, '个人简历生成器', '快速生成专业的个人简历', 'https://picsum.photos/400/300?random=6', 'Z6A7B8C9D0', 10006, NULL, NULL, 0, 0, 0, NULL, NOW(), NOW()),
(1007, 1, '天气预报应用', '实时天气预报和未来一周天气趋势', 'https://picsum.photos/400/300?random=7', 'E1F2G3H4I5', 10007, 10007, NULL, 1, 0, 0, NULL, NOW(), NOW()),
(1008, 1, '待办事项清单', '简洁的待办事项管理工具', 'https://picsum.photos/400/300?random=8', 'J6K7L8M9N0', 10008, NULL, NULL, 0, 0, 0, NULL, NOW(), NOW()),
(1009, 1, '在线计算器', '功能强大的科学计算器', 'https://picsum.photos/400/300?random=9', 'O1P2Q3R4S5', 10009, 10009, NULL, 1, 0, 0, NULL, NOW(), NOW()),
(1010, 1, '番茄钟工具', '基于番茄工作法的时间管理工具', 'https://picsum.photos/400/300?random=10', 'T6U7V8W9X0', 10010, NULL, NULL, 0, 0, 0, NULL, NOW(), NOW()),
(1011, 1, '在线笔记本', '支持富文本编辑的在线笔记应用', 'https://picsum.photos/400/300?random=11', 'Y1Z2A3B4C5', 10011, 10011, NULL, 1, 10, 0, NULL, NOW(), NOW()),
(1012, 1, '二维码生成器', '快速生成各种类型的二维码', 'https://picsum.photos/400/300?random=12', 'D6E7F8G9H0', 10012, NULL, NULL, 0, 0, 0, NULL, NOW(), NOW()),
(1013, 1, '颜色选择器', '专业的颜色选择和配色工具', 'https://picsum.photos/400/300?random=13', 'I1J2K3L4M5', 10013, 10013, NULL, 1, 5, 0, NULL, NOW(), NOW()),
(1014, 1, '在线画板', '支持多种画笔和图层的在线绘图工具', 'https://picsum.photos/400/300?random=14', 'N6O7P8Q9R0', 10014, NULL, NULL, 0, 0, 0, NULL, NOW(), NOW()),
(1015, 1, 'Markdown 编辑器', '实时预览的 Markdown 编辑器', 'https://picsum.photos/400/300?random=15', 'S1T2U3V4W5', 10015, 10015, NULL, 1, 20, 0, NULL, NOW(), NOW());

-- 已删除的应用（用于测试回收站功能）
INSERT INTO `app` (`id`, `user_id`, `app_name`, `description`, `cover`, `deploy_key`, `latest_version_id`, `deployed_version_id`, `latest_deploy_id`, `status`, `priority`, `is_delete`, `delete_time`, `create_time`, `update_time`) VALUES
(1016, 1, '已删除的测试应用', '这是一个已被删除的应用', NULL, 'X6Y7Z8A9B0', NULL, NULL, NULL, 0, 0, 1, NOW(), DATE_SUB(NOW(), INTERVAL 7 DAY), NOW()),
(1017, 1, '回收站中的应用', '等待恢复或永久删除', NULL, 'C1D2E3F4G5', NULL, NULL, NULL, 0, 0, 1, NOW(), DATE_SUB(NOW(), INTERVAL 3 DAY), NOW());

-- =============================================
-- 2. 插入应用版本数据
-- =============================================

INSERT INTO `app_version` (`id`, `app_id`, `version`, `init_prompt`, `code_gen_type`, `storage_type`, `storage_path`, `status`, `error_message`, `user_id`, `create_time`, `update_time`) VALUES
-- AI 代码生成平台的版本
(10001, 1001, 1, '创建一个 AI 代码生成平台，支持多种编程语言', 'MULTI_FILE', 'OSS', 'apps/1001/v1/', 1, NULL, 1, NOW(), NOW()),

-- 在线博客系统的版本
(10002, 1002, 1, '创建一个个人博客系统，支持文章发布、分类、标签等功能', 'VUE', 'OSS', 'apps/1002/v1/', 1, NULL, 1, NOW(), NOW()),

-- 任务管理工具的版本
(10003, 1003, 1, '创建一个任务管理工具，支持任务创建、分配、进度跟踪', 'REACT', 'OSS', 'apps/1003/v1/', 1, NULL, 1, NOW(), NOW()),

-- 图片分享社区的版本
(10004, 1004, 1, '创建一个图片分享社区，支持图片上传、点赞、评论', 'VUE', 'OSS', 'apps/1004/v1/', 1, NULL, 1, NOW(), NOW()),

-- 在线音乐播放器的版本
(10005, 1005, 1, '创建一个在线音乐播放器，支持播放列表、歌词显示', 'HTML', 'OSS', 'apps/1005/v1/', 1, NULL, 1, NOW(), NOW()),

-- 个人简历生成器的版本
(10006, 1006, 1, '创建一个个人简历生成器，支持多种模板', 'HTML', 'LOCAL', 'apps/1006/v1/', 0, NULL, 1, NOW(), NOW()),

-- 天气预报应用的版本
(10007, 1007, 1, '创建一个天气预报应用，显示实时天气和未来趋势', 'HTML', 'OSS', 'apps/1007/v1/', 1, NULL, 1, NOW(), NOW()),

-- 待办事项清单的版本
(10008, 1008, 1, '创建一个待办事项清单，支持任务添加、完成、删除', 'HTML', 'LOCAL', 'apps/1008/v1/', 0, NULL, 1, NOW(), NOW()),

-- 在线计算器的版本
(10009, 1009, 1, '创建一个科学计算器，支持基本运算和科学计算', 'HTML', 'OSS', 'apps/1009/v1/', 1, NULL, 1, NOW(), NOW()),

-- 番茄钟工具的版本
(10010, 1010, 1, '创建一个番茄钟工具，帮助用户进行时间管理', 'HTML', 'LOCAL', 'apps/1010/v1/', 0, NULL, 1, NOW(), NOW()),

-- 在线笔记本的版本
(10011, 1011, 1, '创建一个在线笔记本，支持富文本编辑和分类', 'VUE', 'OSS', 'apps/1011/v1/', 1, NULL, 1, NOW(), NOW()),

-- 二维码生成器的版本
(10012, 1012, 1, '创建一个二维码生成器，支持文本、URL、名片等类型', 'HTML', 'LOCAL', 'apps/1012/v1/', 0, NULL, 1, NOW(), NOW()),

-- 颜色选择器的版本
(10013, 1013, 1, '创建一个颜色选择器，支持多种颜色模式和配色方案', 'HTML', 'OSS', 'apps/1013/v1/', 1, NULL, 1, NOW(), NOW()),

-- 在线画板的版本
(10014, 1014, 1, '创建一个在线画板，支持多种画笔和图层管理', 'HTML', 'LOCAL', 'apps/1014/v1/', 0, NULL, 1, NOW(), NOW()),

-- Markdown 编辑器的版本
(10015, 1015, 1, '创建一个 Markdown 编辑器，支持实时预览和导出', 'HTML', 'OSS', 'apps/1015/v1/', 1, NULL, 1, NOW(), NOW());

-- =============================================
-- 3. 插入应用部署数据
-- =============================================

INSERT INTO `app_deploy` (`id`, `app_id`, `version_id`, `status`, `deploy_url`, `message`, `create_time`, `update_time`) VALUES
-- 成功部署的应用
(20001, 1001, 10001, 1, 'https://app.example.com/A1B2C3D4E5', NULL, NOW(), NOW()),
(20002, 1002, 10002, 1, 'https://app.example.com/F6G7H8I9J0', NULL, NOW(), NOW()),
(20003, 1003, 10003, 1, 'https://app.example.com/K1L2M3N4O5', NULL, NOW(), NOW()),
(20004, 1004, 10004, 1, 'https://app.example.com/P6Q7R8S9T0', NULL, NOW(), NOW()),
(20005, 1005, 10005, 1, 'https://app.example.com/U1V2W3X4Y5', NULL, NOW(), NOW()),
(20006, 1007, 10007, 1, 'https://app.example.com/E1F2G3H4I5', NULL, NOW(), NOW()),
(20007, 1009, 10009, 1, 'https://app.example.com/O1P2Q3R4S5', NULL, NOW(), NOW()),
(20008, 1011, 10011, 1, 'https://app.example.com/Y1Z2A3B4C5', NULL, NOW(), NOW()),
(20009, 1013, 10013, 1, 'https://app.example.com/I1J2K3L4M5', NULL, NOW(), NOW()),
(20010, 1015, 10015, 1, 'https://app.example.com/S1T2U3V4W5', NULL, NOW(), NOW()),

-- 部署失败的应用
(20011, 1006, 10006, 2, NULL, '部署失败：存储空间不足', NOW(), NOW()),
(20012, 1008, 10008, 2, NULL, '部署失败：网络连接超时', NOW(), NOW());

-- =============================================
-- 4. 更新应用表的部署关联字段
-- =============================================

UPDATE `app` SET `latest_deploy_id` = 20001 WHERE `id` = 1001;
UPDATE `app` SET `latest_deploy_id` = 20002 WHERE `id` = 1002;
UPDATE `app` SET `latest_deploy_id` = 20003 WHERE `id` = 1003;
UPDATE `app` SET `latest_deploy_id` = 20004 WHERE `id` = 1004;
UPDATE `app` SET `latest_deploy_id` = 20005 WHERE `id` = 1005;
UPDATE `app` SET `latest_deploy_id` = 20006 WHERE `id` = 1007;
UPDATE `app` SET `latest_deploy_id` = 20007 WHERE `id` = 1009;
UPDATE `app` SET `latest_deploy_id` = 20008 WHERE `id` = 1011;
UPDATE `app` SET `latest_deploy_id` = 20009 WHERE `id` = 1013;
UPDATE `app` SET `latest_deploy_id` = 20010 WHERE `id` = 1015;

-- =============================================
-- 5. 查询验证
-- =============================================

-- 查询所有精选应用（priority >= 99）
SELECT id, app_name, priority, status FROM `app` WHERE `priority` >= 99 AND `is_delete` = 0 ORDER BY `priority` DESC;

-- 查询所有已发布的应用
SELECT id, app_name, status, deploy_key FROM `app` WHERE `status` = 1 AND `is_delete` = 0;

-- 查询回收站中的应用
SELECT id, app_name, delete_time FROM `app` WHERE `is_delete` = 1;

-- 查询应用及其版本信息
SELECT 
    a.id AS app_id,
    a.app_name,
    a.status AS app_status,
    v.version,
    v.code_gen_type,
    v.status AS version_status
FROM `app` a
LEFT JOIN `app_version` v ON a.latest_version_id = v.id
WHERE a.is_delete = 0
ORDER BY a.priority DESC, a.create_time DESC
LIMIT 10;

-- 查询应用及其部署信息
SELECT 
    a.id AS app_id,
    a.app_name,
    d.deploy_url,
    d.status AS deploy_status
FROM `app` a
LEFT JOIN `app_deploy` d ON a.latest_deploy_id = d.id
WHERE a.is_delete = 0 AND a.status = 1
ORDER BY a.priority DESC;

-- =============================================
-- Mock 数据统计
-- =============================================
-- 总应用数：17 个（15 个正常 + 2 个已删除）
-- 精选应用：5 个（priority >= 99）
-- 已发布应用：10 个（status = 1）
-- 待发布应用：5 个（status = 0）
-- 已删除应用：2 个（is_delete = 1）
-- 应用版本：15 个
-- 部署记录：12 个（10 个成功 + 2 个失败）
-- =============================================
