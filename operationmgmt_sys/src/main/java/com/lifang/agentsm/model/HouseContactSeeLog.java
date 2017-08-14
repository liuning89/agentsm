package com.lifang.agentsm.model;

import java.util.Date;

import lombok.Data;
@Data
public class HouseContactSeeLog {

    private int id;
    private int agentId;
    private String agentName;
    private int houseId;
    private String houseContactTel;
    private Date createTime;
    private String ip;
}
