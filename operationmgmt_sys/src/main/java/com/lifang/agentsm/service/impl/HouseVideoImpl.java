package com.lifang.agentsm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lifang.agentsm.dao.read.HouseImageReadMapper;
import com.lifang.agentsm.dao.read.HouseVideoReadMapper;
import com.lifang.agentsm.dao.write.HouseVideoWriteMapper;
import com.lifang.agentsm.dao.write.LfSellHouseAgentWriteMapper;
import com.lifang.agentsm.entity.HouseInfo;
import com.lifang.agentsm.model.HouseVideoAuditInfo;
import com.lifang.agentsm.service.HouseVideoService;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.agentsoa.model.Employee;
import com.lifang.agentsoa.model.enums.SystemInfo;
import com.lifang.housesoa.facade.service.HouseService;
import com.lifang.housesoa.model.HouseDetailInfo;
import com.lifang.httplog.util.StringUtil;
import com.lifang.imgsoa.facade.ImageService;
import com.lifang.model.enums.AppEnum;


/**
 * 
* @ClassName: HouseVideoService 
* @Description: TODO(房源图片service) 
* @author luogq
* @date 2015年8月19日 下午2:38:55 
*
 */
@Service
public class HouseVideoImpl implements HouseVideoService {

    @Autowired
    private HouseService houseRentClient;
    
    @Autowired
    private ImageService imgSOAClient;
    
    @Autowired
    private HouseImageReadMapper houseImageReadMapper;
    
    @Autowired
    private HouseVideoWriteMapper houseVideoWriteMapper;
    
    @Autowired
    private HouseVideoReadMapper houseVideoReadMapper;

    
    @Autowired
    private LfSellHouseAgentWriteMapper lfSellHouseAgentWriteMapper;
    
    @Autowired
    private AgentSOAServer agentSoa;
    
    private static final int FIRSTPAGEINDEX = 1;
    
    @Override
    public HouseDetailInfo getAllHouseInfo(Integer houseId) {
        com.lifang.housesoa.model.Response<HouseDetailInfo> resInfo = houseRentClient
                .getHouseDetailInfoByHouseId(houseId,AppEnum._lf_agentPC.getResource());
        return resInfo.getData();
    }
 

	@Override
	public List<HouseInfo> getEstateName() {
//		int count = houseImageReadMapper.selectHouseNameCount(pars);
//		int count = lfStoreReadMapper.selectStortCount(pars);
		/*Map<String, Object> map = new HashMap<String, Object>();
    	map.put("rows", count > 0 ? lfStoreReadMapper.selectStortList(pars) : new ArrayList<>());
		map.put("total", count);*/
		return houseImageReadMapper.getEstateName();
	}


