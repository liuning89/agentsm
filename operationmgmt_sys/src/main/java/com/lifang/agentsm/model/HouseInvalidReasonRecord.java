package com.lifang.agentsm.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class HouseInvalidReasonRecord {
    
    private int id;
    private int houseId;
    private String reason;
    private String memo;
    private String createTime;
    private String agentName;
    private int agentId;
    private Integer pageIndex;
    private Integer pageSize;
    private String initName;
    private String estateName;
    private Integer cityId;
    /**
     * 起始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    
    
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;

}
