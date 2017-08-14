package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class WkCoinGiveExport {
    private String companyName;
    private String cityName;
    private String areaOrgName;
    private String storeName;
    private String agentName;
    private int coinNum;
    private String createTime;
    private String operatorName;
    private String giveReason;
}
