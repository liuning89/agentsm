package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class WkcoinReport {
    private String companyName;
    private Integer companyId;
    private String cityName;
    private Integer cityId;
    private String areaOrgName;
    private Integer areaId;
    private String storeName;
    private Integer storeId;
    private String agentName;
    private Integer agentId;
    private double xcCoin;
    private double giveCoin;
    private double payCoin;
    private double consumeCoin;
    private Integer pageIndex;
    private Integer pageSize; 
    private int offset;
    private String agentIds;
    private String startTime;
    private String endTime;
    private int type;
    private String mobile;

}
