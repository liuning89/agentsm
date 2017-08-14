package com.lifang.agentsm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lifang.agentsm.model.*;
import com.lifang.housesoa.model.HouseResourceConcact;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.base.model.Response;
import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.dao.read.HouseResourceReadMapper;
import com.lifang.agentsm.dao.write.HouseResourceWriteMapper;
import com.lifang.agentsm.entity.LfSellHouseAgent;
import com.lifang.agentsm.service.HouseResourceService;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.agentsoa.model.Employee;
import com.lifang.httplog.util.StringUtil;
import com.lifang.model.PageResponse;
import com.lifang.search.client.service.SearchFacadeService;

@Service
public class HouseResourceImpl implements HouseResourceService {

	@Autowired
	private SearchFacadeService searchServiceClient;
	
	
	@Autowired
	private HouseResourceReadMapper houseResourceReadMapper;
	@Autowired
	private HouseResourceWriteMapper houseResourceWriteMapper;
	
	@Autowired
	private AgentSOAServer agentSOAServer;
	@Autowired
	private AgentSOAServer agentSoa;
	@Override
	public HouseSellResource loadHouseDetail(Integer houseId) {
		return houseResourceReadMapper.loadHouseSellDetail(houseId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lifang.agentsm.service.HouseResourceService#getLfHouseFollowUpList
	 * (com.lifang.agentsm.base.model.LfHouseFollowUpReq)
	 */
	@Override
	public PageResponse getLfHouseFollowUpList(LfHouseFollowUpReq req) {
		if (req.getPageIndex() != null) {
			req.setPageIndex(req.getPageIndex() + 1);
		}
		Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
		List<LfHouseFollowUp> followUpList = houseResourceReadMapper.getLfHouseFollowUpList(pagination);
		for(LfHouseFollowUp up : followUpList){
		    Agent agent = agentSoa.getAgent(Integer.valueOf(up.getAgentId()));
		    if(agent!=null){
		        up.setStoreName(agent.getStoreName());
		        up.setRealName(agent.getName());
		    }
		}
		PageResponse pageResponse = new PageResponse("success", 1,followUpList);
		pageResponse.setTotal(pagination.getTotal());
		return pageResponse;
	}
	
	/* (non-Javadoc)
	 * @see com.lifang.agentsm.service.HouseResourceService#getLfHouseFollowUpDetail(java.lang.Integer)
	 */
	@Override
	public LfHouseFollowUp getLfHouseFollowUpDetail(Integer id) {
		return houseResourceReadMapper.getLfHouseFollowUpDetail(id);
	}
	
	
	@Override
	public PageResponse getSellHouseList(SellHouseListRequest request) {
	    String agentId="1.2";
	    if(StringUtils.isNotBlank(request.getCompanyId())&&StringUtils.isNotBlank(request.getMobileOrName())){//加盟公司不为空 and 电话或名称不为空
	        List<Agent> agentList = agentSOAServer.getAgentList(null, null, null, null,null, Integer.valueOf(request.getCompanyId()));
	        List<Employee> empList = agentSOAServer.getEmployeeByName(request.getMobileOrName());
            Agent agent = agentSOAServer.getAgent(request.getMobileOrName());
	        if(agentList!=null){
	            for(Agent a : agentList){
	                for(Employee e: empList){
	                    if(a.getName().equals(e.getName())){
	                        agentId+=","+a.getId();
	                    }
	                }
	            }
	        }
	        if(agent!=null){
	            agentId+= ","+agent.getId();
	        }
	        request.setMobileOrName(agentId);
	    }
	    if(!StringUtils.isNotBlank(request.getCompanyId())&&StringUtils.isNotBlank(request.getMobileOrName())){//加盟公司为空  电话或名称不为空
            List<Employee> empList = agentSOAServer.getEmployeeByName(request.getMobileOrName());
            Agent agent = agentSOAServer.getAgent(request.getMobileOrName());
            if(empList!=null && empList.size()>0){
                for(int i=0;i<empList.size();i++){
                    if(i!=empList.size()-1){
                        agentId +=empList.get(i).getId()+ ",";   
                    }else{
                        agentId +=empList.get(i).getId();
                    }
                }
            }
            if(agent!=null){
                agentId +=","+agent.getId();
            }
            request.setMobileOrName(agentId);
	    }
	    
	    if(StringUtils.isNotBlank(request.getCompanyId())&&!StringUtils.isNotBlank(request.getMobileOrName())){//加盟公司不为空  电话或名称为空
	           List<Agent> agentList = agentSOAServer.getAgentList(null, null, null, null,null, Integer.valueOf(request.getCompanyId()));
	           for(Agent a :agentList){
	               agentId+=","+a.getId();
	           }
	           request.setMobileOrName(agentId);
	    }
	    
		request.setPageIndex(request.getPageIndex()+1);
		if(request.getEndTime() != null){
			request.setEndTime(new Date(request.getEndTime().getTime() + (3600l * 24 * 1000)));
		}
		if(request.getStatus()==null){
		    request.setStatus(1);
		}
		Pagination pagination = new Pagination(request, "pageSize", "pageIndex", "sortField", "sortOrder");
		pagination.putSortField("publishDate","hsr.checkPublishTime");
		pagination.putSortField("area","hi.spaceArea");
		pagination.putSortField("price","hsr.sellPrice");
		pagination.putSortField("unitPrice","hsr.unitPrice");
		if(request.getSortField()==null|| "".equals(request.getSortField())){
			pagination.setSortField("publishDate");
			pagination.setSortOrder("DESC");
		}
		System.out.println(request.getMobileOrName());
		List<SellHouseListModel> list = houseResourceReadMapper.getSellHouseList(pagination);
		/* List<Integer> idList = new ArrayList<Integer>();
		for(SellHouseListModel sm:list){
		    if(sm.getAgentId()!=null){
		        idList.add(sm.getAgentId());
		    }else{
		        idList.add(0);
		    }
		}
		List<Agent> agentList = agentSoa.getAgentsByIds(idList);
		for(int i=0;i<list.size();i++){
		    SellHouseListModel sm=list.get(i);
		    if(agentList.get(i)!=null){
		        sm.setPublisher(agentList.get(i).getName());
		    }
		}*/
		PageResponse pageResponse =  new PageResponse("success", 1,list );
		pageResponse.setTotal(pagination.getTotal());
		return pageResponse;
	}

	@Override
	public List<LfHouseOwnReq> getAchievementOwnList(LfHouseOwnReq req) {
		List<LfHouseOwnReq> list = houseResourceReadMapper.getAchievementOwnList(req);
//	    List<LfHouseOwnReq> list = new ArrayList<LfHouseOwnReq>();
//		List<LfSellHouseAgent> lfsellList =  (List<LfSellHouseAgent>) houseResourceReadMapper.getOwnByHouseId(req.getHouseId());
		/*LfHouseOwnReq comAgent =null;
		if(lfsellList.size()>0&&lfsellList!=null){
		    Map<String, Long> map = new HashMap<String, Long>();
		    for(LfSellHouseAgent sellAgent:lfsellList){
		        map.put("1", (sellAgent.getPublishAgent()==null?0:sellAgent.getPublishAgent()));
		      //  map.put("3", sellAgent.getCommAgent());
		        map.put("4", (sellAgent.getKeyAgent()==null?0:sellAgent.getKeyAgent()));
		        map.put("5", (sellAgent.getPictureAgent()==null?0:sellAgent.getPictureAgent()));
		        for (Map.Entry<String, Long> entry : map.entrySet()){
		            LfHouseOwnReq houseOwn = new LfHouseOwnReq();
	                houseOwn.setHouseId(req.getHouseId());
	                houseOwn.setPub(Integer.valueOf(entry.getKey()));
	                if(entry.getKey().equals("1")){
	                    houseOwn.setName("发布人");
	                    houseOwn.setPublishagent(Integer.valueOf(map.get("1").toString()));
	                }else if(entry.getKey().equals("4")){
	                    houseOwn.setName("钥匙人");
	                    houseOwn.setPublishagent(Integer.valueOf(map.get("4").toString()));
	                }else if(entry.getKey().equals("5")){
	                    houseOwn.setName("实景人");
	                    houseOwn.setPublishagent(Integer.valueOf(map.get("5").toString()));
	                }
	                houseOwn.setId(sellAgent.getId());
	                list.add(houseOwn);
	            }
	        }
		    comAgent = new LfHouseOwnReq();
		    comAgent.setHouseId(req.getHouseId());
		    comAgent.setPub(3);
		    comAgent.setId(0L);
		    comAgent.setPublishagent(Integer.valueOf(lfsellList.get(0).getCommAgent()==null?"0":lfsellList.get(0).getCommAgent().toString()));
		    comAgent.setName("独家人");
		    list.add(comAgent);
	    }*/
            for(LfHouseOwnReq lfHouse:list){
                if(lfHouse!=null){
                    Agent agent = agentSOAServer.getAgent(lfHouse.getPublishagent());
                    if(agent!=null){
                        lfHouse.setRealName(agent.getName());
                        lfHouse.setStoreName(agent.getStoreName());
                        lfHouse.setStoreId(agent.getStoreId());
                        //   lfHouse.setId(Long.valueOf(agent.getId()));
                    }
                   /* if(lfHouse.getRealName()==null||lfHouse.getRealName()==""){
                        lfHouse.setCreateTime(null);
                    }*/
                }
            }
            return list;
		}
	@Override
	public AgentSupport getAgentSupport(Integer houseId) {
		return houseResourceReadMapper.getAgentSupport(houseId);
	}

	@Override
	public List<ComboModel> getStoreListByHouseId(Integer houseId,Integer cityId,int franchiseeId) {
		return houseResourceReadMapper.getStoreListByHouseId(houseId,cityId,franchiseeId);
	}

	@Override
	public List<ComboModel> getAgentListByStoreId(Integer houseId,Integer storeId) {
	    List<Agent> agentList= agentSOAServer.getAgentList(null,null, storeId,null,null);
	    List<ComboModel> list = new ArrayList<ComboModel>();
	    if(agentList.size()>0&&agentList!=null){
	        for(Agent agent:agentList){
	            ComboModel cm = new ComboModel();
	            cm.setId(agent.getId());
	            cm.setText(agent.getName());
	            list.add(cm);
	        }
	    }
		return  list;
	}

	@Override
	public boolean saveUpdateAgent(Integer houseAgentId, Integer houseId,String flag,int id) {
		boolean result = houseResourceWriteMapper.saveUpdateAgent(houseAgentId,houseId,flag,id)!=0;
		if(result){
			
				int type = 0;
				if("publishAgent".equals(flag)){
					type=1;
				}else if("pictureAgent".equals(flag)){
					type=5;
				}else if("commAgent".equals(flag)){
					type=3;
				}else if("keyAgent".equals(flag)){
					type=4;
				}
				LfAgentOperation o = new LfAgentOperation();
				o.setCreateTime(new Date());
				o.setHouseId(houseId);
				o.setOperationType(type);
				o.setOperatorId(houseAgentId);
				o.setStatus(1);
				o.setUpdateTime(new Date());
				houseResourceWriteMapper.updateAgentOperation(houseId,type);
				houseResourceWriteMapper.insertCommAgent(o);
		}
		return result;
	}

    @Override
    public Map<String, Object> getHouseSellCheckRecordList(Map<String, Object> params){
        try{
            int count = houseResourceReadMapper.getHouseSellCheckRecordCount(params);
            List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
            if(count > 0){
                list = houseResourceReadMapper.getHouseSellCheckRecordList(params);
                /*for(int i=0;i<list.size();i++){
                    Map<String,Object> map = list.get(i);
                    Agent agent = agentSOAServer.getAgent(Integer.valueOf(map.get("operatorId").toString()));
                    if(agent!=null){
                        map.put("realName", agent.getName());
                    }
                }*/
            }
            params.put("data", list);
            params.put("total", count);
        }catch (Exception e){
            e.printStackTrace();
        }
        return params;
    }

	@Override
	public boolean deleteAgent(Integer houseAgentId, Integer houseId,
			String flag,int id) {
	    if("commAgent".equals(flag)){
	        id=0;
	    }
		boolean result = houseResourceWriteMapper.deleteAgent(houseAgentId,houseId,flag,id)!=0;
		if(result){
			if("commAgent".equals(flag)){
				houseResourceWriteMapper.updateCommAgent(houseId);
			}
			int type = 0;
			if("publishAgent".equals(flag)){
				type=1;
			}else if("pictureAgent".equals(flag)){
				type=5;
			}else if("commAgent".equals(flag)){
				type=3;
			}else if("keyAgent".equals(flag)){
				type=4;
			}
			houseResourceWriteMapper.updateAgentOperation(houseId,type);
		}
		return result;
	}

	@Override
	public Response addOwn(Integer houseId) {
		List<LfSellHouseAgent> lfsell = houseResourceReadMapper.getOwnByHouseId(houseId);
		if(lfsell!=null && lfsell.size()>0){
			return new Response("success",1,"数据已存在！"); 
		}else{
			int i = houseResourceWriteMapper.add(houseId);
			if(i==1){
				return new Response("success",1,"插入成功！");
			}else{
				return new Response("fail",0,"插入出错！");
			}
		}
	}

	@Override
	public PageResponse getLfHouseFollowUpListAll(LfHouseFollowUpReq req) {
		if (req.getPageIndex() != null) {
			req.setPageIndex(req.getPageIndex() + 1);
		}
		Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
    		if (req.getAgentId() != null && !"".equals(req.getAgentId())) {
    		    List<Agent> agentCityList = new ArrayList<Agent>();
    		    Agent agent = new Agent();
    		    agent.setId(Integer.parseInt(req.getAgentId()));
    		    agentCityList.add(agent);
                pagination.put("agentIdList", agentCityList);
            }else if (req.getStoreCode() != null && !"".equals(req.getStoreCode())) {
                
                List<Agent> agentCityList = agentSoa.getAgentList(null, null, Integer.parseInt(req.getStoreCode()), null, null);
                if (agentCityList != null && agentCityList.size() == 0) {
                    List<Agent> agentlAgents = new ArrayList<Agent>();
                    Agent agent = new Agent();
                    agent.setId(-1);
                    agentlAgents.add(agent);
                    pagination.put("agentIdList", agentlAgents);
                }else {
                    
                    pagination.put("agentIdList", agentCityList);
                }
                
            }else if (req.getAreaCode() != null && !"".equals(req.getAreaCode())) {
//                int areaId = houseResourceReadMapper.findCityIdById(Integer.parseInt(req.getAreaCode()));
                
                
                List<Agent> agentCityList = agentSoa.getAgentList(null, Integer.parseInt(req.getAreaCode()), null, null, null);
                if (agentCityList != null && agentCityList.size() == 0) {
                    List<Agent> agentlAgents = new ArrayList<Agent>();
                    Agent agent = new Agent();
                    agent.setId(-1);
                    agentlAgents.add(agent);
                    pagination.put("agentIdList", agentlAgents);
                }else {
                    pagination.put("agentIdList", agentCityList);
                    
                }
                
                
            }else if (req.getCityCode() != null && !"".equals(req.getCityCode())) {
                
                int cityId = houseResourceReadMapper.findCityIdById(Integer.parseInt(req.getCityCode()));
                
                List<Agent> agentCityList = agentSoa.getAgentList(cityId, null, null, null, null);
                pagination.put("agentIdList", agentCityList);
            }else if(Integer.parseInt(req.getCityId()) == 1){
                List<Agent> agentCityList = agentSoa.getAgentList(null, null, null, null, null);
                pagination.put("agentIdList", agentCityList);
            }else {
                List<Agent> agentCityList = agentSoa.getAgentList(Integer.parseInt(req.getCityId()), null, null, null, null);
                pagination.put("agentIdList", agentCityList);
            }
		
		if (pagination.get("agentIdList") == null || pagination.get("agentIdList") == "") {
		    List<Agent> agentCityList = new ArrayList<Agent>();
            Agent agent = new Agent();
            agent.setId(-1);
            agentCityList.add(agent);
            pagination.put("agentIdList", agentCityList);
        }
		
		
		
		List<LfHouseFollowUp> followList = houseResourceReadMapper.getLfHouseFollowUpListAll(pagination);
		
		//
        List<Integer> auditoridList = new ArrayList<Integer>();

        for(LfHouseFollowUp lu : followList){
			if(lu.getAgentId() != null && !"".equals(lu.getAgentId())){
				auditoridList.add(Integer.parseInt(lu.getAgentId()));
			}
		}
        List<Agent> auditorAgentList = agentSoa.getAgentsByIds(auditoridList);
        
        if(auditorAgentList != null && auditorAgentList.size() > 0){
        	for(LfHouseFollowUp lu : followList){
        		for(Agent a : auditorAgentList){
        			if(!StringUtil.isEmpty(lu.getAgentId()) && a != null && !StringUtil.isEmpty(a.getId()+"")){
        				if(Integer.parseInt(lu.getAgentId()) == a.getId()){
//							lu.setAuditor(a.getName());
        					lu.setRealName(a.getName());
        					lu.setStoreName(a.getStoreName());
//							lu.setDeptName(a.getStoreName());
						}
        			}
        		}
        	}
        }
		
		
		PageResponse pageResponse = new PageResponse("success", 1,followList);
		pageResponse.setTotal(pagination.getTotal());
		return pageResponse;
	}

    @Override
    public void saveHouseEditRecord(HouseEditRecord eRecord) {
        houseResourceWriteMapper.saveHouseEditRecord(eRecord);
    }

    @Override
    public void updateHouseInfoByHouseId(Integer houseId, Integer houseResourceStatus) {
        Map map = new HashMap<String, Integer>();
        map.put("houseId", houseId);
        map.put("status",houseResourceStatus);
        houseResourceWriteMapper.updateHouseInfoStatus(map);
    }

    @Override
    public Response saveHouseContactLog(HouseContactSeeLog log) {
         if(houseResourceWriteMapper.saveHouseContactLog(log)!=0){
             return new Response("success",1,"成功");
         }
         return new Response("success",1,"出错！");
    }

    @Override
    public List<ComboModel> getFranchiseeList(int houseId) {
        return houseResourceReadMapper.getFranchiseeList(houseId);
    }

    @Override
    public List<ComboModel> getFranchiseeListByCityId(int cityId) {
        return houseResourceReadMapper.getFranchiseeListByCityId(cityId);
    }

    @Override
    public List<LfAuditInvalid> houseValidReasonList(int houseId) {
        List<LfAuditInvalid> list = houseResourceReadMapper.houseValidReasonList(houseId);
        for(LfAuditInvalid lfAudit:list){
            Agent agent = agentSoa.getAgent(lfAudit.getAgentId());
            if(agent!=null)
                lfAudit.setAgentName(agent.getName());
        }
        return list;
    }

    @Override
    public List<MiniuiEntity> getReasionList() {
        return houseResourceReadMapper.getReasionList();
    }

    @Override
    public void saveUnstatusReason(HouseInvalidReasonRecord hirr) {
        houseResourceWriteMapper.saveUnstatusReason(hirr);
    }



	@Override
    public List<HouseInvalidStatusReason> getInvalidReasonByHouseId(Integer houseId) {
        return houseResourceReadMapper.getInvalidReasonByHouseId(houseId);
    }

	@Override
	public void updateShield(Map<String, Object> pars) {
	    houseResourceWriteMapper.updateShield(pars);
		
	}

    @Override
    public PageResponse invalidReasonList(HouseInvalidReasonRecord req) {
        if (req.getPageIndex() != null) {
            req.setPageIndex(req.getPageIndex()+1);
        }
        Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
        PageResponse response = new PageResponse("success", 1, houseResourceReadMapper.invalidReasonList(pagination));
        response.setTotal(pagination.getTotal());
        return response;
        
    }

    @Override
    public List<SellHouseListModel> getPublisherList(int houseId) {
        List<SellHouseListModel> list = houseResourceReadMapper.getPublisherList(houseId);
        for(SellHouseListModel sm:list){
            if(sm.getAgentId()!=null){
                Agent agent = agentSOAServer.getAgent(sm.getAgentId());
                sm.setStoreName(agent.getStoreName());
                sm.setPublisher(agent.getName());
                sm.setCompanyName(agent.getFranchiseeCompanyName());
            }
        }
        return list;
    }

	@Override
	public List<SellHouseListModel> getCommonList(int houseId) {
		List<SellHouseListModel> list = houseResourceReadMapper.getCommonList(houseId);
		for(SellHouseListModel sm:list){
			if(sm.getAgentId()!=null){
				Agent agent = agentSOAServer.getAgent(sm.getAgentId());
				sm.setStoreName(agent.getStoreName());
				sm.setPublisher(agent.getName());
				sm.setCompanyName(agent.getFranchiseeCompanyName());
			}
		}
		return list;
	}

	@Override
	public List<SellHouseListModel> getPictureList(int houseId) {
		List<SellHouseListModel> list = houseResourceReadMapper.getPictureList(houseId);
		for(SellHouseListModel sm:list){
			if(sm.getAgentId()!=null){
				Agent agent = agentSOAServer.getAgent(sm.getAgentId());
				sm.setStoreName(agent.getStoreName());
				sm.setPublisher(agent.getName());
				sm.setCompanyName(agent.getFranchiseeCompanyName());
			}
		}
		return list;
	}

	@Override
	public HouseResourceConcact getHouseContactDataByContactId(int contactId) {
		return houseResourceReadMapper.getHouseContactDataByContactId(contactId);
	}

	@Override
	public List<SellHouseListModel> getKeyList(int houseId) {
		List<SellHouseListModel> list = houseResourceReadMapper.getKeyList(houseId);
		for(SellHouseListModel sm:list){
			if(sm.getAgentId()!=null){
				Agent agent = agentSOAServer.getAgent(sm.getAgentId());
				sm.setStoreName(agent.getStoreName());
				sm.setPublisher(agent.getName());
				sm.setCompanyName(agent.getFranchiseeCompanyName());
			}
		}
		return list;
	}
}
