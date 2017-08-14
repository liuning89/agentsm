package com.lifang.agentsm.model;

import java.util.Date;

import lombok.Data;

@Data
public class LfAgentLogListInfo {
	
 
	private Date createTime; 
	
	private String name;//

    private String before;//操作前

    private String  after;//操作后

    private String editType;//操作内容

    private String storeName;//mendian

	private String positionid;

	private String agentId;
	
	/**
	 * houseId:
	 */
	private String houseId;
	
  
}
