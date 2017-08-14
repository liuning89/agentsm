package com.lifang.bzsm.console.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by admin on 2015/9/25.
 */

@Data
public class BusinessReport {
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
    private Double houseSeeSum;
    private Double newCustomersum;
    private Double housesum;
    private Double houseSeeAvg;
    private Double newCustomerAvg;
    private Double housePublishAvg;
    private Double houseKeyAvg;
    private Double houseImageAvg;
    private Double houseEntrustAvg;

    //行程量
    private Integer agentStroke;
    // 带盘
    private int houseSeeHCount;
    // 带客
    private int houseSeeCCount;
    // 派单新
    private int orderAllocationNew;
    //派单旧
    private int orderAllocationOld;
    // 接单客户总数
    private int orderCustomer;
    // 人工派单
    private int orderAllocationHand;
    //出价总数
    private  int priceTotal;

    private Integer dataTotalCount; //数据总条数

    private int  invalidReview ;

}