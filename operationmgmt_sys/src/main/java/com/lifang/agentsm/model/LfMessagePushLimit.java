package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class LfMessagePushLimit {

    private int id;
    private int pushId;
    private int companyId;
    private String companyName;
    private Integer cityId;
    private String cityName;
    private int status;
}
