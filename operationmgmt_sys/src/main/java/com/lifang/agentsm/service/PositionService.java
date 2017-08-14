package com.lifang.agentsm.service;

import java.util.List;

import com.lifang.agentsm.model.AppnameMiniuiEntity;
import com.lifang.agentsm.model.Menu;
import com.lifang.agentsm.model.PriFunction;
import com.lifang.agentsm.model.req.PositionReq;
import com.lifang.model.Response;

/**
 * 
 * 职位
 *
 * @author   Yang'ushan
 * @Date	 2015年7月28日		下午4:08:42
 *
 * @see
 */
public interface PositionService {
	
	/**
	 * 
	 * 列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月28日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	public Response list(PositionReq req);
	
	/**
	 * 
	 * 根据部门获取应用列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月28日      新建
	 * </pre>
	 *
	 * @param department
	 * @return
	 */
	public List<AppnameMiniuiEntity> getAppnameByDepartment(int department);
	
	/**
	 * 
	 * 获取职位的功能列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月28日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public List<PriFunction> getPositionPriFunctions(int id);
	
	/**
	 * 
	 * 获取所有功能列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月29日      新建
	 * </pre>
	 *
	 * @param appName
	 * @param name
	 * @return
	 */
	public List<PriFunction> getPriFunctions(String appName, String name);
	
	/**
	 * 
	 * 保存职位的功能列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月29日      新建
	 * </pre>
	 *
	 * @param positionId
	 * @param functionIds
	 */
	public void savePositionPriFunction(int positionId, List<Integer> functionIds);
	
	/**
	 * 
	 * 获取菜单列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月29日      新建
	 * </pre>
	 *
	 * @param appName
	 * @return
	 */
	public List<Menu> getLeafMenuList(String appName);
	
	/**
	 * 
	 * 获取职位的菜单列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月29日      新建
	 * </pre>
	 *
	 * @param positionId
	 * @return
	 */
	public List<Menu> getPositionMenuList(int positionId);
	
	/**
	 * 
	 * 保存菜单
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月30日      新建
	 * </pre>
	 *
	 * @param positionId
	 * @param menuIds
	 */
	public void savePositionMenu(int positionId, List<Integer> menuIds);
	
	/**
	 * 
	 * 列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月6日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	public Response list2(PositionReq req);
	
	/**
	 * 
	 * 获取职位URL列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月6日      新建
	 * </pre>
	 *
	 * @param department
	 * @param cityId
	 * @param level
	 * @return
	 */
	public List<PriFunction> getPositionPriFunctions2(byte department, int cityId, int level);

	/**
	 * 
	 * 保存职位列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月6日      新建
	 * </pre>
	 *
	 * @param department
	 * @param cityId
	 * @param level
	 * @param addFunctionIds
	 * @param deleteFunctionIds
	 * @return
	 */
	public int savePositionPriFunction2(byte department, int cityId, int level, 
			List<Integer> addFunctionIds, List<Integer> deleteFunctionIds);
	
	/**
	 * 
	 * 获取职位菜单列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月6日      新建
	 * </pre>
	 *
	 * @param department
	 * @param cityId
	 * @param level
	 * @param appName
	 * @return
	 */
	public List<Menu> getPositionMenuList2(byte department, int cityId, int level, String appName);
	
	/**
	 * 
	 * 保存职位菜单
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月6日      新建
	 * </pre>
	 *
	 * @param department
	 * @param cityId
	 * @param level
	 * @param addMenuIds
	 * @param deleteMenuIds
	 * @return
	 */
	public int savePositionMenu2(byte department, int cityId, int level, 
			List<Integer> addMenuIds, List<Integer> deleteMenuIds);
	
}
