package com.lifang.agentsm.entity;

import java.util.Date;

import lombok.Data;

@Data
public class LfAgentActivity {
	private Long id;

    private String activityName;

    private String activityImgUrl;

    private String activityLinkUrl;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Date endTime;
     
}