package com.lifang.agentsm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lifang.agentsm.model.*;
import com.lifang.bzsm.console.model.AgentRequest;
import com.lifang.agentsm.dao.read.*;
import com.lifang.model.PageResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leo.common.util.MD5Digest;
import com.lifang.agentsm.dao.write.LfAgentWriteMapper;
import com.lifang.agentsm.entity.LfAgent;
import com.lifang.agentsm.entity.LfEmployee;

import com.lifang.agentsm.service.LfAgentService;
import com.lifang.agentsm.utils.ImageUtils;
import com.lifang.agentsm.utils.OrgCodeUtil;
import com.lifang.agentsm.utils.ValidateUtil;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.agentsoa.model.enums.SystemInfo;
import com.lifang.imgsoa.facade.ImageService;
import com.lifang.imgsoa.model.ImageKeyObject;

@Service(value = "lfAgentService")
public class LfAgentImpl implements LfAgentService {
    private static Logger logger = LoggerFactory.getLogger(LfAgentImpl.class);

    @Autowired
    private HouseResourceReadMapper houseRead;
    
    @Autowired
    private LfAgentReadMapper lfAgentReadMapper;
    @Autowired
    private LfAgentWriteMapper lfAgentWriteMapper;
    @Autowired
    private ImageService imgSOAClient;
    @Autowired
    private HouseImageReadMapper houseImageReadMapper;
//    @Autowired
//	private AreaOrgPriSOAClient apc;
    @Autowired
    private AgentSOAServer agentSoa;
    @Autowired
    private LfAreaOrgReadMapper lfAreaOrgReadMapper;
    @Autowired
    private AgentSOAServer agentSOAServer;
    @Autowired
    private HouseResourceReadMapper houseResourceReadMapper;
	@Override
	public int insertAgent(LfAgent agent,byte[] img) {
		ImageKeyObject imgKey = uploadImg(img);
		if(imgKey!=null){
			logger.info("调用imageSOA上传头像，返回key={}",imgKey.getKey());
			agent.setPhotoHeadUrl(imgKey.getKey());
		}else{
			logger.info("调用imageSOA上传头像，返回为空");
		}
		agent.setStatus((byte)1);
		agent.setGender((byte)ValidateUtil.getGenderByCardId(agent.getIdNumber()));
		agent.setPassword(MD5Digest.getMD5Digest(agent.getPassword()));
		agent.setIsCrownAgent(2);//默认为普通经纪人
		return lfAgentWriteMapper.insertSelective(agent);
	}

    @Override
    public PageResponse getAgentList(AgentRequest req, LfEmployee employee) {
        PageResponse reponse = new PageResponse();
        req.setPageIndex(req.getPageIndex() * req.getPageSize());

//        List<Integer> agents = OrgCodeUtil.getEmployeeIdsByOrgIds(req.getCityId(), req.getAreaId(), req.getStoreId() ,agentSOAServer, lfAreaOrgReadMapper, employee);
//        req.setAgentIds(agents);

//        req.setPageIndex(req.getPageIndex() * req.getPageSize());
        List<Integer> agents = new ArrayList<Integer>();
        if (req.getAgentId() != null && !"".equals(req.getAgentId())) {

            agents.add(req.getAgentId());
            req.setAgentIds(agents);
        }else if (req.getStoreId() != null && !"".equals(req.getStoreId())) {
//            List<Integer> agents = new ArrayList<Integer>();
            List<Agent> agentCityList = agentSOAServer.getAgentList(null, null, req.getStoreId(), null, null);
            if (agentCityList == null || agentCityList.size()<= 0){
                agents.add(req.getAgentId());
                req.setAgentIds(agents);
            }else{
                for (Agent agentId : agentCityList){
                    agents.add(agentId.getId());

                }
                req.setAgentIds(agents);
            }
        }else if (req.getAreaId() != null && !"".equals(req.getAreaId())) {
            List<Agent> agentCityList = agentSOAServer.getAgentList(null, req.getAreaId(), null, null, null);
            if (agentCityList == null || agentCityList.size() <= 0){
                agents.add(req.getAgentId());
                req.setAgentIds(agents);
            }else{
                for (Agent agentId : agentCityList){
                    agents.add(agentId.getId());

                }
                req.setAgentIds(agents);
            }

        }else if(req.getCityId() != null && !"".equals(req.getCityId())) {
            int cityId = houseRead.findCityIdById(req.getCityId());
            req.setCityId(cityId);
            List<Agent> agentCityList = agentSOAServer.getAgentList(req.getCityId(), null, null, null, null);
            if (agentCityList == null || agentCityList.size() <= 0){
                agents.add(req.getAgentId());
                req.setAgentIds(agents);
            }else{
                for (Agent agentId : agentCityList){
                    agents.add(agentId.getId());
                }
                req.setAgentIds(agents);
            }
        }else if (employee.getCityId() == 1){

            List<Agent> agentCityList = agentSOAServer.getAgentList(null, null, null, null, null);
            for (Agent agentId : agentCityList){
                agents.add(agentId.getId());
            }
            req.setAgentIds(agents);
        }else{
            List<Agent> agentCityList = agentSOAServer.getAgentList(employee.getCityId(), null, null, null, null);
            for (Agent agentId : agentCityList){
                agents.add(agentId.getId());
            }
            req.setAgentIds(agents);
        }






        List<LfAgentAllInfo> agentlist =  lfAgentReadMapper.getAgentListByOrg(req);

//        List<Integer> paramList = new ArrayList<Integer>();
//        for (LfAgentAllInfo lfAgentAllInfo : agentlist) {
//            paramList.add(lfAgentAllInfo.getId().intValue());
//        }
//
//        //获取经纪人的level
//        if(agents != null) {
//            List<Agent> agentsoalist = agentSOAServer.getAgentsByIds(paramList);
//            for (LfAgentAllInfo lfAgentAllInfo : agentlist) {
//                lfAgentAllInfo.setLevel(40);
//                for (Agent agent : agentsoalist) {
//                    if (agent.getId().intValue() == lfAgentAllInfo.getId().intValue()) {
//                        if (agent.getStorePosition() == 1) {
//                            lfAgentAllInfo.setLevel(41);
//                        }
//                        break;
//                    }
//                }
//            }
//        }

        reponse.setData(agentlist);
        reponse.setTotal(lfAgentReadMapper.getAgentListByOrgCount(req));
        return reponse;
    }

