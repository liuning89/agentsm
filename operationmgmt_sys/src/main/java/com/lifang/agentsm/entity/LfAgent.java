package com.lifang.agentsm.entity;

import java.util.Date;

import lombok.Data;

@Data
public class LfAgent {
    private Long id;

    private Long cityId;

    private Long companyId;

    private Long storeId;

    private String realName;

    private String password;

    private String mobile;

    private String idNumber;

    private String photoHeadUrl;

    private String photoIdUrl;

    private String photoCardUrl;

    private Byte status;

    private Byte accountType;

    private Byte teamType;

    private Byte levelType;

    private Long uid;

    private Byte gender;

    private Byte bizType;

    private Date createTime;

    private Date updateTime;

    private String memo;

    private Integer sourceId;

    private String dept_name;

    private String company_name;

    private String token;

    private Integer isCrownAgent;
    
    private String agentNum;
}