package com.lifang.agentsm.service;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.model.AccusAtion;

public interface InformService {

	Map<String, Object> getInformList(Map<String, Object> pars);

	void updateInform(Map<String, Object> pars);

	void deleteInform(String id);
	
}
