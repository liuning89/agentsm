package com.lifang.agentsm.model;

import lombok.Data;
@Data
public class TransferResouceShow {

	private String townname;//板块名
	private Integer houseCount;//房源数
	private Integer estateCount;//小区数
	private Integer customerCount;//客源
	private Integer cusSeeCount;//未带看
	private Integer cusGuestCount;//专属经纪人
	private Integer houseSeeCount;//带看表
	private Integer requirementCount;//客户需求
	
	
}