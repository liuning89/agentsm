package com.lifang.agentsm.model;

import java.util.List;

import lombok.Data;

@Data
public class WkCoinGivelModel {
    private int id;
    private Integer agentId;
    private int coinNum;
    private int operatorId;
    private String createTime;
    private String giveReason;
    private Integer pageIndex;
    private Integer pageSize; 
    private String agentIds;
    private String startTime;
    private String endTime;
    private String companyName;
    private Integer companyId;
    private String cityName;
    private Integer cityId;
    private String areaOrgName;
    private Integer areaId;
    private String storeName;
    private Integer storeId;
    private String agentName;
    private int agentCount;
    private String companyIds;
    private String operatorName;
    private String mobile;
}
