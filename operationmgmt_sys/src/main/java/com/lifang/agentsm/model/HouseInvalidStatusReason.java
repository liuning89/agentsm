package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class HouseInvalidStatusReason {
    private int houseId;
    private String memo;
    private String reason;

}
