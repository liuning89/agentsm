package com.lifang.agentsm.dao.log;

import java.util.Map;


public interface LogAgentWriteMapper {
    void inserLfAppDeviceUnlock(Map<String, Object> pars);

	int selectPhoneCount(Map<String, Object> pars);


}