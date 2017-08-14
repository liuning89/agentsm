package com.lifang.agentsm.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lifang.agentsm.dao.read.HouseImageReadMapper;
import com.lifang.agentsm.dao.write.HouseImageWriteMapper;
import com.lifang.agentsm.dao.write.LfSellHouseAgentWriteMapper;
import com.lifang.agentsm.entity.HouseImage;
import com.lifang.agentsm.entity.HouseImageAudit;
import com.lifang.agentsm.entity.HouseInfo;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.entity.SellhouseReturn;
import com.lifang.agentsm.model.HouseImageAuditInfo;
import com.lifang.agentsm.model.LfAgentOperation;
import com.lifang.agentsm.service.HouseImageService;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.agentsoa.model.Employee;
import com.lifang.agentsoa.model.enums.SystemInfo;
import com.lifang.housesoa.facade.service.HouseService;
import com.lifang.housesoa.model.HouseDetailInfo;
import com.lifang.housesoa.model.ReloadEnum;
import com.lifang.httplog.util.StringUtil;
import com.lifang.imgsoa.facade.ImageService;
import com.lifang.imgsoa.model.ImageKeyObject;
import com.lifang.model.enums.AppEnum;


/**
 * 
* @ClassName: HouseImageService 
* @Description: TODO(房源图片service) 
* @author chenqiang
* @date 2015年5月29日 下午2:38:55 
*
 */
@Service
public class HouseImageImpl implements HouseImageService {

    @Autowired
    private HouseService houseRentClient;
    
    @Autowired
    private ImageService imgSOAClient;
    
    @Autowired
    private HouseImageWriteMapper houseImageWriteMapper;
    
    @Autowired
    private HouseImageReadMapper houseImageReadMapper;
    
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
    public int updateImageType(HouseImage houseImage) {
        //如果设置为首页那么先删除首页
        if(houseImage.getType() != null && houseImage.getType().intValue() == FIRSTPAGEINDEX)
        {
            HouseImage houseImagetemp = new HouseImage();
            houseImagetemp.setType(FIRSTPAGEINDEX);
            houseImagetemp.setStatus(0);
            houseImagetemp.setHouseId(houseImage.getHouseId());
            
            //获取首页图片的数量
            int firstPageImageCount = houseImageReadMapper.selectFirstPageImageCount(houseImage.getHouseId());
            
            //更新图片的status = 0
            houseImageWriteMapper.updateByTypeSelective(houseImagetemp);
            
            if(firstPageImageCount != 0)
            {
                //更新有效图片数
                Map<String,Object> parMap =  new HashMap<String, Object>();
                parMap.put("count", 0 - firstPageImageCount);
                parMap.put("houseId", houseImage.getHouseId());
                houseImageWriteMapper.updateHouseInfoPicNum(parMap);
            }
        }
        houseImageWriteMapper.updateByKeySelective(houseImage);
        //清理缓存
        List<Integer> houseIdList = new ArrayList<Integer>();
        houseIdList.add(houseImage.getHouseId());
        houseRentClient.reloadMemcachedDetail(ReloadEnum.houseresource, houseIdList);
        return 1;
    }

    @Override
    public int removeHouseImage(String key,Integer houseId) {
        HouseImage houseImage = new HouseImage();
        houseImage.setImgKey(key);
        houseImage.setStatus(0);
        //更新图片的status = 0
        houseImageWriteMapper.updateByKeySelective(houseImage);
        //更新有效图片数
        Map<String,Object> parMap =  new HashMap<String, Object>();
        parMap.put("count", -1);
        parMap.put("houseId", houseId);
        houseImageWriteMapper.updateHouseInfoPicNum(parMap);
        //清理缓存
        houseRentClient.reloadMemcachedDetail(ReloadEnum.houseresource, Arrays.asList(houseId));
        return 1;
    }

