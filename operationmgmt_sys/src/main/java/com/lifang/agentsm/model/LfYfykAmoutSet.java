package com.lifang.agentsm.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class LfYfykAmoutSet {

    private int id;
    private int wkCoinDenomination;
    private int price;
    private int agentId;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private String createTime;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateTime;
    private String yfykStatus;
    private String startTime;
    private String endTime;
    private String agentName;
    private String agentIds;
    
}
