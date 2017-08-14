package com.lifang.agentsm.dao.read;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.HouseImage;
import com.lifang.agentsm.entity.HouseImageAudit;
import com.lifang.agentsm.entity.HouseInfo;
import com.lifang.agentsm.entity.SellhouseReturn;
import com.lifang.agentsm.model.HouseImageAuditInfo;
import com.lifang.agentsm.model.HouseVideoAuditInfo;

public interface HouseVideoReadMapper {
    HouseImage selectByPrimaryKey(Long id);
    
    
    int selectFirstPageImageCount(Integer houseId);
    
    
    List<HouseInfo> getEstateName();


	int getImageAuditListCount(Map<String, Object> pars);


	List<HouseImageAuditInfo> getImageAuditList(Map<String, Object> pars);


	List<HouseImageAudit> setectImage(Map<String, Object> pars);


	  /**
	 * 
	 * @param pars
	 */
	SellhouseReturn selectagentByhouseId(Map<String, Object> pars);


	String setectHouseAgent(Map<String, Object> pars);


//	int getVideoAuditListCount(Map<String, Object> pars);


//	List<HouseVideoAuditInfo> getVideoAuditList(Map<String, Object> pars);


	int getEstateVideoAuditListCount(Map<String, Object> pars);


	List<HouseVideoAuditInfo> getEstateVideoAuditList(Map<String, Object> pars);


	int getHouseVideoAuditListCount(Map<String, Object> pars);


	List<HouseVideoAuditInfo> getHouseVideoAuditList(Map<String, Object> pars);


}