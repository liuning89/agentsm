package com.lifang.agentsm.model;
import lombok.Data;

@Data
public class AgentWkCoinSearchModel {
    private String agentPhoneNumber;//经济人手机号
    private String startTime = "1900-01-01";
    private String endTime;
    private String rewardReson = "0";//奖励原因
    private Integer cityId;
    private Integer areaId;
    private Integer storeId;
    private Integer agentId;
    private Integer companyId;
    private Integer pageIndex;
    private Integer pageSize;
}
