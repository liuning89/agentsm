package com.lifang.bzsm.console.report.read;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lifang.bzsm.console.entity.LfAreaOrg;

/**
 * 
 * Function:组织架构接口类
 *
 * @author   zhoujun
 * @Date	 2015年4月13日		上午9:52:35
 *
 * @see
 */
public interface LfAreaOrgBzReadMapper {

    LfAreaOrg selectByPrimaryKey(Long id);
    
    /**
     * 功能描述:根据参数获取区域、门店等信息
     * @param param
     * @return
     */
    List<LfAreaOrg> selectBy(Map<String, Object> param);
    
    LfAreaOrg selectByCode(String code);
    
    /**
     * 
    * @Title: selectByEmployeeId 
    * @Description: 通过员工ID获取组织信息
    * @param @param employeeId
    * @param @return    设定文件 
    * @return LfAreaOrg    返回类型 
    * @throws
     */
    //LfAreaOrg selectByEmployeeId(Integer employeeId);


    List<LfAreaOrg> selectByCodeAndLevel(Map param);

}