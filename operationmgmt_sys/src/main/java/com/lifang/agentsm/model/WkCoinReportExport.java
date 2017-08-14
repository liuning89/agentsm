package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class WkCoinReportExport {
    private String companyName;
    private String cityName;
    private String areaOrgName;
    private String storeName;
    private String agentName;
    private double xcCoin;
    private double giveCoin;
    private double payCoin;
    private double consumeCoin;
}