    @Override
    public int updateAgent(LfAgent agent) {
        return lfAgentWriteMapper.updateByPrimaryKeySelective(agent);
    }

    @Override
    public int deleteAgentById(int id) {
        return lfAgentWriteMapper.deleteByPrimaryKey((long) id);
    }

    /**
     * 
     * 功能描述:上传头像
     * 
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     bianjie:   2015年4月27日      新建
     * </pre>
     */
    private ImageKeyObject uploadImg(byte[] img) {
        ImageKeyObject imgObj = null;
        if (img != null && img.length > 0)
            imgObj = ImageUtils.uploadImgToPublicBucket(imgSOAClient, img);
        return imgObj;
    }

    @Override
    public int getAgentCount(String mobile) {
        Map<String, Object> par = new HashMap<String, Object>();
        par.put("mobile", mobile);
        return lfAgentReadMapper.getAgentCount(par);
    }

    @Override
    public int updateAgent(LfAgent agent, byte[] img) {
        ImageKeyObject imgKey = uploadImg(img);
        if (imgKey != null) {
            logger.info("调用imageSOA上传头像，返回key={}", imgKey.getKey());
            agent.setPhotoHeadUrl(imgKey.getKey());
        } else {
            logger.info("调用imageSOA上传头像，返回为空");
        }
        agent.setGender((byte) ValidateUtil.getGenderByCardId(agent.getIdNumber()));
        return lfAgentWriteMapper.updateByPrimaryKeySelective(agent);
    }

    @Override
    public List<LfAgent> selectAgentListByPars(Map<String, Object> pars) {
        return lfAgentReadMapper.selectAgentListByPars(pars);
    }

    @Override
    public List<LfAgent> getAgentListByParms(int storeId) {
        Map<String, Object> pars = new HashMap<>();
        pars.put("storeId", storeId);
        return lfAgentReadMapper.selectAgentListByPars(pars);
    }

    @Override
    public Map<String, Object> getAgentRecentlySeeList(Map<String, Object> params, LfEmployee employee) {
        try{
            
            String empOrgCode = "";
            
            if(params.containsKey("code") && !"".equals(params.get("code")))
            {
                empOrgCode =  params.get("code").toString();
                
            }
            else
            {
                com.lifang.agentsoa.model.Employee empcode = agentSOAServer.getEmployee(employee.getId(), SystemInfo.OperationManagementSystem);
                empOrgCode = empcode.getCodes().get(0);
            }
            
            if(empOrgCode != null && empOrgCode != "")
            {
                List<Integer> agents = OrgCodeUtil.getEmployeeIdsByOrgCode(empOrgCode, agentSoa, lfAreaOrgReadMapper, employee);
                params.put("agents", agents);
            }
            
            
            int count = lfAgentReadMapper.getAgentRecentlySeeCount(params);
            
            
            
            
            List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
            if(count > 0){
                list = lfAgentReadMapper.getAgentRecentlySeeList(params);
                if(list!=null && list.size()>0){
                    for(int i=0;i<list.size();i++){
                        Map<String,Object> map = list.get(i);
                        //,c.gender,  a.realName AS agentName,store.name AS storeName
                        Agent agent = agentSoa.getAgent(Integer.valueOf(map.get("agentId").toString()));
                        if(agent != null){
                            map.put("agentName", agent.getName());
                            map.put("gender",agent.getGender());
                            map.put("storeName",agent.getStoreName());
                        }
                    }
                }
            }
    //        Map<String, Object> map = new HashMap<String, Object>();
            params.put("data", list);
            params.put("total", count);
        }catch (Exception e){
            e.printStackTrace();
        }
        return params;
    }
    
