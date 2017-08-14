package com.lifang.agentsm.model;

import java.util.Date;

import lombok.Data;
@Data
public class CompanyInfoModel {
	 	private Integer id;

	    private String companyName;
	    
	    private Integer companyId;

	    private Integer cityId;
	    
	    private String city;

	    private String mobile;

	    private String address;

	    private String mono;

	    private Byte status;

	    private String createTimeStr;

	    private Date updateTime;
	    
	    private String companyProfile;
	    
	    private String userName;
}
