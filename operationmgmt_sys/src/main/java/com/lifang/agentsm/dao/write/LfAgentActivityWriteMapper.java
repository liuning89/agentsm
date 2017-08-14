package com.lifang.agentsm.dao.write;

import java.util.Map;

import com.lifang.agentsm.entity.LfAgentActivity;

public interface LfAgentActivityWriteMapper {

	void updateLfAgentActivity(Map<String, Object> pars);

	void insertAgentActivity(LfAgentActivity laa);

	void updateAgentActivity(LfAgentActivity la);
	
	void updateByPrimaryKeySelective(LfAgentActivity la);
}