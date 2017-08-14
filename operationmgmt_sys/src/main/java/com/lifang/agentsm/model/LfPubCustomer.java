package com.lifang.agentsm.model;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class LfPubCustomer {
    private Long id;

    private String customerName;

    private String customerMobile;

    private Integer customerGender;

    private String customerReq;

    private Integer houseSeeCount;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
    private Date lastSeeTime;

    private Integer sourceFrom;

    private Integer storeId;

    private Integer areaId;

    private Integer status;

    private Integer customerType;

    private Date createTime;

    private Date updateTime;

    private Integer claimAgentId;

    private Date claimTime;

    private Integer changeType;
    
    private String mobile;
    private String houseSeeId;
    private String followUpId;
    private String cusGuestId;
    private String bidOrderId;
    private String customerId; 
    private String agentId;
    private String name;
    /**
     * gender:性别
     */
    private String gender;
    /**
     * source:来源
     */
    private String source;//
    private String isCallBackCus;
    /**
     * cusCreateTime:客户创建时间
     */
    private Date cusCreateTime;

}