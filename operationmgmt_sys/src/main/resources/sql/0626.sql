CREATE TABLE `house_image_audit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `houseId` int(11) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '描述 1, 首图;2, 客厅;3, 卧室;4, 厨房;5, 卫生间;6, 阳台;7, 房型图;8, 外观图',
  `imgKey` varchar(255) NOT NULL DEFAULT '' COMMENT 'OSS服务器key',
  `desc` varchar(255) DEFAULT NULL COMMENT '照片描述',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1有效,0 无效',
  `takePhotoTime` datetime DEFAULT NULL COMMENT '拍摄时间',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `sourceId` int(11) NOT NULL DEFAULT '0' COMMENT 'house_res_info表res_id',
  `agentId` bigint(20) DEFAULT NULL COMMENT '上传图片的经纪人',
  audit_status tinyint(4) NOT NULL DEFAULT '0' COMMENT '0未审核,1 审核通过,2审核不通过',
  employee_id int(11) COMMENT '审核人 lf_employee表id',
  audit_time datetime   COMMENT '审核时间',
  PRIMARY KEY (`id`)

) comment='实景图片审核表';