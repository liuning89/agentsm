package com.lifang.agentsm.model;

import java.util.List;

import lombok.Data;

@Data
public class WkCoinDetailModel {
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
    private String consumeDate;//消费时间
    private String businessType;//消费方式
    private String wuKongCoin;//充值悟空币额
    private String price;//交易金额
    private String platform;//充值方式
    private String payId;//交易流水
    private String createTime;//交易时间
    private Integer pageIndex;
    private Integer pageSize; 
    private String agentIds;
    private String startTime;
    private String endTime;


}
