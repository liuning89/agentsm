package com.lifang.agentsm.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * Author:CK
 * 创建时间：2015年5月29日
 */
@Data
public class SellHouseListModel {
	private Integer houseId;
	
	private Integer subEstateId;
	
	private String estateName;
	
	private String buildingName;
	
	private String room;
	
	private String huxing;
	
	private String area;
	
	private String price;
	
	private String publisher;
	
	private String publishDate;
	
	private Byte status;
	private String unitPrice;//单价
	private String gefuPrice;
	private String initName;
	private Integer cityId;
	private Integer agentId;
	private String storeName;
	private String companyName;
}

