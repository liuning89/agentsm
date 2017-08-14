package com.lifang.agentsm.dao.read;

import com.lifang.agentsm.entity.LfAreaOrg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Function:组织架构接口类
 *
 * @author   zhoujun
 * @Date	 2015年4月13日		上午9:52:35
 *
 * @see
 */
public interface LfAreaOrgReadMapper {

    LfAreaOrg selectByPrimaryKey(Long id);
    
   
    /**
     * 功能描述:根据参数获取区域、门店等信息
     * @param param
     * @return
     */
    List<LfAreaOrg> selectBy(Map<String, Object> param);
    LfAreaOrg selectByCode(String code);

	List<LfAreaOrg> selectAgent(HashMap<String, Object> param);
	 LfAreaOrg selectByEmployeeId(Integer empId);

	List<LfAreaOrg> selectByType(HashMap<String, Object> param);

	List<LfAreaOrg> selectOnlyByType(HashMap<String, Object> param);

	LfAreaOrg selectByParentId(String parentId);
	List<LfAreaOrg> selectByCodeAndLevel(Map param);
}