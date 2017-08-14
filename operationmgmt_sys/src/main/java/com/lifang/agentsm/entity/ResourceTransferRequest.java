package com.lifang.agentsm.entity;

import lombok.Data;

@Data
public class ResourceTransferRequest{
	private Integer oldAgentId;
	private Integer newAgentId;
	private Integer[] types;
}
