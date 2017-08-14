package com.lifang.agentsm.service;

import com.lifang.agentsm.entity.LfAgent;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.HouseCountTown;
import com.lifang.agentsm.model.LfAgentAllInfo;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.bzsm.console.model.AgentRequest;
import com.lifang.model.PageResponse;

import java.util.List;
import java.util.Map;

public interface HouseDataReport {


	Map<String, Object> getHouseDataTown(Map<String, Object> pars);
}
