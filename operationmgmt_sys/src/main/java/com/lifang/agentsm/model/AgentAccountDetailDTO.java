package com.lifang.agentsm.model;

public class AgentAccountDetailDTO {
	private int id;
	private int agentId;
	public int getAgentId() {
		return agentId;
	}
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	private String createTime;
	private String orderName;
	private int wuKongCoin;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public int getWuKongCoin() {
		return wuKongCoin;
	}
	public void setWuKongCoin(int wuKongCoin) {
		this.wuKongCoin = wuKongCoin;
	}
}
