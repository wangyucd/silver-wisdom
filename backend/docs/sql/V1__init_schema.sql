CREATE DATABASE IF NOT EXISTS `silver_wisdom`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE `silver_wisdom`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `learn_record`;
DROP TABLE IF EXISTS `ai_generate_result`;
DROP TABLE IF EXISTS `ai_generate_task`;
DROP TABLE IF EXISTS `content_tag_rel`;
DROP TABLE IF EXISTS `content_item`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `hotspot`;
DROP TABLE IF EXISTS `user_status_log`;
DROP TABLE IF EXISTS `user_interest_tag`;
DROP TABLE IF EXISTS `user_account`;
DROP TABLE IF EXISTS `admin_account`;

CREATE TABLE `admin_account` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '管理员主键',
  `username` VARCHAR(64) NOT NULL COMMENT '登录账号',
  `password_hash` VARCHAR(255) NOT NULL COMMENT '密码哈希',
  `name` VARCHAR(64) NOT NULL COMMENT '展示名称',
  `status` VARCHAR(16) NOT NULL DEFAULT 'ENABLED' COMMENT '状态：ENABLED/DISABLED',
  `last_login_time` DATETIME NULL DEFAULT NULL COMMENT '最近登录时间',
  `creator` VARCHAR(255) NOT NULL COMMENT '创建人ID｜创建人名称',
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` VARCHAR(255) NOT NULL COMMENT '修改人ID｜修改人名称',
  `modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员账号表';

CREATE TABLE `user_account` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `wx_openid` VARCHAR(64) NOT NULL COMMENT '微信唯一标识',
  `nickname` VARCHAR(64) NOT NULL COMMENT '昵称',
  `avatar_url` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '头像地址',
  `mobile_masked` VARCHAR(32) NULL DEFAULT NULL COMMENT '脱敏手机号',
  `status` VARCHAR(16) NOT NULL DEFAULT 'ENABLED' COMMENT '状态：ENABLED/DISABLED',
  `last_login_time` DATETIME NULL DEFAULT NULL COMMENT '最近登录时间',
  `creator` VARCHAR(255) NOT NULL COMMENT '创建人ID｜创建人名称',
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` VARCHAR(255) NOT NULL COMMENT '修改人ID｜修改人名称',
  `modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_wx_openid` (`wx_openid`),
  KEY `idx_status_created` (`status`, `created` DESC),
  KEY `idx_last_login` (`last_login_time` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='小程序用户表';

CREATE TABLE `user_interest_tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT NOT NULL COMMENT '关联 user_account 表 id 字段',
  `tag` VARCHAR(64) NOT NULL COMMENT '标签名称',
  `weight` DECIMAL(5,2) NOT NULL DEFAULT 1.00 COMMENT '标签权重',
  `source` VARCHAR(32) NOT NULL DEFAULT 'QUESTIONNAIRE' COMMENT '来源：QUESTIONNAIRE',
  `creator` VARCHAR(255) NOT NULL COMMENT '创建人ID｜创建人名称',
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` VARCHAR(255) NOT NULL COMMENT '修改人ID｜修改人名称',
  `modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_tag` (`user_id`, `tag`),
  KEY `idx_user_tag` (`user_id`, `tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户兴趣标签表';

