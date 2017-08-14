package com.lifang.agentsm.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Author:CK
 * 创建时间：2015年7月14日
 */
@Data
public class Employee implements Serializable {
    
	private Integer id;
	
	private String mobile;
	
	private String password;
	
	private Integer positionId;
	
	private String workNumber;
	
	private String name;
	
	private Integer cityId;
	
	private Byte department;
	
	private Byte status;
	
	private Date joinTime;
	
	private Byte positionStatus;
	
}

