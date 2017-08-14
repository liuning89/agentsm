package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class TravelAllowanceSet {
    private int id;
    private int cityId;
    private String cityName;
    private String updateTime;
    private int status;
    private int  publishHouse;
    private int imageNotNow;
    private int imageNow;
    private int videoNotNow;
    private int videoNow;
    private int favorableComment;
    private int takeHouse;
    private int invalidHouse;
    private String typeName;
    private Integer pageIndex;
    private Integer pageSize;
    private int type;
    private int offset;
}
