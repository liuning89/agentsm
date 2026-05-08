package com.lifang.agentcommsoa.service;

import com.lifang.agentcommsoa.enums.RewardEnum;
import com.lifang.agentcommsoa.util.ResponseStatus;

public interface AgentCommonService {
    void rewardWuKongCoin(int agentId, RewardEnum rewardEnum, int houseId);
    ResponseStatus clearAgentCache(int agentId);
}
