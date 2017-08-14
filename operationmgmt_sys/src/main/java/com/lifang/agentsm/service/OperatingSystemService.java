package com.lifang.agentsm.service;
import java.util.List;
import java.util.Map;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.AgentWkCoinResultModel;
import com.lifang.agentsm.model.AgentWkCoinSearchModel;
import com.lifang.model.PageResponse;

public interface OperatingSystemService {
	public Map<String, Object> queryAgentWKCoinRewardList(AgentWkCoinSearchModel searchMode,LfEmployee employee);
	
	public PageResponse<List<AgentWkCoinResultModel>> queryAgentWKCoinSurplusList(AgentWkCoinSearchModel searchModel,LfEmployee employee);
	
	public PageResponse<String> getAgentWKCoinSurplusTotal(AgentWkCoinSearchModel searchModel,LfEmployee employee);
	
	public List<AgentWkCoinResultModel> agentWkCoinSurplusExport(AgentWkCoinSearchModel searchModel,LfEmployee employee);
}
