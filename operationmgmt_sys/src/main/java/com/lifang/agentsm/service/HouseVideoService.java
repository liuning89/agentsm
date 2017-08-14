package com.lifang.agentsm.service;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.HouseInfo;
import com.lifang.housesoa.model.HouseDetailInfo;

/**
 * 
* @ClassName: HouseImageService 
* @Description: TODO(房源图片service) 
* @author chenqiang
* @date 2015年5月29日 下午2:38:55 
*
 */
public interface HouseVideoService {
    
    /**
     * 
    * @Title: getAllHouseInfo 
    * @Description: 获取房源图片信息
    * @param @param houseId
    * @param @return    设定文件 
    * @return List<HouseRentDetailInfo>    返回类型 
    * @throws
     */
    HouseDetailInfo getAllHouseInfo(Integer houseId);
    
    /**
     * 获取下拉小区名
     * @param pars
     * @return
     */
	List<HouseInfo> getEstateName();

	/**
	 * 获取图片审核列表
	 * @param pars
	 * @return
	 */
	Map<String, Object> getVideoAuditList(Map<String, Object> pars);


	void updateAudit(Map<String, Object> pars);
    
}
