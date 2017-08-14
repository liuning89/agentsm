package com.lifang.agentsm.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class FeedBackRequest
{
    private int pageIndex;
    private int pageSize;
    
    private String cityCode;
    private String areaCode;
    private String storeCode;
    private String orgCode;
    
    private Integer agentId;
    private String mobile;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")  
    private Date createTimestart;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")  
    private Date createTimeend;
}
