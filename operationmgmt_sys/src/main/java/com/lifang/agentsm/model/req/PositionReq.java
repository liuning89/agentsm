package com.lifang.agentsm.model.req;

import lombok.Data;

@Data
public class PositionReq extends BaseReq {

	private String name;
	private String areaOrg;
	private String parentName;
	private Byte department;
	private Integer cityId;
	private Byte isShowCountry;
	
}
