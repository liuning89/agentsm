package com.lifang.agentsm.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class LfAgentReportRequest {
    private Integer storeId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
    private Date createTimestart;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
    private Date createTimeend;
    private Integer pageIndex;
    private Integer pageSize;
    private String month;
    
}
