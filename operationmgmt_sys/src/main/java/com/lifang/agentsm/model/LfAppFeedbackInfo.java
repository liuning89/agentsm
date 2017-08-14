package com.lifang.agentsm.model;

import java.util.Date;
import lombok.Data;

@Data
public class LfAppFeedbackInfo {
    //意见反馈
    private Long id;
    private Long agentId;
    private String title;
    private String context;
    private String imgKey;
    private Date createTime;
    private Byte status;

    //经纪人
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
    private Byte accountType;
    private Byte teamType;
    private Byte levelType;
    private Long uid;
    private Byte gender;
    private Byte bizType;
    private Date updateTime;
    private String memo;
    private Integer sourceId;
    private String dept_name;
    private String company_name;
    private String token;
    
    //门店
    private String storeName;
    private Integer districtid;
    private Integer townid;
    private String address;
    private String mono;
    
    //employee
    private Integer positionId;
    
}
