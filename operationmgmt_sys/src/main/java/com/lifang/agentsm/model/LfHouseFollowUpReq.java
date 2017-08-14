package com.lifang.agentsm.model;
import java.util.Date;

import lombok.Data;

/**
 * @author lohocc
 * @Date 2015年5月28日
 */
@Data
public class LfHouseFollowUpReq {
	private Integer houseId; 
	private Date createTime;
	private String type;
	private String note;
	private String realName;
	private String storeName;
	private Integer pageIndex;
	private Integer pageSize;
	private String storeId;//门店ID
	private String cityId;//城市
	private String agentId;//经纪人id
	private String areaId;//区域ID
	private String queryTimeBegin;//开始时间
	private String queryTimeEnd;//结束时间
	
	private String address;
    private String cityCode;
    private String areaCode;
    private String storeCode;
    private String orgCode;
    private Integer isResearch;
	
}
