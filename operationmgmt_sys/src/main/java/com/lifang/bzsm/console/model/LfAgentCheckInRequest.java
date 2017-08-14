package com.lifang.bzsm.console.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class LfAgentCheckInRequest {
    @DateTimeFormat(pattern="yyyy-MM-dd")  
    private Date dateStart;
    @DateTimeFormat(pattern="yyyy-MM-dd")  
    private Date dateEnd;
    
    private Integer pageIndex;
    private Integer pageSize;
    
    
    private Integer cityId;
    private Integer areaId;
    private Integer storeId;
    private String orgCode;
    private String agentMobile;
    private Integer agentId;
    
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
    private Date createTimestart;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTimeend;

    private String queryType;

    private String sortField;
    private String sortOrder;


    private String agentStr;

    private Integer areaOrgType;

    List<Integer> storeIds;
    
}
