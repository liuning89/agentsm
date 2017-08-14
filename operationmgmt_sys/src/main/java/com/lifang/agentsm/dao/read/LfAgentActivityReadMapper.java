package com.lifang.agentsm.dao.read;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.LfAgentActivity;


public interface LfAgentActivityReadMapper {

	List<LfAgentActivity> selectByList();

	LfAgentActivity findById(Map<String, Object> pars);

   
}