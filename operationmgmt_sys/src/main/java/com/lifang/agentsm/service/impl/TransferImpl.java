package com.lifang.agentsm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lifang.agentsm.dao.read.HouseResourceReadMapper;
import com.lifang.agentsm.dao.read.LfResourceTransLogReadMapper;
import com.lifang.agentsm.dao.read.TransferReadMapper;
import com.lifang.agentsm.dao.write.LfResourceTransLogWriteMapper;
import com.lifang.agentsm.dao.write.TransferWriteMapper;
import com.lifang.agentsm.entity.FranchiseeDic;
import com.lifang.agentsm.model.FranchiseeArea;
import com.lifang.agentsm.model.LfFranchiseeInfo;
import com.lifang.agentsm.model.LfFranchiseeStore;
import com.lifang.agentsm.model.LfTownInfo;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.model.PublicReport;
import com.lifang.agentsm.model.TransferResouce;
import com.lifang.agentsm.model.TransferResouceShow;
import com.lifang.agentsm.model.TransferStore;
import com.lifang.agentsm.service.TransferService;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.housesoa.facade.service.HouseService;
import com.lifang.housesoa.model.Response;
import com.lifang.hr.exception.BusinessException;
import com.lifang.hr.facade.EmployeeService;
import com.lifang.httplog.util.StringUtil;


/**
 * 
* @ClassName: TransferService 
* @Description: (资源转移service) 
* @author luogq
* @date 2015年10月20日 
*
 */
@Service
public class TransferImpl implements TransferService {

	@Autowired
	TransferReadMapper transferReadMapper;
	
	@Autowired
	TransferWriteMapper transferWriteMapper;
	
	@Autowired
	LfResourceTransLogReadMapper tfResourceTransLogReadMapper;
	
	@Autowired
	LfResourceTransLogWriteMapper tfResourceTransLogWriteMapper;
   @Autowired
    private HouseResourceReadMapper houseResourceReadMapper;
	
	@Autowired
	AgentSOAServer agentSOAServer;
	@Autowired
	HouseService houseService;
	@Autowired
	EmployeeService employeeService;

