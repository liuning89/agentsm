package com.lifang.agentsm.dao.write;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.HouseImage;
import com.lifang.agentsm.model.LfAgentOperation;

public interface HouseVideoWriteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HouseImage record);

    int insertSelective(HouseImage record);

    int updateByPrimaryKeySelective(HouseImage record);

    int updateByPrimaryKey(HouseImage record);
    
    int updateByKeySelective(HouseImage record);
    
    int deleteHouseImageByKey(String key);
    
    int updateByTypeSelective(HouseImage record);
    
    int updateHouseInfoPicNum(Map map);

	void insertimagebatch(List<HouseImage> hImageList);

	void insertAgentOperation(List<LfAgentOperation> operationList);

	void insertAgentOperationF(Map<String, Object> pars);

	void updateHouseImageAudit(Map<String, Object> pars);

	void inserteditrecord(Map<String, Object> pars);

	void updateHouseVideo(Map<String, Object> pars);

	void insertVideoAudit(Map<String, Object> pars);

	void updateEstateVideo(Map<String, Object> pars);
}