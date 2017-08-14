package com.lifang.bzsm.console.model;

import com.lifang.bzsm.console.entity.LfCustomerRequirement;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class LfCustomerRequirementInfo extends LfCustomerRequirement{
    private String estateName;
    private String townName;
    private String areaName;
}