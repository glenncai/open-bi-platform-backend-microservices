# Create Open BI Platform Database and Tables
# @author Glenn Cai
# @date 2023-07-18

-- Drop database if exists
DROP DATABASE IF EXISTS `bi`;

-- Create database if not exists
CREATE DATABASE IF NOT EXISTS `bi` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Change database
USE `bi`;

-- User table
CREATE TABLE IF NOT EXISTS `t_user`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary key',
    `username`   VARCHAR(256)    NOT NULL COMMENT 'Username',
    `password`   VARCHAR(512)    NOT NULL COMMENT 'Password',
    `role`       VARCHAR(256)    NOT NULL DEFAULT 'user' COMMENT 'user / admin',
    `login_ip`   VARCHAR(256)    NOT NULL DEFAULT '' COMMENT 'Login IP',
    `valid`      TINYINT         NOT NULL DEFAULT 1 COMMENT '0: invalid, 1: valid',
    `created_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
    `updated_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated time',
    INDEX `idx_username` (`username`),
    INDEX `idx_login_ip` (`login_ip`),
    INDEX `idx_valid` (`valid`),
    INDEX `idx_role` (`role`)
) COMMENT 'User table';

-- IP table
CREATE TABLE IF NOT EXISTS `t_ip`
(
    `ip`              VARCHAR(64) NOT NULL PRIMARY KEY DEFAULT '' COMMENT 'IP',
    `remaining_quota` INT         NOT NULL             DEFAULT 10 COMMENT 'Remaining AI service quota of this IP today',
    `last_call_date`  DATETIME    NOT NULL             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last call API service date'
) COMMENT 'IP table';

-- Chart table
CREATE TABLE IF NOT EXISTS `t_chart`
(
    `id`                   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary key',
    `goal`                 TEXT            NULL COMMENT 'Analysis target',
    `name`                 VARCHAR(128)    NULL COMMENT 'Chart name',
    `chart_data`           TEXT            NULL COMMENT 'Chart data',
    `chart_type`           VARCHAR(128)    NULL COMMENT 'Chart type',
    `gen_chart_data`       TEXT            NULL COMMENT 'Generated chart data',
    `gen_chart_conclusion` TEXT            NULL COMMENT 'Generated chart conclusion',
    `status`               TINYINT         NULL COMMENT '0: Waiting, 1: Running, 2: Succeed, 3: Failed',
    `exec_message`         TEXT            NULL COMMENT 'Execution message',
    `user_id`              BIGINT UNSIGNED NOT NULL COMMENT 'User ID',
    `valid`                TINYINT         NOT NULL DEFAULT 1 COMMENT '0: invalid, 1: valid',
    `created_at`           DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
    `updated_at`           DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated time',
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_valid` (`valid`),
    INDEX `idx_status` (`status`),
    INDEX `idx_name` (`name`),
    FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT 'Chart table';

-- AI key table
CREATE TABLE IF NOT EXISTS `t_ai_key`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary key',
    `key`        VARCHAR(256)    NOT NULL COMMENT 'AI key',
    `belong_to`  VARCHAR(256)    NOT NULL DEFAULT 'user' COMMENT 'user / admin',
    `valid`      TINYINT         NOT NULL DEFAULT 1 COMMENT '0: invalid, 1: valid',
    `created_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
    `updated_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated time',
    INDEX `idx_key` (`key`),
    INDEX `idx_belong_to` (`belong_to`),
    INDEX `idx_valid` (`valid`)
) COMMENT 'AI key table';