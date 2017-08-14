package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class LfYfykAmoutSetLog {

    private int operatorId;
    private String content;
    private String beforeValue;
    private String afterValue;
    private int setId;
    private String type;
    private Integer pageIndex;
    private Integer pageSize; 
    private String agentName;
    private String createTime;
}
