package com.lifang.bzsm.console.entity;

import java.util.Date;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

@Data
public class LfBzAgentReport
{
    private Long id;
    private Integer cityOrgId;
    private String cityOrgName;
    private Integer areaOrgId;
    private String areaOrgName;
    private Integer storeOrgId;
    private String storeOrgName;
    private String orgCode;
    private Integer agentId;
    private String agentName;
    private String agentMobile;
    private Date statisticsDate;
    private String weekDay;
    private Integer orderSuccess;
    private Integer orderFailed;
    private Integer willCustomer;
    private Integer houseSeeFirst;
    private Integer houseSeeSecond;
    private Integer newCustomerWK;
    private Integer newCustomerNet;
    private Integer newCustomerOther;
    private Integer housePublish;
    private Integer houseKey;
    private Integer houseEntrust;
    private Integer houseImage;
    private Integer houseMaintain;
    private Integer followHouse;
    private Integer followCustomer;
    private Integer followCustomerN;
    private Integer shareTotal;
    private Integer shareHouse;
    private Integer shareHouseCount;
    private Integer agentRegist;
    private Integer opinionHight;
    private Integer opinionLow;
    private Date createTime;
    private Date updateTime;
    private Integer status;
    private Integer orderCreate;
    private Integer orderEdit;
    private Integer dayRegistTotal;
    private Integer dayShareTotal;
    private Integer orderAllocation;
    private Integer orderGrab;
    private Integer orderTotal;

    private Integer agentStroke; //行程量
    private Integer houseSeeHCount; //带盘
    private Integer houseSeeCCount; //带客
    private Integer orderAllocationNew; //派单新
    private Integer orderAllocationOld; //派单旧
    private Integer orderCustomer; //接单客户总数
    private Integer orderAllocationHand; //人工派单

    private Integer dataTotalCount; //数据总条数
    
    private Integer invalidReview;//无效审核数量

}