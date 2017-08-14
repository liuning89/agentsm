package com.lifang.agentsm.model;

import java.util.Date;

import lombok.Data;
@Data
public class LfFranchiseeInfo {
    private Long id;

    private String cityName;
    private String cityId;

    private String townName;
    private String townid;

    private String abbreviation;

    private String companyname;

    private String corporate;

    private String corporatephone;

    private String partner1;

    private String partnerphone1;

    private String partner2;

    private String partnerphone2;
    
    private String bd;
    private String bdphone;
    private String bp;
    private String bpphone;
    

    private String address;

    private Date cooperationstart;

    private Date cooperationend;

    private String isseed;

    private String deposit;

    private String brandcost;

    private String cooperationcost;

    private String status;

    private Date updateTime;

    private Long createBy;

    private Date createTime;

    private String tid;
    
//    private List<FranchiseeArea> fAreasList;
    private String areaId;
    private String areaName;
    /**
     * ispush:是否推送
     */
    private String ispush;
    /**
     * 合作伙伴手机号
     */
    private String franchiseePhone;
    
}