    @Override
    public List<MiniuiEntity> getAgentSimpleListByStoreId(Integer storeId) {
    	return lfAgentReadMapper.getAgentSimpleListByStoreId(storeId);
    }

    //查询经纪人评价信息
	@Override
	public Map<String, Object> getAgentEvalueteList(Map<String, Object> pars) {
		int count = lfAgentReadMapper.getAgentEvalueteCount(pars);
        List<LfAgentEvalueteInfo> list = new ArrayList<LfAgentEvalueteInfo>();
        if (count > 0) {
            list = lfAgentReadMapper.getAgentEvaluete(pars);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", list);
        map.put("total", count);
        return map;
	}
	
	
	    @Override
    public LfAgentAllInfo getAgentById(Integer id) {
        
        LfAgentAllInfo agentInfo = lfAgentReadMapper.getAgentById(id);
        String headKey = agentInfo.getPhotoHeadUrl();// 头像key
        if (!StringUtils.isEmpty(headKey)) {
            ImageKeyObject headImageObj = ImageUtils.getPublicUrlByKey(imgSOAClient, headKey);
            if (headImageObj != null)
                agentInfo.setPhotoHeadUrl(headImageObj.getOriginal());
        }
        return agentInfo;
    }

	@Override
	public Map<String, Object> getAgentLogList(Map<String, Object> pars) {
		
	    if (pars.get("agentId") != null && !"".equals(pars.get("agentId"))) {
	        List<Agent> agentCityList = new ArrayList<Agent>();
	        Agent a = new Agent();
	        a.setId(Integer.parseInt(pars.get("agentId").toString()));
	        agentCityList.add(a);
            pars.put("agentCityList", agentCityList);
        }else if(pars.get("storeId") != null && !"".equals(pars.get("storeId"))){
            List<Agent> agentCityList = agentSoa.getAgentList(null, null, Integer.parseInt(pars.get("storeId")+""), null, null);
            pars.put("agentCityList", agentCityList);
        }else if(pars.get("areaId") != null && !"".equals(pars.get("areaId"))){
            List<Agent> agentCityList = agentSoa.getAgentList(null, Integer.parseInt(pars.get("areaId")+""), null, null, null);
            pars.put("agentCityList", agentCityList);
        }else if(pars.get("cityId") != null && !"".equals(pars.get("cityId"))){
            
            int cityId = houseResourceReadMapper.findCityIdById(Integer.parseInt(pars.get("cityId")+""));
            
            List<Agent> agentCityList = agentSoa.getAgentList(cityId, null, null, null, null);
            pars.put("agentCityList", agentCityList);
        }else if (pars.get("agentCity") != null && Integer.parseInt(pars.get("agentCity")+"") == 1) {
          List<Agent> agentCityList = agentSoa.getAgentList(null, null, null, null, null);
          pars.put("agentCityList", agentCityList);
      }

        if(pars.get("agentCityList") == null){
            List<Agent> agentCityList = agentSoa.getAgentList(Integer.parseInt(pars.get("agentCity")+""), null, null, null, null);
            pars.put("agentCityList", agentCityList);
        }

		int count = lfAgentReadMapper.getAgentLogListCount(pars);
        List<LfAgentLogListInfo> list = new ArrayList<LfAgentLogListInfo>();
        if (count > 0) {
            list = lfAgentReadMapper.getAgentLogList(pars);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        
        List<Integer> agentIdList = new ArrayList<Integer>();
        for(LfAgentLogListInfo lu : list){
        	if(lu.getAgentId() != null && !"".equals(lu.getAgentId())){//视频上传人
        		agentIdList.add(Integer.parseInt(lu.getAgentId()));
			}
        }
        
        List<Agent> aList = agentSoa.getAgentsByIds(agentIdList);
        
        if(aList != null && aList.size() > 0){
			for(LfAgentLogListInfo lu : list){
				for(Agent a : aList){
					if(lu.getAgentId() != null && !"".equals(lu.getAgentId()) && a != null && a.getId() != null && !"".equals(a.getId()+"")){
						if(Integer.parseInt(lu.getAgentId()) == a.getId()){
							lu.setStoreName(a.getStoreName());
							lu.setName(a.getName());
							
						}
					}
				}
			}
        }
        
        map.put("data", list);
        map.put("total", count);
        return map;
	}

	@Override
	 public List<Employee> getAgentByStoreOrg(Map pars) {
        return lfAgentReadMapper.getAgentByStoreOrg(pars);
    }

}
