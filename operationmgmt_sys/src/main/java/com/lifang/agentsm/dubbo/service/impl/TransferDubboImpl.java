package com.lifang.agentsm.dubbo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.read.TransferReadMapper;
import com.lifang.agentsm.dubbo.service.TransferDubboService;
import com.lifang.agentsm.model.TransferResouce;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;


/**
 * 
* @ClassName: TransferDubboService 
* @Description: (资源转移service) 
* @author luogq
* @date 2015年10月20日 
*
 */
@Service
public class TransferDubboImpl implements TransferDubboService {

	@Autowired
	TransferReadMapper transferReadMapper;
	@Autowired
	AgentSOAServer agentSOAServer;

	@Override
	public boolean transferResouce(String areaId) {
		boolean result = false;
		Map<String, Object> pars = new HashMap<String,Object>();
		pars.put("areaId",areaId);
		
		System.out.println("传进的加盟商区域id为:"+areaId+"***********************");
		
		List<String> areaList = transferReadMapper.findAreaById(areaId);
		System.out.println("加盟商对应的区域为:"+areaList.size()+"**************");
//		System.out.println(areaList);
		//查处区域下对应的经纪人
        List<Agent> agentIdOut = new ArrayList<Agent>();
		for(String s : areaList){
		   List<Agent> aList = agentSOAServer.getAgentList(null, Integer.parseInt(s), null, null, null);
		   agentIdOut.addAll(aList);
		}
		
		
//		agentIdOut = agentSOAServer.getAgentList(null, Integer.parseInt(pars.get("areaId")+""), null, null, null);
//		System.out.println(agentIdOut);
		System.out.println("查找区域下的加盟商的人为:"+agentIdOut+"size为:"+agentIdOut.size()+"******************");
		if (agentIdOut.size() > 0) {
       
    		pars.put("agentIdOut", agentIdOut);
    
    		/**********************************************************发布人**********************************************************************************/
    		List<TransferResouce> transferPublishInList = new ArrayList<TransferResouce>();
    		transferPublishInList = transferReadMapper.findPublishInBoolean(pars);
    		if (transferPublishInList != null && transferPublishInList.size() > 0) {
    			
    			result = true;
    			return result;
    		}
    		
    		/**********************************************************实景人**********************************************************************************/
    		List<TransferResouce> transferPictureInList = new ArrayList<TransferResouce>();
    		transferPictureInList = transferReadMapper.findPictureInBoolean(pars);
    		if (transferPictureInList != null && transferPictureInList.size() > 0) {
    			
    			result = true;
    			return result;
    		}
    		
    		/**********************************************************钥匙经纪人**********************************************************************************/
    		List<TransferResouce> transferKeyInList = new ArrayList<TransferResouce>();
    		transferKeyInList = transferReadMapper.findKeyInBoolean(pars);
    		if (transferKeyInList != null && transferKeyInList.size() > 0) {
    			
    			result = true;
    			return result;
    		}
    		/**********************************************************速消人’**********************************************************************************/
    		List<TransferResouce> transferCommInList = new ArrayList<TransferResouce>();
    		transferCommInList = transferReadMapper.findCommInBoolean(pars);
    		if (transferCommInList != null && transferCommInList.size() > 0) {
    			
    			result = true;
    			return result;
    		}
    		/**********************************************************维护人**********************************************************************************/
    		List<TransferResouce> transferMaintainInList = new ArrayList<TransferResouce>();
    		transferMaintainInList = transferReadMapper.findMaintainInBoolean(pars);
    		if (transferMaintainInList != null && transferMaintainInList.size() > 0) {
    			
    			result = true;
    			return result;
    		}
    		/**********************************************************专属经纪人**********************************************************************************/
    //		List<TransferResouce> transferCusGuestInList = new ArrayList<TransferResouce>();
    		int count = transferReadMapper.findCusGuestInBoolean(pars);
    		if (count > 0) {
    			
    			result = true;
    			return result;
    		}
    		
    		/**********************************************************带看**********************************************************************************/
    		int houseSeeCount = transferReadMapper.findhouseSeeInBoolean(pars);
    		if (houseSeeCount > 0) {
    			
    			result = true;
    			return result;
    		}
    		/**********************************************************客户需求**********************************************************************************/
    		int customerCount = transferReadMapper.findCustomerInBoolean(pars);
    		if (customerCount > 0) {
    			
    			result = true;
    			return result;
    		}
    		
    		
    		/**********************************************************客源**********************************************************************************/
    		int cCount = transferReadMapper.findCInBoolean(pars);
    		if (cCount > 0) {
    			
    			result = true;
    			return result;
    		}
    		/**********************************************************小区**********************************************************************************/
    		int estateCount = transferReadMapper.findEstateInBoolean(pars);
    		if (estateCount > 0) {
    			
    			result = true;
    			return result;
    		}
    		/**********************************************************待看记录**********************************************************************************/
    		int cusSeeCount = transferReadMapper.findcusSeeInBoolean(pars);
    		if (cusSeeCount > 0) {
    			
    			result = true;
    			return result;
    		}
		 }
		return result;
	}

}
