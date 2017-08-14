package com.lifang.agentsm.model.req;

import lombok.Data;

@Data
public class AreaOrgReq extends BaseReq {

	private String cityName;
	private String areaName;
	private String name;
	private Integer cityId;
	private Integer districtId;
	private Integer townId;
	private Integer page;
	private Byte type;
	private String franchiseeName;
	
}
