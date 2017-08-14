package com.lifang.bzsm.console.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LfCustomerHouseSee {
    private Long id;

    private Long customerId;

    private Long agentId;

    private Date seeTime;

    private String agreementKey;

    private String agreementNo;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private Long houseId;

    private Byte addType;

    private String mark;
}