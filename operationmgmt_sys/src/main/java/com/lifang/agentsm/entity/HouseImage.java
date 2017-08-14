package com.lifang.agentsm.entity;

import java.util.Date;

import lombok.Data;

@Data
public class HouseImage {
    private Long id;

    private Integer houseId;

    private Integer type;

    private String imgKey;

    private String desc;

    private Integer status;

    private Date takePhotoTime;

    private Date createTime;

    private Date updateTime;

    private Integer sourceId;

    private Long agentId;
    
    private Long guestId;
 
}