	@Override
	public Map<String, Object> findTransferList(Map<String, Object> pars) {
		//查处板块
		if("null".equals(pars.get("parentId")) || "".equals(pars.get("parentId"))){
			return  null;
		}else{
			//查找加盟商下面的所有板块 下的所有房子对应的发钥.... dic.id,dic.name 
//			List<LfTownInfo> lfTownList = transferReadMapper.findTransferList(pars);//板块,房源数量
			pars.put("parentIdIn", pars.get("parentId"));
			List<String> townIdInList = new ArrayList<String>();
//			if(pars.get("storeId") != null && pars.get("storeId") !="" && !"null".equals(pars.get("storeId"))){
//			    pars.put("parentIdIn", pars.get("storeId"));
			    townIdInList = transferReadMapper.findTownIdIn(pars);
//			}
			
			List<TransferResouceShow> tShows = new ArrayList<TransferResouceShow>();
			//板块名
			String tName = "";
			//查找房源数
			Integer hCount = 0;
            //小区数estateCount
            Integer eCount = 0;
            //客源 customerCount
            Integer cCount = 0;
            //未带看 cusSeeCount
            Integer seeCount = 0;
            //cusGuestCount 专属经纪人
            Integer guestCount = 0;
            //带看 houseSeeCount
            Integer hSeeCount = 0;
            //客户需求requirementCount
            Integer rCount = 0;
            //用来判断经纪人说会否重复
            List<Agent> agent = new ArrayList<Agent>();
//			//查找板块下面的经纪人
            TransferResouceShow trs = new TransferResouceShow();
			for (String townId : townIdInList) {
				
				List<Agent> agentIdIn = new ArrayList<Agent>();
				if(pars.get("storeId") != null && pars.get("storeId") !="" && !"null".equals(pars.get("storeId"))){
					agentIdIn = agentSOAServer.getAgentList(null, null, Integer.parseInt(pars.get("storeId")+""), null, Integer.parseInt(townId));
				}else {
					agentIdIn = agentSOAServer.getAgentList(null, Integer.parseInt(pars.get("parentId")+""), null, null, Integer.parseInt(townId));
				}
				Iterator<Agent> iterator = agentIdIn.iterator();
				for(Agent a : agent) {
				    while(iterator.hasNext()){
				        Agent agent2 = iterator.next();
				        if (a.getId().intValue() == agent2.getId()) {
                            iterator.remove();
                            break;
                        }
				    }
				}
				agent.addAll(agentIdIn);
				//获取板块名
//				select * from dic_area_new
				String townName = transferReadMapper.findTownIdById(townId);
//				trs.setTownname(townName);
				tName += townName + ",";
				        
				
				if (agentIdIn != null && agentIdIn.size() > 0) {
                
    				//查找房源数
                    pars.put("agentIdIn", agentIdIn);
                    Integer houseCount = transferReadMapper.findHouseCount(pars);
//                    trs.setHouseCount(houseCount);
                    hCount = hCount + houseCount;
    				//小区数estateCount
    				Integer estateCount = transferReadMapper.findEstateCount(pars);
//    				trs.setEstateCount(estateCount);
    				eCount = eCount + estateCount;
    				//客源 customerCount
    				Integer customerCount = transferReadMapper.findCustomerCount(pars);
//    				trs.setCustomerCount(customerCount);
    				cCount = cCount + customerCount;
    				//未带看 cusSeeCount
    				Integer cusSeeCount = transferReadMapper.findCusSeeCount(pars);
//    				trs.setCusSeeCount(cusSeeCount);
    				seeCount = seeCount + cusSeeCount;
    				//cusGuestCount 专属经纪人
    				Integer cusGuestCount = transferReadMapper.findCusGuestCount(pars);
//    				trs.setCusGuestCount(cusGuestCount);
    				guestCount = guestCount + cusGuestCount;
    				//带看 houseSeeCount
    				Integer houseSeeCount = transferReadMapper.findHouseSeeCount(pars);
//    				trs.setHouseSeeCount(houseSeeCount);
    				hSeeCount = hSeeCount + houseSeeCount;
    				
    				//客户需求requirementCount
    				Integer requirementCount = transferReadMapper.findRequirementCount(pars);
//    				trs.setRequirementCount(requirementCount);
    				rCount = rCount + requirementCount;
    				
				}
			}
			if (!"".equals(tName)){
				trs.setTownname(tName.substring(0,tName.length() - 1));
			}

			trs.setHouseCount(hCount);
			trs.setEstateCount(eCount);
			trs.setCustomerCount(cCount);
			trs.setCusSeeCount(seeCount);
			trs.setCusGuestCount(guestCount);
			trs.setHouseSeeCount(hSeeCount);
			trs.setRequirementCount(rCount);
			
			tShows.add(trs);
			Map<String, Object> map = new HashMap<String, Object>();
	        map.put("data", tShows);
	        map.put("total", tShows.size());
			return map;
		}
	}
	//加载小区,房源,客源
	
	@Override
	public Map<String, Object> findCustomerList(Map<String, Object> pars) {
		//查处板块
		if("null".equals(pars.get("parentId")) || "".equals(pars.get("parentId"))){
			return  null;
		}else{
			//查找小区

			List<LfTownInfo> lfTownList = transferReadMapper.findAllCountList(pars);//板块,房源数量
			
			Map<String, Object> map = new HashMap<String, Object>();
	        map.put("data", lfTownList);
	        map.put("total", lfTownList.size());
			return map;
		}
	}

	@Override
	public Map<String, Object> findFranchiseeList(Map<String, Object> pars) {

//		String code = transferReadMapper.findCode(pars);
//
//		pars.put("code",code);
//		if (code.length() < 4){
//			pars.put("code","");
//		}
		List<LfFranchiseeInfo> lfiList = new ArrayList<LfFranchiseeInfo>();
		int count = transferReadMapper.findFranchiseeListCount(pars);
		if (count > 0){
			lfiList = transferReadMapper.findFranchiseeList(pars);
		}
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", lfiList);
        map.put("total", count);
		return map;
	}

