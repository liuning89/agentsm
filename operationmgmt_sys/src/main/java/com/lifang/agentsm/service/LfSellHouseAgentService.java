package com.lifang.agentsm.service;

import com.lifang.agentsm.entity.ResourceTransferRequest;

public interface LfSellHouseAgentService {
	public void transferResource(ResourceTransferRequest resourceTransferRequest);
	
	public Integer[] isResourceTransfer(Integer userId);
}
