package com.lifang.agentsm.model;

import java.util.Date;

import lombok.Data;

@Data
public class Position {

	private Integer id;
	private String name;
	private String areaOrg;
	private Integer areaOrgId;
	private Integer parentId;
	private String parentName;
	private String memo;
	private Date createTime;
	private String cityName;
	private Byte department;
	private Integer cityId;
	private String code;
	private Integer level;
	private Byte status;
	
}