	@Override
	public Map<String, Object> getVideoAuditList(Map<String, Object> pars) {
//		String code = "";
//		if("".equals(pars.get("agentCode"))){
//			code = "";
//		}else{
//			code = houseImageReadMapper.findCode(pars);
//		}
//		pars.put("code",code);

    	String videoType = pars.get("videoType") + "";
        Map<String, Object> map = new HashMap<String, Object>();
//        if(pars.get("agentCity") != null){
//	        List<Agent> agentCityList = agentSoa.getAgentList(Integer.parseInt(pars.get("agentCity")+""), null, null, null, null);
//	        pars.put("agentCityList", agentCityList);
//        }
        
        if(pars.get("agentCity") != null && Integer.parseInt(pars.get("agentCity")+"") != 1){
			List<Agent> agentCityList = agentSoa.getAgentList(Integer.parseInt(pars.get("agentCity")+""), null, null, null, null);
	        pars.put("agentCityList", agentCityList);
		}else if (pars.get("agentCity") != null && Integer.parseInt(pars.get("agentCity")+"") == 1) {
			List<Agent> agentCityList = agentSoa.getAgentList(null, null, null, null, null);
	        pars.put("agentCityList", agentCityList);
		}
        
        
		if("1".equals(videoType)){//1:房屋视频，2:小区视频
			
			int count = houseVideoReadMapper.getHouseVideoAuditListCount(pars);
			
			List<HouseVideoAuditInfo> list = new ArrayList<HouseVideoAuditInfo>();
	        if (count > 0) {
	            list = houseVideoReadMapper.getHouseVideoAuditList(pars);
	        }
	        //根据agentid获取经纪人姓名和门店  视频上传人
	        List<Integer> agentIdList = new ArrayList<Integer>();
	        for(HouseVideoAuditInfo lu : list){
	        	if(lu.getEmployeeId() != null && !"".equals(lu.getEmployeeId())){//视频上传人
	        	    if("5".equals(lu.getVideostatus())||"6".equals(lu.getVideostatus())){
                        if(lu.getAuditorid()!=null){
                            Employee e = agentSoa.getEmployee(Integer.valueOf(lu.getAuditorid()));
                            if(e!=null){
                                lu.setAuditor(e.getName());
                            }
                        }
                    }
	        		agentIdList.add(Integer.parseInt(lu.getEmployeeId()));
				}
	        }
	        
	        List<Agent> aList = new ArrayList<Agent>();
	        aList = agentSoa.getAgentsByIds(agentIdList);
	        
	        if(aList != null && aList.size() > 0){
				for(HouseVideoAuditInfo lu : list){
					for(Agent a : aList){
						if(!StringUtil.isEmpty(lu.getEmployeeId()) && a != null && !StringUtil.isEmpty(a.getId() +"")){
							if(Integer.parseInt(lu.getEmployeeId()) == a.getId()){
								lu.setDeptName(a.getStoreName());
								lu.setAgentName(a.getName());
								lu.setMobile(a.getMobile());
								
							}
						}
					}
				}
	        }
//	        
	      //根据agentid获取经纪人姓名和门店  审核人
//	        List<Integer> agentIdList = new ArrayList<Integer>();
	        if (agentIdList != null && agentIdList.size() > 0) {
				
	        	agentIdList.clear();
			}
	        for(HouseVideoAuditInfo lu : list){
	        	if(lu.getAuditorid() != null && !"".equals(lu.getAuditorid())){//视频上传人
	        		agentIdList.add(Integer.parseInt(lu.getAuditorid()));
				}
	        }
	        if (aList != null && aList.size() > 0) {
				
	        	aList.clear();
			}
//	        List<Agent> aList = agentSoa.getAgentByIds(agentIdList);
	        
	        /*aList = agentSoa.getAgentsByIds(agentIdList);

	        if(aList != null && aList.size() > 0){
				for(HouseVideoAuditInfo lu : list){
					for(Agent a : aList){
						if(!StringUtil.isEmpty(lu.getAuditorid()) && a != null && !StringUtil.isEmpty(a.getId()+"")){
							if(Integer.parseInt(lu.getAuditorid()) == a.getId()){
								lu.setDeptName(a.getStoreName());
								lu.setAuditor(a.getName());
							}
						}
					}
				}
	        }*/
	        
//			List<Integer> positionList = new ArrayList<Integer>();
//			for(HouseVideoAuditInfo lu : list){
//				if(lu.getPositionid() != null && !"".equals(lu.getPositionid())){
//					positionList.add(Integer.parseInt(lu.getPositionid()));
//				}
//			}
//			
//			List<PositionCache> pcList = apc.getShowNameByPositionIds(positionList);
//			
//			if(pcList != null && pcList.size() > 0){
//				for(HouseVideoAuditInfo lu : list){
//	
//					for(PositionCache pc : pcList){
//						if(lu.getPositionid() != null && !"".equals(lu.getPositionid()) && pc != null && pc.getPersonPositionId() != null && !"".equals(pc.getPersonPositionId())){
//							if(Integer.parseInt(lu.getPositionid()) == pc.getPersonPositionId()){
//			//					lu.setAreaName(pc.getAreaOrg());
//			                    lu.setDeptName(pc.getDoorAreaOrg());
//			                    break;
//							}
//						}
//					}
//				}
//			}
//	        
	        map.put("data", list);
	        map.put("total", count);
			
    	}else{

			int count = houseVideoReadMapper.getEstateVideoAuditListCount(pars);
			
			List<HouseVideoAuditInfo> list = new ArrayList<HouseVideoAuditInfo>();
	        if (count > 0) {
	            list = houseVideoReadMapper.getEstateVideoAuditList(pars);
	        }

	        //上传人id 
			List<Integer> employeedIdList = new ArrayList<Integer>();

	        for(HouseVideoAuditInfo lu : list){
				if(lu.getEmployeeId() != null && !"".equals(lu.getEmployeeId())){
				    if("5".equals(lu.getVideostatus())||"6".equals(lu.getVideostatus())){
				        if(lu.getAuditorid()!=null){
				            Employee e = agentSoa.getEmployee(Integer.valueOf(lu.getAuditorid()));
				            if(e!=null){
				                lu.setAuditor(e.getName());
				            }
				        }
                    }
					employeedIdList.add(Integer.parseInt(lu.getEmployeeId()));
				}
			}
//	        List<Agent> getAgentByIds(List<Integer> ids);
	        List<Agent> agentList = agentSoa.getAgentsByIds(employeedIdList);
	        
	        if(agentList != null && agentList.size() > 0){
	        	for(HouseVideoAuditInfo lu : list){
	        		for(Agent a : agentList){
	        			if(!StringUtil.isEmpty(lu.getEmployeeId()) && a != null && !StringUtil.isEmpty(a.getId()+"")){
	        				if(Integer.parseInt(lu.getEmployeeId()) == a.getId()){
								lu.setAgentName(a.getName());
								lu.setDeptName(a.getStoreName());
								lu.setMobile(a.getMobile());
							}
	        			}
	        		}
	        	}
	        }
	        
	        //审核人id
	        List<Integer> auditoridList = new ArrayList<Integer>();

	        for(HouseVideoAuditInfo lu : list){
				if(lu.getAuditorid() != null && !"".equals(lu.getAuditorid())){
					auditoridList.add(Integer.parseInt(lu.getAuditorid()));
				}
			}
	        List<Employee> auditorAgentList = agentSoa.getEmployee(auditoridList,SystemInfo.OperationManagementSystem);
	        
	        if(auditorAgentList != null && auditorAgentList.size() > 0){
	        	for(HouseVideoAuditInfo lu : list){
	        		for(Employee a : auditorAgentList){
	        			if(!StringUtil.isEmpty(lu.getEmployeeId()) && a != null && !StringUtil.isEmpty(a.getId()+"")){
	        				if(Integer.parseInt(lu.getEmployeeId()) == a.getId()){
								lu.setAuditor(a.getName());
//								lu.setDeptName(a.getStoreName());
							}
	        			}
	        		}
	        	}
	        }
	        
	        map.put("data", list);
	        map.put("total", count);
    	}
		
		
        return map;
	}


	@Transactional
	@Override
	public void updateAudit(Map<String, Object> pars) {

//    	request.setAttribute("videoType", pars.get("videoType"));
//    	request.setAttribute("id", pars.get("id"));
		
		if("0".equals(pars.get("flag"))){
			pars.put("videoStatus",5);
		}else{
			pars.put("videoStatus",6);
		}
    	
		if("1".equals(pars.get("videoType"))){//房源视频
			//修改视频表的状态
			houseVideoWriteMapper.updateHouseVideo(pars);
		}else{//小区视频
			//修改视频表的状态
			houseVideoWriteMapper.updateEstateVideo(pars);
		}
		//插入一条审核记录
		houseVideoWriteMapper.insertVideoAudit(pars);
		
		
	}

}
