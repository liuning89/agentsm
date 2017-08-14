package com.lifang.agentsm.dao.write;

import com.lifang.agentsm.model.HouseOperationLog;

/**
 * @author lohocc
 * @Date 2015年6月24日
 */
public interface HouseOperationLogWriteMapper {
	/**
	 * @param operationLog 日志对象
	 * @return 插入记录数
	 */
	public int insertHouseOperationLog(HouseOperationLog operationLog);
}