	@Override
	@Transactional
	public String saveFranchiseeInfo(Map<String, Object> pars) throws BusinessException{
		List<String> abbreviationList = transferReadMapper.findFranchinseeByName(pars);
		String abbreviation = pars.get("abbreviation")+"";
		if(abbreviationList.contains(abbreviation)){
			return "2";
		}
		String ids = pars.get("cityId")+"";
		
		Integer id = transferWriteMapper.insertSelective(pars);
		String keyString =  pars.get("id")+"";
		List<FranchiseeArea> fList = new ArrayList<FranchiseeArea>();
		String[] idStrings = ids.split("\\|");
		for(String s : idStrings){
		    if("".equals(s)){
		        continue;
		    }
			FranchiseeArea faArea = new FranchiseeArea();
			faArea.setCityId(s);
			faArea.setFranchiseeId(keyString);
			fList.add(faArea);
		}
		if(fList.size() > 0) {
			transferWriteMapper.insertFranchiseeAreaBatch(fList);
		}

		//开通人事账号
		/**
		 *   * @param mobile 手机号
		 * @param name 姓名
		 * @param franchiseeId 公司ID
		 * @param operateEmployeeId 操作员工ID
		 */

		if("".equals(pars.get("franchiseePhone")) || pars.get("franchiseePhone") == null){

		}else{
			employeeService.initWPHRAccount(pars.get("franchiseePhone")+"", abbreviation, Integer.parseInt(keyString), Integer.parseInt(pars.get("createBy") + ""));

		}

		//开始记录通过率
		transferWriteMapper.inserHouseAuto(keyString);
		System.out.println("***********************");
		return "1";
	}

	@Override
	public List<LfFranchiseeInfo> findFranchiseeById(Map<String, Object> pars) {
		List<LfFranchiseeInfo> lfiList = new ArrayList<LfFranchiseeInfo>();
		lfiList = transferReadMapper.findFranchiseeById(pars);
		for (LfFranchiseeInfo lf : lfiList){
			if ("".equals(lf.getFranchiseePhone()) || lf.getFranchiseePhone() == null){
				lf.setFranchiseePhone("0");
			}
		}
		
		List<FranchiseeArea> faAreaList = transferReadMapper.findFranchiseeArea(lfiList.get(0).getId());
		 String cityId = "";
		 String cityName = "";
		for(FranchiseeArea fa : faAreaList){
			cityId += fa.getCityId() + "|";

			cityName += fa.getCityName()+"|";
		}



//		lfiList.get(0).setAreaId(areaId);
//		lfiList.get(0).setAreaName(areaName);

		lfiList.get(0).setCityId(cityId);
		lfiList.get(0).setCityName(cityName);

		return lfiList;
	}

	@Override
	@Transactional
	public String updateFranchiseeInfo(Map<String, Object> pars) throws BusinessException{
		String result = "";
		String ids = pars.get("cityId")+"";
		String[] idStrings = ids.split("\\|");

		LfFranchiseeInfo lf = transferReadMapper.findFranchiseeById(pars).get(0);

		if("".equals(lf.getFranchiseePhone()) || lf.getFranchiseePhone() == null){
			if("".equals(pars.get("franchiseePhone")) || pars.get("franchiseePhone") == null){

			}else{
				employeeService.initWPHRAccount(pars.get("franchiseePhone")+"", pars.get("abbreviation")+"", Integer.parseInt(pars.get("id")+""), Integer.parseInt(pars.get("createBy") + ""));

			}
		}

		//删除已经存在的区域
		transferWriteMapper.deleteFranchiseeAreaById(pars.get("id")+"");



		transferWriteMapper.updateFranchiseeInfo(pars);

		//插入城市
		List<FranchiseeArea> fList = new ArrayList<FranchiseeArea>();
//		String[] idStrings = ids.split("\\|");
		for(String s : idStrings){
			FranchiseeArea faArea = new FranchiseeArea();
			faArea.setCityId(s);
			faArea.setFranchiseeId(pars.get("id")+"");
			fList.add(faArea);
		}
		transferWriteMapper.insertFranchiseeAreaBatch(fList);
		result = "1";
		return result;
	}

	@Override
	public Map<String, Object> findFranchiseeStoreList(Map<String, Object> pars) {
		List<LfFranchiseeStore> lfsList = new ArrayList<LfFranchiseeStore>();
		int count = transferReadMapper.findFranchiseeStoreCount(pars);
		if(count > 0){
			lfsList = transferReadMapper.findFranchiseeStoreList(pars);
		}
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", lfsList);
        map.put("total", count);
		return map;
	}

	@Override
	public String findAreaOrg(Map<String, Object> pars) {
		
		return transferReadMapper.findAreaOrg(pars);
	}

	@Override
	public void saveFranchiseeStore(Map<String, Object> pars) {
		int count = transferReadMapper.findFranchiseeStoreById(pars);
		if(count > 0){
			transferWriteMapper.updateFranchiseeStore(pars);
		}else{
			transferWriteMapper.saveFranchiseeStore(pars);
		}
	}

