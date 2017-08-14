CREATE TABLE `lf_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `companyName` varchar(100) NOT NULL COMMENT '公司名称',
  `cityId` int(11) NOT NULL COMMENT 'cityId',
  `mobile` varchar(100) DEFAULT NULL COMMENT '公司电话',
  `address` varchar(200) DEFAULT NULL COMMENT '公司地址',
  `mono` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否可用 ：1正常，2 删除',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `userName` varchar(20) NOT NULL COMMENT '联系人',
  `companyProfile` varchar(50) NOT NULL COMMENT '公司简介',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=997 DEFAULT CHARSET=utf8 COMMENT='加盟公司表';

CREATE TABLE `lf_employee` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `loginName` varchar(20) DEFAULT NULL COMMENT '登录名',
  `passWord` varchar(100) DEFAULT NULL COMMENT '密码',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '状态  1有效 2无效',
  `grade` int(10) NOT NULL DEFAULT '0' COMMENT '权限等级 预留字段',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='经纪人pc登陆用户表';

CREATE TABLE `lf_store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `storeName` varchar(100) NOT NULL COMMENT '门店名称',
  `companyId` int(11) NOT NULL COMMENT 'companyId',
  `cityId` int(11) NOT NULL DEFAULT '0' COMMENT 'cityId',
  `districtid` int(11) NOT NULL DEFAULT '0' COMMENT '行政区ID',
  `townid` int(11) NOT NULL DEFAULT '0' COMMENT '板块ID',
  `mobile` varchar(100) DEFAULT NULL COMMENT '公司电话',
  `address` varchar(200) DEFAULT NULL COMMENT '公司地址',
  `mono` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否可用 ：1正常，2 删除',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=966 DEFAULT CHARSET=utf8 COMMENT='门店表';
--状态字段增加注释
ALTER TABLE lf_agent MODIFY COLUMN `status` TINYINT(4) DEFAULT '0' COMMENT '0 审核中 1 审核成功  2 审核失败 3 删除 4 离职';

ALTER TABLE lf_company ADD UNIQUE INDEX(`companyName`,`cityId`);
ALTER TABLE lf_store ADD UNIQUE INDEX(`storeName`,`companyId`);
ALTER TABLE lf_employee add unique index(`loginName`);
--ALTER TABLE lf_agent ADD UNIQUE INDEX(`mobile`);


