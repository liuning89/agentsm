package com.lifang.agentsoa.facade;

import java.util.List;
import com.lifang.agentsoa.model.Agent;
import com.lifang.agentsoa.model.AreaOrg;
import com.lifang.agentsoa.model.AreaOrgModel;
import com.lifang.agentsoa.model.Employee;
import com.lifang.agentsoa.model.PositionInfo;
import com.lifang.agentsoa.model.enums.SystemInfo;

public interface AgentSOAServer {
    Agent getAgent(int agentId);
    Agent getAgent(String mobile);
    List<Agent> getAgentList(Integer cityId, Integer areaId, Integer storeId, String code, Integer townId);
    List<Agent> getAgentList(Integer cityId, Integer areaId, Integer storeId, Integer status, Integer townId, Integer companyId);
    List<Agent> getAgentList(Integer cityId, Integer areaId, Integer storeId, Integer status, Integer townId, Integer companyId, String agentIds, String mobile, String companyName, Integer pageIndex, Integer pageSize);
    Integer getAgentListCount(Integer cityId, Integer areaId, Integer storeId, Integer status, Integer townId, Integer companyId, String agentIds, String mobile, String companyName);
    List<Agent> getAgentsByMobiles(List<String> mobiles);
    List<Agent> getAgentsByIds(List<Integer> ids);
    List<Agent> getAgentInfoByIds(List<Integer> ids);
    List<Agent> getAllAgentList(Integer cityId, Integer areaId, Integer storeId, Integer status, Integer townId, Integer companyId);
    Employee getEmployee(int employeeId);
    Employee getEmployee(int employeeId, SystemInfo systemInfo);
    List<Employee> getEmployee(List<Integer> employeeIds, SystemInfo systemInfo);
    List<Employee> getEmployeeByName(String name);
    PositionInfo getEmployeePositionInfo(int employeeId, SystemInfo systemInfo);
    AreaOrgModel getEmployeeAreaOrgsMap(int employeeId, SystemInfo systemInfo);
    AreaOrgModel getEmployeeAreaOrgs(int employeeId, SystemInfo systemInfo);
}
