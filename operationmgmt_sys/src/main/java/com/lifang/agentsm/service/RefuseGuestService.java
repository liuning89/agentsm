package com.lifang.agentsm.service;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.Employee;

public interface RefuseGuestService {
	
	
	/**
	 * 
	 * @param agent
	 * @param img
	 * @return
	 */
	public int insertRefuseGuest(Map<String, Object> pars);

	public List<LfEmployee> selectById(Map<String, Object> pars);

	public void updateLfEmp(Map<String, Object> pars);

	public int selectPhoneCount(Map<String, Object> pars);

    public Employee getEmployeeByMobile(String mobile);
	
}
