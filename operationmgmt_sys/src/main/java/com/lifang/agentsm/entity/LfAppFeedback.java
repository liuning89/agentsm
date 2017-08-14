package com.lifang.agentsm.entity;

import java.util.Date;

import lombok.Data;

@Data
public class LfAppFeedback {
    private Long id;

    private Long agentId;

    private String title;

    private String context;

    private String imgKey;

    private Date createTime;

    private Byte status;

}