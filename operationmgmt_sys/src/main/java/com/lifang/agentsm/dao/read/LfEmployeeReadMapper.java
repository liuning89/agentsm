package com.lifang.agentsm.dao.read;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.LfEmployee;

public interface LfEmployeeReadMapper {
    
    LfEmployee selectByPrimaryKey(Integer id);
    
    List loginCheck(Map map);
    
    //员工列表-分页
    List<LfEmployee> selectEmployeeListByPage(Map map);
    
    //分页总数量
    int selectEmployeeListCount(Map map);
    
    //检查用户是否存在
    int checkEmployeeExists(Map map);

    /**
     * 根据区域组织架构信息获取用户
     * */
    List<LfEmployee> getByAreaOrg(Map<String,Object> map);
}