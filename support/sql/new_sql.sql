




# 1 活动规则配置表 [单表 zlx_activity_rule,库在 ]
CREATE TABLE `zlx_activity_rule` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_effect_start` datetime NOT NULL COMMENT '激活码生效开始时间',
  `gmt_effect_end` datetime NOT NULL COMMENT '激活码生效截止时间',
  `creator` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `org_id` bigint NOT NULL COMMENT '租户id',
  `product_id` bigint NOT NULL COMMENT '产品id',
  `lession_id` bigint NOT NULL COMMENT '课程id',
  `activity_id` bigint NOT NULL COMMENT '发码活动id',
  `yk_charge_id` bigint NOT NULL COMMENT '优酷直充活动id',
  `org_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户名称',
  `product_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品名称',
  `lession_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程名称',
  `activity_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发码活动名称',
  `state` int NOT NULL COMMENT '活动状态(失效状态不可再发放激活码)',
  `feature` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='活动规则配置表';


# 2 激活码记录表# [单表 zlx_cdkey_record,库在 ]
CREATE TABLE `zlx_cdkey_record` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_effect_start` datetime NOT NULL COMMENT '激活码生效开始时间',
  `gmt_effect_end` datetime NOT NULL COMMENT '激活码生效截止时间',
  `gmt_send` datetime NOT NULL COMMENT '发送激活短信时间',
  `gmt_activate` datetime NOT NULL COMMENT '激活时间',
  `org_id` bigint NOT NULL COMMENT '租户id',
  `product_id` bigint NOT NULL COMMENT '产品id',
  `lession_id` bigint NOT NULL COMMENT '课程id',
  `activity_id` bigint NOT NULL COMMENT '发码活动id',
  `yk_charge_id` bigint NOT NULL COMMENT '优酷直充活动id',
  `activate_utid` bigint NOT NULL COMMENT '激活优酷id',
  `org_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户名称',
  `product_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品名称',
  `lession_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程名称',
  `activity_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发码活动名称',
  `user_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发码活动名称',
  `user_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发码活动名称',
  `activate_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '激活手机号',
  `active_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '激活码',
  `operation_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发码操作人姓名',
  `key_state` int NOT NULL COMMENT '激活码状态：未使用、已使用',
  `feature` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='激活码记录表';