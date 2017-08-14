package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class RulePublic {

	private Integer id;
	

	
	private String publicToStore;
	private String storeToArea;
	
	private String storeCount;
	private String areaCount;
	
	private  String viewStoreCount;
	private String viewAreaCount;
		
}
