package com.lifang.agentsm.service;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.LfAppFeedbackInfo;

/**
 * CRM本身相关业务-登陆
 * Function: TODO ADD FUNCTION
 *
 * @author   fanxin
 * @Date	 2015年4月21日		下午6:05:08
 *
 * @see
 */
public interface LfEmployeeService {

    LfEmployee loginCheck(Map map);
    
    
    //查询员工列表分页
    Map<String,Object> selectEmployeeListByPage(Map map);
    
    //添加员工
    int addLfEmployee(LfEmployee lfEmployee);
    
    //修改员工
    int updateLfEmployeeById(LfEmployee employee);
    
    //删除员工
    int removeLfEmployeeById(Integer id);
    
    //通过ID查找员工
    LfEmployee selectEmployeeById(Integer id);
    
    //检查用户是否存在
    int checkEmployeeExists(Map map);

    /**
     * 根据区域组织架构信息获取用户
     * */
    List<LfEmployee> getByAreaOrg(Map<String,Object> map);

	/**
	 * 修改密码
	 * @param map
	 */
	void updatePw(Map<String, Object> map);
}
