package com.lifang.bzsm.console.model;

import lombok.Data;

import java.util.List;

@Data
public class AgentRequest {
    private Integer cityId;
    private Integer areaId;
    private Integer storeId;
    private String agentName;
    private Integer agentId;

    private Integer pageIndex;
    private Integer pageSize;
    private String orgCode;


    private List<Integer> agentIds;
}
