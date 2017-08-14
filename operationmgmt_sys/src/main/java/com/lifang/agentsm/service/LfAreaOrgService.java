package com.lifang.agentsm.service;

import com.lifang.agentsm.entity.LfAreaOrg;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 15-7-20.
 */
public interface LfAreaOrgService {
    /**
     * 功能描述:根据参数获取区域、门店等信息
     * @param map
     * @return
     */
    List<LfAreaOrg> selectBy(HashMap<String, Object> map);
    
    public LfAreaOrg selectByCode(String code);

	List<LfAreaOrg> selectAgent(HashMap<String, Object> param);

    public LfAreaOrg selectByPK(Integer id);
    
    public LfAreaOrg selectByEmployeeId(Integer empId);

	List<LfAreaOrg> selectByType(HashMap<String, Object> param);

	List<LfAreaOrg> selectOnlyByType(HashMap<String, Object> param);

	LfAreaOrg selectByParentId(String parentId);
}