CREATE TABLE `user_status_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT NOT NULL COMMENT '关联 user_account 表 id 字段',
  `before_status` VARCHAR(16) NOT NULL COMMENT '变更前状态',
  `after_status` VARCHAR(16) NOT NULL COMMENT '变更后状态',
  `reason` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '变更原因',
  `operator_admin_id` BIGINT NOT NULL COMMENT '关联 admin_account 表 id 字段',
  `creator` VARCHAR(255) NOT NULL COMMENT '创建人ID｜创建人名称',
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` VARCHAR(255) NOT NULL COMMENT '修改人ID｜修改人名称',
  `modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_created` (`user_id`, `created` DESC),
  KEY `idx_operator_created` (`operator_admin_id`, `created` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户状态变更记录表';

CREATE TABLE `hotspot` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '热点主键',
  `title` VARCHAR(128) NOT NULL COMMENT '标题',
  `summary` VARCHAR(512) NOT NULL DEFAULT '' COMMENT '摘要',
  `cover_url` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '封面图',
  `weight` INT NOT NULL DEFAULT 0 COMMENT '权重',
  `start_time` DATETIME NOT NULL COMMENT '上线时间',
  `end_time` DATETIME NOT NULL COMMENT '下线时间',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
  `creator` VARCHAR(255) NOT NULL COMMENT '创建人ID｜创建人名称',
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` VARCHAR(255) NOT NULL COMMENT '修改人ID｜修改人名称',
  `modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_status_time_weight` (`status`, `start_time`, `end_time`, `weight` DESC),
  KEY `idx_creator` (`creator`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='热点表';

CREATE TABLE `category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类主键',
  `name` VARCHAR(64) NOT NULL COMMENT '分类名',
  `icon_url` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '图标',
  `cover_url` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '封面图',
  `sort` INT NOT NULL DEFAULT 0 COMMENT '排序值',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
  `creator` VARCHAR(255) NOT NULL COMMENT '创建人ID｜创建人名称',
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` VARCHAR(255) NOT NULL COMMENT '修改人ID｜修改人名称',
  `modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`),
  KEY `idx_status_sort` (`status`, `sort` DESC, `id` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='内容分类表';

CREATE TABLE `content_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '内容主键',
  `title` VARCHAR(128) NOT NULL COMMENT '标题',
  `summary` VARCHAR(1000) NOT NULL DEFAULT '' COMMENT '摘要',
  `type` VARCHAR(16) NOT NULL COMMENT '类型：ARTICLE/VIDEO',
  `category_id` BIGINT NOT NULL COMMENT '分类ID',
  `cover_url` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '封面图',
  `content_body` LONGTEXT NULL COMMENT '图文正文',
  `video_url` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '视频地址',
  `heat_score` INT NOT NULL DEFAULT 0 COMMENT '热度',
  `publish_status` VARCHAR(16) NOT NULL DEFAULT 'DRAFT' COMMENT '发布状态：DRAFT/PUBLISHED',
  `publish_time` DATETIME NULL DEFAULT NULL COMMENT '发布时间',
  `creator` VARCHAR(255) NOT NULL COMMENT '创建人ID｜创建人名称',
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` VARCHAR(255) NOT NULL COMMENT '修改人ID｜修改人名称',
  `modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category_publish` (`category_id`, `publish_status`, `publish_time` DESC),
  KEY `idx_heat_publish` (`heat_score` DESC, `publish_time` DESC),
  KEY `idx_creator` (`creator`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='内容表';

CREATE TABLE `content_tag_rel` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content_id` BIGINT NOT NULL COMMENT '关联 content_item 表 id 字段',
  `tag` VARCHAR(64) NOT NULL COMMENT '标签',
  `creator` VARCHAR(255) NOT NULL COMMENT '创建人ID｜创建人名称',
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` VARCHAR(255) NOT NULL COMMENT '修改人ID｜修改人名称',
  `modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_content_tag` (`content_id`, `tag`),
  KEY `idx_tag` (`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='内容标签关联表';

CREATE TABLE `ai_generate_task` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_id` VARCHAR(64) NOT NULL COMMENT '业务任务号',
  `user_id` BIGINT NOT NULL COMMENT '关联 user_account 表 id 字段',
  `prompt` TEXT NOT NULL COMMENT '用户输入',
  `scene` VARCHAR(64) NOT NULL COMMENT '生成场景',
  `status` VARCHAR(16) NOT NULL DEFAULT 'PENDING' COMMENT '状态：PENDING/RUNNING/SUCCESS/FAILED',
  `fail_reason` VARCHAR(512) NOT NULL DEFAULT '' COMMENT '失败原因',
  `creator` VARCHAR(255) NOT NULL COMMENT '创建人ID｜创建人名称',
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` VARCHAR(255) NOT NULL COMMENT '修改人ID｜修改人名称',
  `modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_task_id` (`task_id`),
  KEY `idx_user_created` (`user_id`, `created` DESC),
  KEY `idx_status_created` (`status`, `created` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI生成任务表';

CREATE TABLE `ai_generate_result` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_id` VARCHAR(64) NOT NULL COMMENT '关联 ai_generate_task 表 task_id 字段',
  `title` VARCHAR(128) NOT NULL COMMENT '结果标题',
  `summary` VARCHAR(512) NOT NULL DEFAULT '' COMMENT '结果摘要',
  `body` LONGTEXT NOT NULL COMMENT '结果正文',
  `outline` LONGTEXT NULL COMMENT '结果大纲',
  `creator` VARCHAR(255) NOT NULL COMMENT '创建人ID｜创建人名称',
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` VARCHAR(255) NOT NULL COMMENT '修改人ID｜修改人名称',
  `modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_task_id` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI生成结果表';

CREATE TABLE `learn_record` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT NOT NULL COMMENT '关联 user_account 表 id 字段',
  `record_type` VARCHAR(32) NOT NULL COMMENT '记录类型：GENERATED/CONTENT_VIEW',
  `biz_id` VARCHAR(64) NOT NULL COMMENT '关联业务ID',
  `progress` INT NOT NULL DEFAULT 0 COMMENT '学习进度',
  `last_view_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近查看时间',
  `creator` VARCHAR(255) NOT NULL COMMENT '创建人ID｜创建人名称',
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` VARCHAR(255) NOT NULL COMMENT '修改人ID｜修改人名称',
  `modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_record_biz` (`user_id`, `record_type`, `biz_id`),
  KEY `idx_user_last_view` (`user_id`, `last_view_time` DESC),
  KEY `idx_record_type_created` (`record_type`, `created` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学习记录表';

SET FOREIGN_KEY_CHECKS = 1;
