package com.lifang.agentsm.model;

import lombok.Data;

import java.util.Date;

@Data
public class HouseCountTown {
	private Integer cityId;//城市id
	private String cityName;
	private Integer areaId;//区域id
	private String areaName;
	private Integer townId;//板块id
	private String townName;
	private Integer sellcount;//有效房源数
	private Integer auditcount;//待审批下架房源数
	private Integer picturecount;//实景房源数
	private Integer videocount;//视频房源数
	private Integer sellPointcount;//描述数
	private Integer commcount;//速消
	private Integer keycount;//钥匙
	private Integer houseStatecount;//无效房源
	private Date createTime;
	private Date updateTime;


}
