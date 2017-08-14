package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class AgentReportModel {
	private long id;
	private long storeId;
	private long operator;//操作人
	private long agentId;
	private long shareCount;
}