	@Override
	@Transactional
	public String transferResouce(Map<String, Object> pars) {
		boolean result = false;
		//查找所有转入到的townID --此id全局共享
		List<String> townIdInList = transferReadMapper.findTownIdIn(pars);
		if (townIdInList == null || townIdInList.size() <= 0){
			return "6";
		}
		//查出所有转出人的townid
		List<String> townIdOutList = transferReadMapper.findTownIdOutList(pars);
		if(townIdOutList == null || townIdOutList.size() <= 0){
			return "3";
		}
		
		//
		List<String> townList = new ArrayList<String>();
		//查找需要转出的 townid    
		for(String inId : townIdInList){
			for(String outId : townIdOutList){
				if(inId.equals(outId)){
					townList.add(inId);
					continue;
				}
			}
		}
//		过滤一个人属于多个板块
		 //用来判断经纪人说会否重复
        Set<Agent> agentInSet = new HashSet<Agent>();
        Set<Agent> agentOutSet = new HashSet<Agent>();
        List<Agent> agentIdIn = new ArrayList<Agent>();
        List<Agent> agentIdOut = new ArrayList<Agent>();

		for (String townId : townIdInList) {
            
            if(pars.get("storeIdIn") != null && pars.get("storeIdIn") !="" && !"null".equals(pars.get("storeIdIn"))){
                agentIdIn = agentSOAServer.getAgentList(null, null, Integer.parseInt(pars.get("storeIdIn")+""), null, Integer.parseInt(townId));
            }else {
                agentIdIn = agentSOAServer.getAgentList(null, Integer.parseInt(pars.get("areaIdIn")+""), null, null, Integer.parseInt(townId));
            }
           
            if(agentIdIn == null || agentIdIn.size() <= 0){
                //return "5";
                continue;
            }
            agentInSet.addAll(agentIdIn);
            
            agentIdIn.clear();
            
            
            if(pars.get("storeId") != null && pars.get("storeId") !="" && !"null".equals(pars.get("storeId")))
            {
                agentIdOut = agentSOAServer.getAgentList(null, null, Integer.parseInt(pars.get("storeId")+""), null, Integer.parseInt(townId));
            }else{
                agentIdOut = agentSOAServer.getAgentList(null, Integer.parseInt(pars.get("areaId")+""), null, null, Integer.parseInt(townId));
            }
            
            
            if(agentIdOut == null || agentIdOut.size() <= 0){
                return "3";
            }
            agentOutSet.addAll(agentIdOut);
            agentIdOut.clear();
            
		}
		agentIdIn.addAll(agentInSet);
        pars.put("agentIdIn", agentIdIn);
        agentIdOut.addAll(agentOutSet);

		if(agentIdIn == null || agentIdIn.size() <= 0){
			return "5";
		}
        pars.put("agentIdOut",agentIdOut);
		
//			pars.put("agentIdOut",agentIdOut);
			String type = pars.get("type")+"";
			if(type.contains("1")) {//表示勾选了 房源转移
				//1.  `publishAgent` '发布经纪人’  转出人
				List<TransferResouce> transferPublishInList = transferReadMapper.findPublishInList(pars);
				
				if (transferPublishInList != null && transferPublishInList.size() > 0) {
					int j = 0;
					for (int i = 0; i < transferPublishInList.size(); i++) {
						if (j == agentIdOut.size()) {
							j = 0;
						}
						transferPublishInList.get(i).setPublishAgent(agentIdOut.get(j).getId()+"");
						Agent a = agentSOAServer.getAgent(agentIdOut.get(j).getId());
						Agent b = agentSOAServer.getAgent(Integer.parseInt(transferPublishInList.get(i).getPublishAgentIn()));
						transferPublishInList.get(i).setFranchiseeAreaOrgId(a.getFranchiseeId());
						transferPublishInList.get(i).setCreateBy(pars.get("createBy")+"");
						//发布人转移接口
						try{
						    System.out.println("houseService:"+houseService);
						    System.out.println("请求接口参数:transferPublishInList.get(i).getPublishAgentIn():"+transferPublishInList.get(i).getPublishAgentIn()+"***b.getFranchiseeId():"+b.getFranchiseeId()+"***agentIdOut.get(j).getId()"+agentIdOut.get(j).getId()+"****a.getFranchiseeId():"+a.getFranchiseeId());
    						Response response = houseService.resourceTransfer(Integer.parseInt(transferPublishInList.get(i).getPublishAgentIn()), b.getFranchiseeId(), agentIdOut.get(j).getId(), a.getFranchiseeId());
    						System.out.println("方友慧接口返回:"+response);
						}catch(Exception e){
						    System.out.println("已进入exception *************************************************************************************");
						    e.printStackTrace();
						}
						j++;
					}
					//插入数据库
					transferWriteMapper.updatePublishAgentBath(transferPublishInList);
					//日志记录
					//类型1发布人2钥匙人呢3实景人4速销人5维护人6客户7约看8小区
					tfResourceTransLogWriteMapper.insertPublishAgentLog(transferPublishInList);
					result = true;
				}
	
	//			2.实景人转移
					//查出转出人(实景人)对应的房源信息
	
					List<TransferResouce> transferPictureAgentInList = transferReadMapper.findPictureAgentList(pars);
					//开始转移实景人
					if (transferPictureAgentInList != null && transferPictureAgentInList.size() > 0) {
						//将转出人的资源对应转入人
						int j = 0;
						for (int i = 0; i < transferPictureAgentInList.size(); i++) {
							if (j == agentIdOut.size()) {
								j = 0;
							}
							transferPictureAgentInList.get(i).setPictureAgent(agentIdOut.get(j).getId()+"");
							transferPictureAgentInList.get(i).setCreateBy(pars.get("createBy")+"");
							j++;
						}
						//插入数据库
						transferWriteMapper.updatePictureAgentBath(transferPictureAgentInList);
						//日志记录
						//类型1发布人2钥匙人呢3实景人4速销人5维护人6客户7约看8小区
						tfResourceTransLogWriteMapper.insertPictureAgentLog(transferPictureAgentInList);
						result = true;
					}
	//				//CommAgent
	//				//2.速消人转移
	//				//查出转出人(速消人)对应的房源信息
	//
					List<TransferResouce> transferCommAgentInList = new ArrayList<TransferResouce>();
					transferCommAgentInList = transferReadMapper.findCommAgentList(pars);
		//开始转移
					if (transferCommAgentInList != null && transferCommAgentInList.size() > 0) {
						//将转出人的资源对应转入人
						int j = 0;
						for (int i = 0; i < transferCommAgentInList.size(); i++) {
							if (j == agentIdOut.size()) {
								j = 0;
							}
							transferCommAgentInList.get(i).setCommAgent(agentIdOut.get(j).getId()+"");
							transferCommAgentInList.get(i).setCreateBy(pars.get("createBy")+"");
							j++;
						}
						//插入数据库
						transferWriteMapper.updateCommAgentBath(transferCommAgentInList);
						//日志记录
						//类型1发布人2钥匙人呢3实景人4速销人5维护人6客户7约看8小区
						tfResourceTransLogWriteMapper.insertCommAgentLog(transferCommAgentInList);
						
						result = true;
					}
					
					//maintainAgent
					//2.维护人转移
					//查出转出人(维护人)对应的房源信息
	
					List<TransferResouce> transferMaintainAgentInList = new ArrayList<TransferResouce>();
					transferMaintainAgentInList = transferReadMapper.findMaintainAgentList(pars);
		//开始转移
					if (transferMaintainAgentInList != null && transferMaintainAgentInList.size() > 0) {
						//将转出人的资源对应转入人
						int j = 0;
						for (int i = 0; i < transferMaintainAgentInList.size(); i++) {
							if (j == agentIdOut.size()) {
								j = 0;
							}
							transferMaintainAgentInList.get(i).setMaintainAgent(agentIdOut.get(j).getId()+"");
							transferMaintainAgentInList.get(i).setCreateBy(pars.get("createBy")+"");
							j++;
						}
						//插入数据库
						transferWriteMapper.updateMaintainAgentBath(transferMaintainAgentInList);
						//日志记录
						//类型1发布人2钥匙人呢3实景人4速销人5维护人6客户7约看8小区
						tfResourceTransLogWriteMapper.insertMaintainAgentLog(transferMaintainAgentInList);
						result = true;
					}
				
					//钥匙人转移
					//查处转出人(钥匙人)对应的房源id
					List<TransferResouce> transferAgentIdInList = new ArrayList<TransferResouce>();
					transferAgentIdInList = transferReadMapper.findtransferKeyAgentInList(pars);
				//钥匙人
					if (transferAgentIdInList != null && transferAgentIdInList.size() > 0) {
						//将转出人的资源对应转入人
						int j = 0;
						for (int i = 0; i < transferAgentIdInList.size(); i++) {
							if (j == agentIdOut.size()) {
								j = 0;
							}
							transferAgentIdInList.get(i).setKeyAgent(agentIdOut.get(j).getId()+"");
							transferAgentIdInList.get(i).setCreateBy(pars.get("createBy")+"");
							j++;
						}
						//插入数据库
						transferWriteMapper.updateKeyAgentBath(transferAgentIdInList);
						//日志记录
						//类型1发布人2钥匙人呢3实景人4速销人5维护人6客户7约看8小区
						tfResourceTransLogWriteMapper.insertKeyAgentLog(transferAgentIdInList);
						result = true;
					}
			}
			/****************************************************************************************************************************************************/
			//客源转移lf_customer
			//查处转出人对应的客源
			if(type.contains("2")) {
				List<TransferResouce> transferCustomerList = new ArrayList<TransferResouce>();
				transferCustomerList = transferReadMapper.findCustomerList(pars);
	
				//转入
				if (transferCustomerList != null && transferCustomerList.size() > 0) {
					//将转出人的资源对应转入人
					int j = 0;
					for (int i = 0; i < transferCustomerList.size(); i++) {
						if (j == agentIdOut.size()) {
							j = 0;
						}
						transferCustomerList.get(i).setCustomer(agentIdOut.get(j).getId()+"");
						transferCustomerList.get(i).setCreateBy(pars.get("createBy")+"");
						j++;
					}
					//插入数据库
					transferWriteMapper.updateCustomerBath(transferCustomerList);
					//日志记录
					//类型1发布人2钥匙人呢3实景人4速销人5维护人6客户7约看8小区
					tfResourceTransLogWriteMapper.insertCustomerLog(transferCustomerList);
					result = true;
				}
	
			}
			
			/****************************************************************************************************************************************************/
			//小区转移lf_agent_estate
			//查处转出人对应的小区
			if(type.contains("3")) {
				List<TransferResouce> transferEstateList = new ArrayList<TransferResouce>();
				transferEstateList = transferReadMapper.findEstateList(pars);
	
				//转入
				if (transferEstateList != null && transferEstateList.size() > 0) {
					//将转出人的资源对应转入人
					int j = 0;
					for (int i = 0; i < transferEstateList.size(); i++) {
						if (j == agentIdOut.size()) {
							j = 0;
						}
						transferEstateList.get(i).setEstate(agentIdOut.get(j).getId()+"");
						transferEstateList.get(i).setCreateBy(pars.get("createBy")+"");
						j++;
					}
					//插入数据库
					transferWriteMapper.updateEstateBath(transferEstateList);
					//日志记录
					//类型1发布人2钥匙人呢3实景人4速销人5维护人6客户7约看8小区
					tfResourceTransLogWriteMapper.insertEstateLog(transferEstateList);
					result = true;
				}
			}
			/****************************************************************************************************************************************************/
			//带看转移cus_see_house_order
			//查处转出人对应的资源
			if(type.contains("4")) {
				List<TransferResouce> transferCusSeeList = new ArrayList<TransferResouce>();
				transferCusSeeList = transferReadMapper.findCusSeeList(pars);

				//转入
				if (transferCusSeeList != null && transferCusSeeList.size() > 0) {
					//将转出人的资源对应转入人
					int j = 0;
					for (int i = 0; i < transferCusSeeList.size(); i++) {
						if (j == agentIdOut.size()) {
							j = 0;
						}
						transferCusSeeList.get(i).setCusSee(agentIdOut.get(j).getId()+"");
						transferCusSeeList.get(i).setCreateBy(pars.get("createBy")+"");
						j++;
					}
					//插入数据库
					transferWriteMapper.updateCusSeeBath(transferCusSeeList);
					//日志记录
					//类型1发布人2钥匙人3实景人4速销人5维护人6客户7约看8小区
					tfResourceTransLogWriteMapper.insertCusSeeLog(transferCusSeeList);
					result = true;
				}
			}
			//专属经纪人转移 cus_guest_appoint_agent 专属经纪人表
			if(type.contains("5")) {
				List<TransferResouce> cusGuestList = new ArrayList<TransferResouce>();
				cusGuestList = transferReadMapper.findCusGuestList(pars);
				//
				if (cusGuestList != null && cusGuestList.size() > 0) {
					int j = 0;
					for (int i = 0; i < cusGuestList.size(); i++) {
						if (j == agentIdOut.size()) {
							j = 0;
						}
						cusGuestList.get(i).setCusGuest(agentIdOut.get(j).getId()+"");
						cusGuestList.get(i).setCreateBy(pars.get("createBy")+"");
						j++;
					}
					//插入数据库
					transferWriteMapper.updateCusGuestBath(cusGuestList);
					//日志记录
					//类型1发布人2钥匙人3实景人4速销人5维护人6客户7约看8小区9客户需求10带看,11专属经济人
					tfResourceTransLogWriteMapper.insertCusGuestLog(cusGuestList);
					result = true;
				}
				
			}
			//已带看 lf_customer_house_see
			if(type.contains("6")) {
				List<TransferResouce> houseSeeList = new ArrayList<TransferResouce>();
				houseSeeList = transferReadMapper.findHouseSeeList(pars);
				//
				if (houseSeeList != null && houseSeeList.size() > 0) {
					int j = 0;
					for (int i = 0; i < houseSeeList.size(); i++) {
						if (j == agentIdOut.size()) {
							j = 0;
						}
						houseSeeList.get(i).setHouseSee(agentIdOut.get(j).getId()+"");
						houseSeeList.get(i).setCreateBy(pars.get("createBy")+"");
						j++;
					}
					//插入数据库
					transferWriteMapper.updateHouseSeeBath(houseSeeList);
					//日志记录
					//类型1发布人2钥匙人3实景人4速销人5维护人6客户7约看8小区9客户需求10带看,11专属经济人
					tfResourceTransLogWriteMapper.insertHouseSeeLog(houseSeeList);
					result = true;
				}
			}
			//客户需求 lf_customer_requirement
			if(type.contains("7")) {
				List<TransferResouce> requirementList = new ArrayList<TransferResouce>();
				requirementList = transferReadMapper.findRequirementListList(pars);
				//
				if (requirementList != null && requirementList.size() > 0) {
					int j = 0;
					for (int i = 0; i < requirementList.size(); i++) {
						if (j == agentIdOut.size()) {
							j = 0;
						}
						requirementList.get(i).setRequirement(agentIdOut.get(j).getId()+"");
						requirementList.get(i).setCreateBy(pars.get("createBy")+"");
						j++;
					}
					//插入数据库
					transferWriteMapper.updateRequirementBath(requirementList);
					//日志记录
					//类型1发布人2钥匙人3实景人4速销人5维护人6客户7约看8小区9客户需求10带看,11专属经济人
					tfResourceTransLogWriteMapper.insertRequirementLog(requirementList);
					result = true;
				}
			}
			
//		}
		if(result){
			return "1"; 
		}else{
			return "4";
		}
	}

