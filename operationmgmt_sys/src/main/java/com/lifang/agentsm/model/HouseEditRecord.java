package com.lifang.agentsm.model;

import java.util.Date;

import lombok.Data;

@Data
public class HouseEditRecord {
    private Integer houseId;
    private Integer agentId;
    private Integer otherOperation;
    private int editType;//8.朝向 9.装修 10.楼层 11.卖点
    private String before;
    private String after;
    private Date createTime;
}
