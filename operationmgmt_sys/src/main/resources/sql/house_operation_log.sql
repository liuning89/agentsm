CREATE TABLE house_operation_log (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`houseId`  int(11) NOT NULL COMMENT '房源ID' ,
`empId`  int(11) NULL DEFAULT NULL COMMENT '操作人ID' ,
`opType`  tinyint(50) NOT NULL COMMENT '操作类型 1.设置无效 2.设置有效' ,
`memo` varchar(2000) NULL DEFAULT NULL COMMENT '日志描述',
`status`  tinyint(4) NULL DEFAULT 0 COMMENT '是否可用 ：0默认，1正常' ,
`createTime`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ,
`updateTime`  timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='房源管理日志表'
ROW_FORMAT=COMPACT
;