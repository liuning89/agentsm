package com.lifang.agentsm.base.model;

import lombok.Data;

@Data
public class WkCoinConsumeExport {

    private String companyName;
    private String cityName;
    private String areaOrgName;
    private String storeName;
    private String agentName;
    private String price;//交易金额
    private String createTime;//消费时间
    private String businessType;//消费方式
}
