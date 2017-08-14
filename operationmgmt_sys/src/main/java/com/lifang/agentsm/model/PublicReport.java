package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class PublicReport {

    /**
     * reportDate:报表日期
     */
	private String reportDate;
	private String cityName;
    private String areaName;
    private String storeName;
    private String agentName;
	
	
	/**
	 * areapub:区公客数
	 */
	private Integer areapub;
	/**
	 * storepub:店公客数
	 */
	private Integer storepub;
	
	/**
	 * arearl:区公客认领数
	 */
	private  Integer arearl;
	/**
	 * viewarea:区公客查看数
	 */
	private Integer viewarea;
		
	/**
	 * storerl:店公客认领数
	 */
	private Integer storerl;
	/**
	 * viewstore:店公客查看数
	 */
	private Integer viewstore;
	/**
	 * pubcusid:关联功课表的id
	 */
//	private Integer pubcusid;
	private Integer createTime;
	private Integer updateTime;
	
//	private Integer id;
    
	/**
     * agentId:经纪人id
     */
    private String agentId;
}
