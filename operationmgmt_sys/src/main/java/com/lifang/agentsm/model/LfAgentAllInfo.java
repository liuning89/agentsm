package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class LfAgentAllInfo {
	private Long id;

	private Long cityId;

    private Long companyId;

    private Long storeId;

    private String realName;

    private String password;

    private String mobile;

    private String idNumber;//身份证

    private String photoHeadUrl;

    private Byte status;//0 审核中 1 审核成功(在职)  2 审核失败 3 删除  4 离职

    private Byte gender;//性别 1男 2女

    private String createTime;

	private String companyName;//关联查询出来的公司名
    private String cityName;//所属城市名
    private String storeName;//所属门店钻
    private int isCrownAgent;//是否皇冠经纪人,1-皇冠经纪人，2-普通经纪人
    private String agentNum;//工号
    
    private int highPercentage; //好评数
    private int highPercentageTotal;//评论总数
    private int level;
}
