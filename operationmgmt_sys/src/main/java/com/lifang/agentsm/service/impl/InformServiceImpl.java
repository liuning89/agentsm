package com.lifang.agentsm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.read.AgentAccusationReadMapper;
import com.lifang.agentsm.dao.read.HouseImageReadMapper;
import com.lifang.agentsm.dao.write.AgentAccusationWriteMapper;
import com.lifang.agentsm.model.AccusAtion;
import com.lifang.agentsm.service.InformService;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.agentsoa.model.Employee;
import com.lifang.agentsoa.model.enums.SystemInfo;


/**
 * 加载举报列表
 * @author luogq
 *
 */
@Service
public class InformServiceImpl implements InformService {

	@Autowired
	AgentAccusationReadMapper agentRead;
	@Autowired
	AgentAccusationWriteMapper agentWrite;

	@Autowired
	private HouseImageReadMapper houseImageReadMapper;
	@Autowired
    private AgentSOAServer agentSoa;
	@Override
	public Map<String, Object> getInformList(Map<String, Object> pars) {
//		String code = "";
//		if(pars.get("cityCode") == null || "".equals(pars.get("cityCode"))){
//			code = houseImageReadMapper.findCode(pars);
//		}else{
//			code = pars.get("cityCode")+"";
//		}
//		
//		 
//		
//		pars.put("code",code);
//		if(pars.get("agentCity") != null){
//			List<Agent> agentCityList = agentSoa.getAgentList(Integer.parseInt(pars.get("agentCity")+""), null, null, null, null);
//	        pars.put("agentCityList", agentCityList);
//		}
//		
		if(pars.get("agentCity") != null && Integer.parseInt(pars.get("agentCity")+"") != 1){
			List<Agent> agentCityList = agentSoa.getAgentList(Integer.parseInt(pars.get("agentCity")+""), null, null, null, null);
	        pars.put("agentCityList", agentCityList);
		}else if (pars.get("agentCity") != null && Integer.parseInt(pars.get("agentCity")+"") == 1) {
			List<Agent> agentCityList = agentSoa.getAgentList(null, null, null, null, null);
	        pars.put("agentCityList", agentCityList);
		}
		
		
		 Map<String, Object> map = new HashMap<String, Object>();

		
		 int count = agentRead.selectByCount(pars);
		 List<AccusAtion> alist = new ArrayList<AccusAtion>();
		 if (count > 0) {
			 alist = agentRead.selectBy(pars); 
		}
		//举报人姓名
		 List<Integer> employeedIdList = new ArrayList<Integer>();

	        for(AccusAtion lu : alist){
				if(lu.getAccusationAgentId() != null && !"".equals(lu.getAccusationAgentId()+"")){
					employeedIdList.add(lu.getAccusationAgentId());
				}
			}
	        List<Agent> agentList = agentSoa.getAgentsByIds(employeedIdList);
	        if(agentList != null && agentList.size() > 0){
	        	for(AccusAtion lu : alist){
	        		for(Agent a : agentList){
	        			if(lu.getAccusationAgentId() != null && !"".equals(lu.getAccusationAgentId()+"") && a != null && a.getId() != null && !"".equals(a.getId()+"")){
	        				if(lu.getAccusationAgentId().intValue() == a.getId().intValue()){
								lu.setName1(a.getName());
								lu.setPositiName1(a.getStoreName());
							}
	        			}
	        		}
	        	}
	        }
		 
		 //被举报人姓名
	    employeedIdList.clear();
		for(AccusAtion lu : alist){
			if(lu.getBeAccusationAgentId() != null && !"".equals(lu.getBeAccusationAgentId()+"")){
				employeedIdList.add(lu.getBeAccusationAgentId());
			}
		}
//        List<Agent> getAgentByIds(List<Integer> ids);
		if (agentList != null ) {
            
		    agentList.clear();
        }
        agentList = agentSoa.getAgentsByIds(employeedIdList);
        
        if(agentList != null && agentList.size() > 0){
        	for(AccusAtion lu : alist){
        		for(Agent a : agentList){
        			if(lu.getBeAccusationAgentId() != null && !"".equals(lu.getBeAccusationAgentId()+"") && a != null && a.getId() != null && !"".equals(a.getId()+"")){
        				if(lu.getBeAccusationAgentId().intValue() == a.getId().intValue()){
							lu.setName(a.getName());
							lu.setPositiName(a.getStoreName());
						}
        			}
        		}
        	}
        }
       //处理人
        employeedIdList.clear();
		for(AccusAtion lu : alist){
			if(lu.getAuditor() != null && !"".equals(lu.getAuditor()+"")){
				employeedIdList.add(lu.getAuditor());
			}
		}
//        List<Agent> getAgentByIds(List<Integer> ids);
//		agentList.clear();
//        agentList = agentSoa.getAgentsByIds(employeedIdList);
		
		List<Employee> eList = agentSoa.getEmployee(employeedIdList,SystemInfo.OperationManagementSystem);
        
        if(eList != null && eList.size() > 0){
        	for(AccusAtion lu : alist){
        		for(Employee a : eList){
        			if(lu.getAuditor() != null && !"".equals(lu.getAuditor() +"") && a != null && a.getId() != null && !"".equals(a.getId()+"")){
        				if(lu.getAuditor().intValue() == a.getId().intValue()){
							lu.setAname(a.getName());
						}
        			}
        		}
        	}
        }
		map.put("data", alist);
	      map.put("total", count);
	      
		
		return map;
	}

	@Override
	public void updateInform(Map<String, Object> pars) {
		agentWrite.updateInform(pars);
	}

	@Override
	public void deleteInform(String id) {
		agentWrite.deleteInform(id);
	}

   
}
