package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class LfAuditInvalid {
    private int agentId;
    private String content;
    private String createTime;
    private String agentName;

}
