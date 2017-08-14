package com.lifang.agentsm.base.model;

import lombok.Data;

@Data
public class WkCoinPayExport {
    private String companyName;
    private String cityName;
    private String areaOrgName;
    private String storeName;
    private String agentName;
    private String wuKongCoin;//充值悟空币额
    private String price;//交易金额
    private String platform;//充值方式
    private String payId;//交易流水
    private String createTime;//交易时间

}
