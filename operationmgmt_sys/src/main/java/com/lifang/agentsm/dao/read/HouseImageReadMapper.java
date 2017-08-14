package com.lifang.agentsm.dao.read;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.HouseImage;
import com.lifang.agentsm.entity.HouseImageAudit;
import com.lifang.agentsm.entity.HouseInfo;
import com.lifang.agentsm.entity.SellhouseReturn;
import com.lifang.agentsm.model.HouseImageAuditInfo;

public interface HouseImageReadMapper {
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


	String findSellPointById(Map<String, Object> pars);


	String findRoomById(Map<String, Object> pars);


	String findCode(Map<String, Object> pars);

	int findHouseById(Map<String, Object> pars);
}