/**
 * 
 */
package com.lifang.agentsm.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.log.LogAgentWriteMapper;
import com.lifang.agentsm.dao.read.RefuseGuestReadMapper;
import com.lifang.agentsm.dao.write.RefuseGuestWriteMapper;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.Employee;
import com.lifang.agentsm.service.RefuseGuestService;

/**
 * @author luogq
 * @Date 2015年7月20日
 */
@Service
public class RefuseGuestServiceImpl implements RefuseGuestService {

	@Autowired
	private RefuseGuestWriteMapper rwrite;
	@Autowired
	private RefuseGuestReadMapper rread;
	@Autowired
	private LogAgentWriteMapper logAgentWrite;
	@Override
	public int insertRefuseGuest(Map<String, Object> pars) {
		return rwrite.inserRefuseGuest(pars);
	}
	@Override
	public List<LfEmployee> selectById(Map<String, Object> pars) {
		
		return rread.selectById(pars);
	}
	@Override
	@Transactional
	public void updateLfEmp(Map<String, Object> pars) {
		//修改lf_agent表
		rwrite.updateLfEmp(pars);
		//插入一条记录到agentapp_device_unlock 表

		logAgentWrite.inserLfAppDeviceUnlock(pars);
	}
	@Override
	public int selectPhoneCount(Map<String, Object> pars) {
		return logAgentWrite.selectPhoneCount(pars);
	}
    @Override
    public Employee getEmployeeByMobile(String mobile) {
        return rread.getEmployeeByMobile(mobile);
    }
	
}
