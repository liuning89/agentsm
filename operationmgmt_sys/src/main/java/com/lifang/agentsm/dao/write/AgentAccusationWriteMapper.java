package com.lifang.agentsm.dao.write;

import java.util.Map;


/**
 * 举报dao
 * @author luogq
 *
 */
public interface AgentAccusationWriteMapper {

	void updateInform(Map<String, Object> pars);

	void deleteInform(String id);

}