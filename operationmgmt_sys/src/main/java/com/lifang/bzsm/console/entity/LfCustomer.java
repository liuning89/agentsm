package com.lifang.bzsm.console.entity;

import lombok.Data;

import java.util.Date;


@Data
public class LfCustomer {

    private Long id;

    private String name;

    private Byte gender;

    private String mobile;

    private Byte type;

    private Integer cityId;

    private Byte source;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    private Long agentId;

    private Byte cusLevel;

    private Date birthDay;

    private String tempSource;

    private Integer tempId;

    private Integer isCallBackCus;

    private Byte bonus;

    private Byte wuKongWay;

}