package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class LfAgentFeeSet {
    private int id;
    private String type;
    private int consumecoin;
    private int agentId;
    private String createTime;
    private String agentName;

}
