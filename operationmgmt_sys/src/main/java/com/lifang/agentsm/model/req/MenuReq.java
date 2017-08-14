package com.lifang.agentsm.model.req;

import lombok.Data;

@Data
public class MenuReq extends BaseReq {

	private String name;
	private String parentName;
	private String functionName;
	private Byte isLeaf;
	
}