    @Override
    public HouseImage uploadSingleFile(MultipartFile file, LfEmployee employee, Map<String, Object> pars) {
        ImageKeyObject imgKey = null;
        Integer houseId = (Integer) pars.get("houseId");
        Integer type = (Integer) pars.get("type");
        try {
            imgKey = imgSOAClient.uploadSingleFile(file.getBytes(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(imgKey != null)
        {
            
            int firstPageImageCount = 0;
            //如果设置为首页那么先删除首页
            if(type != null && type.intValue() == FIRSTPAGEINDEX)
            {
                HouseImage houseImagetemp = new HouseImage();
                houseImagetemp.setType(FIRSTPAGEINDEX);
                houseImagetemp.setStatus(0);
                houseImagetemp.setHouseId(houseId);
                //获取首页图片的数量
                firstPageImageCount = houseImageReadMapper.selectFirstPageImageCount(houseId);
                //更新图片的status = 0
                houseImageWriteMapper.updateByTypeSelective(houseImagetemp);
            }
            
            HouseImage record = new HouseImage();
            record.setAgentId(employee.getId().longValue());
            record.setHouseId((Integer) pars.get("houseId"));
            record.setImgKey(imgKey.getKey());
            record.setType((Integer) pars.get("type"));
            record.setStatus(1);
            houseImageWriteMapper.insertSelective(record);
            
            //更新图片的数量
            Map<String,Object> parMap =  new HashMap<String, Object>();
            parMap.put("count", 1 - firstPageImageCount);
            parMap.put("houseId", houseId);
            houseImageWriteMapper.updateHouseInfoPicNum(parMap);
            
            //清理缓存
            List<Integer> houseIdList = new ArrayList<Integer>();
            houseIdList.add(houseId);
            houseRentClient.reloadMemcachedDetail(ReloadEnum.houseresource, houseIdList);
            //返回数据
            return houseImageReadMapper.selectByPrimaryKey(record.getId());
        }
        return null;
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
	public Map<String, Object> getImageAuditList(Map<String, Object> pars) {
//		String code = "";
//		if("".equals(pars.get("agentCode"))){
//			code = "";
//		}else{
//			code = houseImageReadMapper.findCode(pars);
//		}
//		 
//		
//		pars.put("code",code);
		if(pars.get("agentCity") != null && Integer.parseInt(pars.get("agentCity")+"") != 1){
			List<Agent> agentCityList = agentSoa.getAgentList(Integer.parseInt(pars.get("agentCity")+""), null, null, null, null);
	        pars.put("agentCityList", agentCityList);
		}else if (pars.get("agentCity") != null && Integer.parseInt(pars.get("agentCity")+"") == 1) {
			List<Agent> agentCityList = agentSoa.getAgentList(null, null, null, null, null);
	        pars.put("agentCityList", agentCityList);
		}
		int count = houseImageReadMapper.getImageAuditListCount(pars);
        List<HouseImageAuditInfo> list = new ArrayList<HouseImageAuditInfo>();
        if (count > 0) {
            list = houseImageReadMapper.getImageAuditList(pars);
        }
        
      //根据agentid获取经纪人姓名和门店  上传人
        List<Integer> agentIdList = new ArrayList<Integer>();
        for(HouseImageAuditInfo lu : list){
        	if(lu.getAgentId() != null && !"".equals(lu.getAgentId())){//上传人
        		agentIdList.add(Integer.parseInt(lu.getAgentId()));
			}
        }
        
        List<Agent> aList = agentSoa.getAgentsByIds(agentIdList);
        
        if(aList != null && aList.size() > 0){
			for(HouseImageAuditInfo lu : list){
				for(Agent a : aList){
				    if(!StringUtil.isEmpty(lu.getAgentId()) && !StringUtil.isEmpty(a.getId()+""))
				    {
//					if(lu.getAgentId() != null && !"".equals(lu.getAgentId()) && a != null && a.getId() != null && !"".equals(a.getId())){
						if(Integer.parseInt(lu.getAgentId()) == a.getId()){
							lu.setDeptName(a.getStoreName());
							lu.setRealName(a.getName());
							lu.setMobile(a.getMobile());
						}
					}
				}
			}
        }
        
      //审核人id
        List<Integer> auditoridList = new ArrayList<Integer>();

        for(HouseImageAuditInfo lu : list){
			if(lu.getEmppId() != null && !"".equals(lu.getEmppId())){
				auditoridList.add(Integer.parseInt(lu.getEmppId()));
			}
		}
        List<Employee> auditorAgentList = agentSoa.getEmployee(auditoridList,SystemInfo.OperationManagementSystem);
        
        if(auditorAgentList != null && auditorAgentList.size() > 0){
        	for(HouseImageAuditInfo lu : list){
        		for(Employee a : auditorAgentList){
        		    
        		    if(!StringUtil.isEmpty(lu.getEmppId()) && a != null && !StringUtil.isEmpty(a.getId()+"")){
//        			if(lu.getEmppId() != null && !"".equals(lu.getEmppId()) && a != null && a.getId() != null && !"".equals(a.getId())){
        				if(Integer.parseInt(lu.getEmppId()) == a.getId()){
							lu.setLoginName(a.getName());
//							lu.setDeptName(a.getStoreName());
						}
        			}
        		}
        	}
        }
        
        
        //获取部门
//    	List<Integer> positionList = new ArrayList<Integer>();
//		for(HouseImageAuditInfo lu : list){
//			if(lu.getAgentpId() != null && !"".equals(lu.getAgentpId())){
//				positionList.add(Integer.parseInt(lu.getAgentpId()));
//			}
//		}
//		
//		List<PositionCache> pcList = apc.getShowNameByPositionIds(positionList);
//		
//		if(pcList != null && pcList.size() > 0){
//			for(HouseImageAuditInfo lu : list){
//
//				for(PositionCache pc : pcList){
//					if(lu.getAgentpId() != null && !"".equals(lu.getAgentpId()) && pc != null && pc.getPersonPositionId() != null && !"".equals(pc.getPersonPositionId())){
//						if(Integer.parseInt(lu.getAgentpId()) == pc.getPersonPositionId()){
//		//					lu.setAreaName(pc.getAreaOrg());
//		                    lu.setDeptName(pc.getDoorAreaOrg());
//		                    break;
//						}
//					}
//				}
//			}
//		}
        
        
        
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", list);
        map.put("total", count);
        return map;
	}


	@Override
	public List<HouseImageAudit> selectImageByhouseId(Map<String, Object> pars) {
		
		List<HouseImageAudit> hImageList = new ArrayList<HouseImageAudit>();
		
		List<HouseImageAudit> hiList = houseImageReadMapper.setectImage(pars);
		ImageKeyObject obj = null;
		for(HouseImageAudit hi : hiList){
			HouseImageAudit hImage = new HouseImageAudit();
			hImage.setAgentId(hi.getAgentId());
			hImage.setCreateTime(hi.getCreateTime());
			hImage.setDesc(hi.getDesc());
			hImage.setHouseId(hi.getHouseId());
			hImage.setId(hi.getId());
			hImage.setSourceId(hi.getSourceId());
			hImage.setStatus(hi.getStatus());
			hImage.setTakePhotoTime(hi.getTakePhotoTime());
			hImage.setType(hi.getType());
			hImage.setUpdateTime(hi.getUpdateTime());
			hImage.setAudit_status(hi.getAudit_status());
			hImage.setEmployee_id(hi.getEmployee_id());
			hImage.setAudit_time(hi.getAudit_time());
			hImage.setGuestId(hi.getGuestId());
			obj = imgSOAClient.getImageKeyObject(hi.getImgKey());
	        try {
				URL url = new URL(obj.getOriginal());
				hImage.setImgKey(url.toString());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
	        hImageList.add(hImage);
		}
		
		return hImageList;
	}

	@Transactional
	@Override
	public int updateAudit(Map<String, Object> pars) {
		String flag = pars.get("flag")+"";
		String guestId = pars.get("guestId")+"";
		int pass = 0;
		if("0".equals(flag)){
			
	//		houseImageWriteMapper.insertAgentOperation(pars); //插入数据lf_agent_operation表 每传一次增加一条为2的数据,第一次上传增加一条为5的数据
			
			
			//查找出此次需要改变的图片list
			List<HouseImageAudit> hiList = houseImageReadMapper.setectImage(pars);
			List<HouseImage> hImageList = new ArrayList<HouseImage>();
			List<LfAgentOperation> operationList = new ArrayList<LfAgentOperation>();
			for(HouseImageAudit hi : hiList){
				HouseImage hImage = new HouseImage();
				LfAgentOperation lo = new LfAgentOperation();
				hImage.setAgentId(hi.getAgentId());//
				hImage.setCreateTime(hi.getCreateTime());//
				hImage.setDesc(hi.getDesc());//
				hImage.setHouseId(hi.getHouseId());//
	
				hImage.setSourceId(hi.getSourceId());//
				hImage.setStatus(hi.getStatus());//
				hImage.setTakePhotoTime(hi.getTakePhotoTime());//
				hImage.setType(hi.getType());//
				hImage.setUpdateTime(hi.getUpdateTime());//
				hImage.setImgKey(hi.getImgKey());//
				hImage.setGuestId(hi.getGuestId());
				
				lo.setHouseId(hi.getHouseId());
				lo.setOperationType(2);
				lo.setOperator(hi.getAgentId());
				lo.setStatus(1);
				operationList.add(lo);
		        hImageList.add(hImage);
			}
			
			//先判断lf_sell_house_agent表的pictureAgent是否有值,有就添加lf_agent_operation表为5的数据,没有就不添加
			
			if(!"null".equals(pars.get("agentId")) && !"".equals(pars.get("agentId")) && pars.get("agentId") != null){
				Agent a = agentSoa.getAgent(Integer.parseInt(pars.get("agentId")+""));
				
				pars.put("franchiseeAreaOrgId", a.getFranchiseeId());
			
			}
			SellhouseReturn sr = houseImageReadMapper.selectagentByhouseId(pars);
			if(!"null".equals(guestId) && !"".equals(guestId) && guestId != null){
				
			}else{
				//查出没有记录就插入一条记录
				if(sr == null){
					//插入lf_sell_house_agent表记录
					lfSellHouseAgentWriteMapper.insertHouseAgent(pars);
					//有就添加lf_agent_operation表为5的数据,没有就不添加
					houseImageWriteMapper.insertAgentOperationF(pars);
				}else if(sr.getPictureAgent() == null || "".equals(sr.getPictureAgent())){//不为空的时候就修改lf_sell_house_agent  有数据但是照片人为空
					houseImageWriteMapper.insertAgentOperationF(pars);
					lfSellHouseAgentWriteMapper.updateHouseAgent(pars);//更新lf_sell_house_agent表中pictureAgent字段(照片人)
					pass = 1;
				}
			}
			
			/*if(count == 0){//没有查到为5的数据,插入一条为5的数据
				houseImageWriteMapper.insertAgentOperationF(pars);
			}*/
			//图片数量就是list的长度
			if(hImageList != null && hImageList.size() > 0){
				pars.put("count", hImageList.size());
				if(!"null".equals(guestId) && !"".equals(guestId) && guestId != null){
					
				}else{
					
					houseImageWriteMapper.updateHouseInfoPicNum(pars);//更新图片数量 
				}
				//此逻辑不用
				//	houseImageWriteMapper.insertAgentOperation(operationList);//插入数据lf_agent_operation表 每传一次增加一条为2的数据
				//插入house_image表  以上传人为单位上传
				houseImageWriteMapper.insertimagebatch(hImageList);
			}
			//更新house_image_audit 表状态 已审核状态.
			pars.put("status", 1);
			if(!"null".equals(guestId) && !"".equals(guestId) && guestId != null){
				
			}else{
				houseImageWriteMapper.inserteditrecord(pars);
			}
		}else{
			//更新house_image_audit 表状态 
			pars.put("status", 2);//审核不通过
		}
		//更新审核表
		houseImageWriteMapper.updateHouseImageAudit(pars);
		return pass;
	}


	@Override
	public String findSellPointById(Map<String, Object> pars) {
		
		return houseImageReadMapper.findSellPointById(pars);
	}


	@Override
	public String findRoomById(Map<String, Object> pars) {
		
		return houseImageReadMapper.findRoomById(pars);
	}

	@Override
	public int findHouseById(Map<String, Object> pars) {

		return houseImageReadMapper.findHouseById(pars);
	}

}
