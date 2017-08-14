package com.lifang.agentsm.model;

import java.util.Date;

import lombok.Data;
@Data
public class LfResourceTransLog {
    private Long id;

    private Integer type;

    private Long resourceId;

    private Long oldAgentId;

    private Long newAgentId;

    private Long createBy;

    private Date createTime;

    
}