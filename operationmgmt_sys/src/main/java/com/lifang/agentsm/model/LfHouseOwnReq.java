package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class LfHouseOwnReq {
	private String name;
	private Integer houseId;
	private Integer publishagent;
	private String createTime;
	private Integer pub;
	private String realName;
	private String storeName;
	private Long id;
    public Integer storeId;
    private Integer operation;
    private String abbreviation;
    private String franchiseeId;
}
