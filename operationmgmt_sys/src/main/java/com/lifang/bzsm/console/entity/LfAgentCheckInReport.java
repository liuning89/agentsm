package com.lifang.bzsm.console.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LfAgentCheckInReport {
    private Integer id;

    private Integer cityId;

    private String cityName;

    private Integer areaId;

    private String areaName;

    private Integer storeId;

    private String storeName;

    private Integer employeeId;

    private String employeeName;

    private String positionName;

    private String orgCode;

    private Date statisticsTime;

    private Date onTime;

    private Date outTime;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private Integer areaOrgType;

}