	@Override
	public List<TransferStore> findNameById(Map<String, Object> pars) {
		return transferReadMapper.findNameById(pars);
	}

	@Override
	public List<FranchiseeDic> getAreaByCityId(Map<String, Object> pars) {
		
		return transferReadMapper.getAreaByCityId(pars);
	}

	@Override
	public List<FranchiseeDic> getData2AreaByAreaId(List<String> list) {
		
		return transferReadMapper.getData2AreaByAreaId(list);
	}

	/**
	 * @see com.lifang.agentsm.service.TransferService#getSimpleFranchiseeListByCityId(int)
	 */
	@Override
	public List<MiniuiEntity> getSimpleFranchiseeListByCityId(int cityId) {
		return transferReadMapper.getSimpleFranchiseeListByCityId(cityId);
	}

    @Override
    public void updatePush(Map<String, Object> pars) {
        transferWriteMapper.updatePush(pars);
        
    }

    @Override
    public Map<String, Object> getPubReportList(Map<String, Object> pars) {
        
        if ( pars.get("agentId") != null && !StringUtil.isEmpty(pars.get("agentId")+"")) {
            List<Agent> agentCityList = new ArrayList<Agent>();
            Agent agent = new Agent();
            agent.setId(Integer.parseInt(pars.get("agentId")+""));
            agentCityList.add(agent);
            pars.put("agentIdList", agentCityList);
        }else if (pars.get("storeCode") != null && !StringUtil.isEmpty(pars.get("storeCode")+"")) {
            
            List<Agent> agentCityList = agentSOAServer.getAgentList(null, null, Integer.parseInt(pars.get("storeCode")+""), null, null);
            if (agentCityList != null && agentCityList.size() == 0) {
                List<Agent> agentlAgents = new ArrayList<Agent>();
                Agent agent = new Agent();
                agent.setId(-1);
                agentlAgents.add(agent);
                pars.put("agentIdList", agentlAgents);
            }else {
                
                pars.put("agentIdList", agentCityList);
            }
            
        }else if (pars.get("areaCode") != null && !StringUtil.isEmpty(pars.get("areaCode")+"") ) {
//            int areaId = houseResourceReadMapper.findCityIdById(Integer.parseInt(req.getAreaCode()));
            
            
            List<Agent> agentCityList = agentSOAServer.getAgentList(null, Integer.parseInt(pars.get("areaCode")+""), null, null, null);
            if (agentCityList != null && agentCityList.size() == 0) {
                List<Agent> agentlAgents = new ArrayList<Agent>();
                Agent agent = new Agent();
                agent.setId(-1);
                agentlAgents.add(agent);
                pars.put("agentIdList", agentlAgents);
            }else {
                pars.put("agentIdList", agentCityList);
                
            }
            
            
        }else if (pars.get("cityCode") != null && !StringUtil.isEmpty(pars.get("cityCode")+"")) {
            
            int cityId = houseResourceReadMapper.findCityIdById(Integer.parseInt(pars.get("cityCode")+""));
            
            List<Agent> agentCityList = agentSOAServer.getAgentList(cityId, null, null, null, null);
            pars.put("agentIdList", agentCityList);
        }else if(Integer.parseInt(pars.get("cityId")+"") == 1){
            List<Agent> agentCityList = agentSOAServer.getAgentList(null, null, null, null, null);
            pars.put("agentIdList", agentCityList);
        }else {
            List<Agent> agentCityList = agentSOAServer.getAgentList(Integer.parseInt(pars.get("cityId")+""), null, null, null, null);
            pars.put("agentIdList", agentCityList);
        }
    
    if (pars.get("agentIdList") == null || pars.get("agentIdList") == "") {
        List<Agent> agentCityList = new ArrayList<Agent>();
        Agent agent = new Agent();
        agent.setId(-1);
        agentCityList.add(agent);
        pars.put("agentIdList", agentCityList);
    }
    
    Map<String, Object> map = new HashMap<String, Object>();
    int count = transferReadMapper.findPubReportCount(pars);
    List<PublicReport> prList = new ArrayList<PublicReport>(); 
    PublicReport pre = new PublicReport();
    if(count > 0){
        prList = transferReadMapper.findPubReport(pars);
        //计算总算
        pre = transferReadMapper.findPubReportByParm(pars);
    }
    
    List<Integer> agentId = new ArrayList<Integer>();
    for(PublicReport p : prList ){
        agentId.add(Integer.parseInt(p.getAgentId()));
    }
//    List<Agent> aList = agentSOAServer.getAgentTownsByIds(agentId);
    List<Agent> aList = agentSOAServer.getAgentsByIds(agentId);//agentSOAServer.getEmployeePositionInfoByIds(agentId,SystemInfo.BusinessManagementSystem);
    
    
    for(PublicReport p : prList ){
        for(Agent a : aList){
            if(p.getAgentId().equals(a.getId()+"")){
                p.setCityName(a.getCityName());
                p.setAreaName(a.getAreaOrgName());
                p.setStoreName(a.getStoreName());
                p.setAgentName(a.getName());
                continue;
            }
        }
    }
    pre.setReportDate("合计");
    prList.add(pre);
    map.put("data", prList);
    map.put("total", count);
    return map;
    }

	@Override
	public boolean transferResouce(String fransferId) {
		boolean result = false;
		Map<String, Object> pars = new HashMap<String,Object>();
		pars.put("areaId",fransferId);

		System.out.println("传进的加盟商区域id为:"+fransferId+"***********************");

		List<String> areaList = transferReadMapper.findAreaById(fransferId);
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

	@Override
	public String findIdByCityId(Map<String, Object> pars) {
		return transferReadMapper.findIdByCityId(pars);
	}


}
