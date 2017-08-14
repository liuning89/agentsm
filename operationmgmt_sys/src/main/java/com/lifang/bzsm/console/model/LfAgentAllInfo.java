package com.lifang.bzsm.console.model;

import lombok.Data;

@Data
public class LfAgentAllInfo {
	private Long id;
	private String workNumber;//工号
    private String realName;
    private String mobile;
    private Byte gender;//性别 1男 2女
    private int isCrownAgent;//是否皇冠经纪人,1-皇冠经纪人，2-普通经纪人
    private int highPercentage; //好评数
    private int highPercentageTotal;//评论总数
    private int level;
}
