package com.lifang.agentsm.dao.read;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.Employee;

/**
 * 黑名单dao
 * @author luogq
 *
 */
public interface RefuseGuestReadMapper {

	List<LfEmployee> selectById(Map<String, Object> pars);

    Employee getEmployeeByMobile(String mobile);

    
   

}