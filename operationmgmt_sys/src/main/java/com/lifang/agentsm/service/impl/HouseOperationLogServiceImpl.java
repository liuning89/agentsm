/**
 * 
 */
package com.lifang.agentsm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.write.HouseOperationLogWriteMapper;
import com.lifang.agentsm.model.HouseOperationLog;
import com.lifang.agentsm.service.HouseOperationLogService;

/**
 * @author lohocc
 * @Date 2015年6月24日
 */
@Service
public class HouseOperationLogServiceImpl implements HouseOperationLogService {
	@Autowired
	private HouseOperationLogWriteMapper houseOperationLogWriteMapper;
	/* (non-Javadoc)
	 * @see com.lifang.agentsm.service.HouseOperationLogService#insertHouseOperationLog(com.lifang.agentsm.model.HouseOperationLog)
	 */
	@Override
	public int insertHouseOperationLog(HouseOperationLog operationLog) {
		return houseOperationLogWriteMapper.insertHouseOperationLog(operationLog);
	}

}
