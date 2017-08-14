package com.lifang.agentsm.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Config {
    private Integer id;

    private String sysName;

    private String ipValue;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private String remark;
}