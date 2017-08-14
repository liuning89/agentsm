package com.lifang.agentsm.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.lifang.agentsm.entity.HouseImage;
import com.lifang.agentsm.entity.HouseImageAudit;
import com.lifang.agentsm.entity.HouseInfo;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.housesoa.model.HouseDetailInfo;
import com.lifang.housesoa.model.HouseRentDetailInfo;

/**
 * 
* @ClassName: HouseImageService 
* @Description: TODO(房源图片service) 
* @author chenqiang
* @date 2015年5月29日 下午2:38:55 
*
 */
public interface HouseImageService {
    
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
     * 
    * @Title: uploadSingleFile 
    * @Description: 上传图片并插入到数据库
    * @param @param file
    * @param @param type
    * @param @return    设定文件 
    * @return int    返回类型 
    * @throws
     */
    HouseImage uploadSingleFile(MultipartFile file, LfEmployee employee, Map<String,Object> pars);
    
    /**
     * 
    * @Title: updateImageType 
    * @Description: 更新图片类型
    * @param @param houseImage
    * @param @return    设定文件 
    * @return int    返回类型 
    * @throws
     */
    int updateImageType(HouseImage houseImage);
    
    /**
     * 
    * @Title: removeHouseImage 
    * @Description: 删除图片的本地记录
    * @param @param id
    * @param @return    设定文件 
    * @return int    返回类型 
    * @throws
     */
    int removeHouseImage(String key,Integer houseId);
 
    /**
     * 获取下拉小区名
     * @return
     */
	List<HouseInfo> getEstateName();

	/**
	 * 获取图片审核列表
	 * @param pars
	 * @return
	 */
	Map<String, Object> getImageAuditList(Map<String, Object> pars);

	List<HouseImageAudit> selectImageByhouseId(Map<String, Object> pars);

	int updateAudit(Map<String, Object> pars);

	String findSellPointById(Map<String, Object> pars);

	String findRoomById(Map<String, Object> pars);

    int findHouseById(Map<String, Object> pars);
}
