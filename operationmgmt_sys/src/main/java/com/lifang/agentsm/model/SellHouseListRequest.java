package com.lifang.agentsm.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * Author:CK
 * 创建时间：2015年5月29日
 */
@Data
public class SellHouseListRequest {
	/**
	 * 城市编号
	 */
	private Integer cityId;
	
	
	private String estateName;
	
	private Integer estateId;
	
	/**
	 * 楼栋号id
	 */
	private Integer buildingId;
	
	
	private String room;
	
	
	
	/**
	 * 发布时间起始时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTime;
	
	
	/**
	 * 发布时间结束时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endTime;
	
	/**
	 * 房源状态，-1：全部，1：有效，0：无效
	 */
	private Integer status;
	
	/**
	 * 经纪人类型，0：全部，1：委托人，2：钥匙人，3：发布人，4：实景人
	 */
	private String agentType;
	
	/**
	 * 门店id，为0时，表示忽略此条件
	 */
	private Integer store;
	
	/**
	 * 经纪人id，为0时，表示忽略此条件
	 */
	private Integer agent;
	
	/**
	 * 当前页面（从0开始）
	 */
	private Integer pageIndex;
	
	private Integer pageSize;
	
	private String sortField;
	
	private String sortOrder;
	
	private Integer houseId;
	
	private Integer isFiveYears;
	private Integer isOnlyOne;
	private Integer publisher;
	private String mobileOrName;
	private String hostMobile;
	
    private String startSpaceArea;
    private String endSpaceArea;
    private String startSellPrice;
    private String endSellPrice;
    private String applyInvalid;
    private String companyId;
    private String auditStatus;
    private String hasAgent;
}

