package com.lifang.agentsm.model;

import lombok.Data;

/**
 * 
 * 经纪人资源转移
 *
 * @author   luogq
 * @Date	 2015年10月20日	
 *
 * @see
 */
@Data
public class AgentTransfer {
//keyAgent,pictureAgent,commAgent,publishAgent,maintainAgent,subEstateCount,customer,cussee 

	private String cityName;//城市
//	private String regionName;//行政区
	private String plate;//板块
	
//	private Integer keyAgent;//钥匙人
//	private Integer pictureAgent;//实景人
//	private Integer commAgent;//速销人
//	private Integer publishAgent;//发布人
//	
//	private Integer maintainAgent;//维护人
	private Integer subEstateCount;//小区数
//	private Integer customer;//客源
//	private Integer cussee;//未带看
	private Integer houseCount;//房源数
}
