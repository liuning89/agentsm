drop table lf_report_agent;
drop table lf_report_store;


CREATE TABLE `lf_report_agent` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID' ,
`agentId`  bigint(20) NOT NULL COMMENT '经纪人ID' ,
`storeId`  int(7) NOT NULL ,
`publishNum`  int(10) NOT NULL COMMENT '发布房源数' ,
`uploadPicNum`  int(10) NOT NULL COMMENT '上传图片套数' ,
`keyNum`  int(10) NOT NULL COMMENT '钥匙数' ,
`entrustNum`  int(10) NOT NULL COMMENT '独家委托数' ,
`addMasterNum`  int(10) NOT NULL COMMENT '新增客户数' ,
`contactLandladyCount`  int(10) NOT NULL COMMENT '联系房东次数' ,
`shareHouseNum`  int(10) NOT NULL COMMENT '分享房源套数' ,
`forwardHouseCount`  int(10) NOT NULL COMMENT '转发房源次数' ,
`leadMasterNum`  int(10) NOT NULL COMMENT '带看客户数' ,
`createTime`  timestamp NULL DEFAULT NULL COMMENT '创建时间' ,
`updateTime`  timestamp NULL DEFAULT NULL COMMENT '更新时间' ,
`status`  tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

CREATE TABLE `lf_report_month_store` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`storeId`  int(11) NOT NULL ,
`publishMans`  int(4) NULL DEFAULT 0 COMMENT '发布房源人数' ,
`uploadPicMans`  int(4) NULL DEFAULT 0 COMMENT '上传图片人数' ,
`createTime`  datetime NULL DEFAULT NULL ,
`updateTime`  datetime NULL DEFAULT NULL ,
`status`  int(11) NULL DEFAULT 1 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;


CREATE TABLE `lf_report_store` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID' ,
`storeId`  bigint(20) NOT NULL COMMENT '门店ID' ,
`publishNum`  int(10) NOT NULL COMMENT '发布房源数' ,
`publishManNum`  int(10) NOT NULL COMMENT '发布房源人数' ,
`uploadPicNum`  int(10) NOT NULL COMMENT '上传图片套数' ,
`uploadPicManNum`  int(10) NOT NULL COMMENT '上传图片人数' ,
`keyNum`  int(10) NOT NULL COMMENT '钥匙数' ,
`entrustNum`  int(10) NOT NULL COMMENT '独家委托数' ,
`addMasterNum`  int(10) NOT NULL COMMENT '新增客户数' ,
`contactLandladyCount`  int(10) NOT NULL COMMENT '联系房东次数' ,
`shareHouseNum`  int(10) NOT NULL COMMENT '分享房源套数' ,
`forwardHouseCount`  int(10) NOT NULL COMMENT '转发房源次数' ,
`leadMasterNum`  int(10) NOT NULL COMMENT '带看客户数' ,
`createTime`  timestamp NULL DEFAULT NULL COMMENT '创建时间' ,
`updateTime`  timestamp NULL DEFAULT NULL COMMENT '更新时间' ,
`status`  tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;
