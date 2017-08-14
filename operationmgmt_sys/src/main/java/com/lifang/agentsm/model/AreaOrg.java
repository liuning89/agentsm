package com.lifang.agentsm.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * 组织架构
 *
 * @author   Yang'ushan
 * @Date	 2015年8月3日		下午6:37:42
 *
 * @see
 */
@Data
public class AreaOrg {

	private Integer id;
	private Integer cityId;
	private Integer districtId;
	private Integer townId;
	private String name;
	private String areaName;
	private Date createTime;
	private String memo;
	private Integer level;
	private Integer parentId;
	private String parentName;
	private String cityName;
	private String townName;
	private String code;
	private Byte type;
	private String longitude;
	private String latitude;
	private Integer franchiseeId;
	private String franchiseeName;
	
	private Integer cityAreaOrgId;
	private String cityAreaOrg;
	
	/** 板块列表,以,拼接*/
	private String townNames;
	
}
