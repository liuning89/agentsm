package com.lifang.agentsm.model.req;

import lombok.Data;

@Data
public class PriFunctionReq extends BaseReq {

	private String name;
	private String appName;
	/** 1菜单 2url*/
	private Byte type;
	
}
