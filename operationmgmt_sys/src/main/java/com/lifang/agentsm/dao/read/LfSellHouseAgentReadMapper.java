package com.lifang.agentsm.dao.read;

import com.lifang.agentsm.entity.LfSellHouseAgent;

public interface LfSellHouseAgentReadMapper {
    LfSellHouseAgent selectByPrimaryKey(Long id);
    
   //keyAgent, publishAgent, commAgent, buyAgentId,,agentId
  	public Integer isKeyAgent(Integer keyAgent);
  	
  	public Integer isPublishAgent(Integer publishAgent);
  	
  	public Integer isCommAgent(Integer commAgent);
  	
  	public Integer isBuyAgentId(Integer buyAgentId);
  	
  	public Integer isPictureAgent(Integer pictureAgent);
  	
  	public Integer isHouseSeeAgentId(Integer agentId);
}