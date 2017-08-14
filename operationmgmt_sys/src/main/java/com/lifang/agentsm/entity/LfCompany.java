package com.lifang.agentsm.entity;

import java.util.Date;

import lombok.Data;
@Data
public class LfCompany {
    private Integer id;

    private String companyName;

    private Integer cityId;

    private String mobile;

    private String address;

    private String mono;

    private Byte status;

    private Date createTime;

    private Date updateTime;
    
    private String companyProfile;
    
    private String userName;

    
}