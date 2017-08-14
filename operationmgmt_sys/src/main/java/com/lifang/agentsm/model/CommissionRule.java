package com.lifang.agentsm.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by admin on 2015/10/20.
 */

@Data
public class CommissionRule {

    private int id;
    private String role;
    private float distpercent;
    private int islocked;
    private Date createTime;
    private int createby;
    private String status;
    private String _state;
    private Float commission;
    private int updateby;
}
