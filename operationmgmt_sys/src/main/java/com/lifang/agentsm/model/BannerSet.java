package com.lifang.agentsm.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BannerSet {
    private Integer id;
    private String h5path;
    private String startTime;
    private String endTime;
    private String imageKey;
    private String description;
    private int status;
    private int isShow;
}
