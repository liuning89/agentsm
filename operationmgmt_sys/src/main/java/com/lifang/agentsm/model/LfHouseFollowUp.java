package com.lifang.agentsm.model;

import lombok.Data;

/**
 * @author lohocc
 * @Date 2015年5月28日
 */
@Data
public class LfHouseFollowUp {
	private String createTime;
	private String type;
	private String note;
	private String realName;
	private String storeName;
	private Integer id;
	private Integer houseId;
	private String agentName;
	private String positionId;
	private String initname;//房屋地址
	private String cityId;
	private String agentId;
	private String shield;//是否屏蔽
}
