package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class StoreInfoModel {
	 	private Integer id;

	    private String storeName;

	    private Integer companyId;
	    
	    private String companyName;

	    private Integer cityId;
	    
	    private String city;

	    private Integer districtid;
	    
	    private String district;

	    private Integer townid;
	    
	    private String town;

	    private String mobile;

	    private String address;

	    private String mono;

	    private Byte status;

	    private String createTimeStr;

}
