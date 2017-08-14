package com.lifang.agentsm.dao.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.model.AppnameMiniuiEntity;
import com.lifang.agentsm.model.Menu;
import com.lifang.agentsm.model.Position;
import com.lifang.agentsm.model.PriFunction;

/**
 * 
 * 职位，读
 *
 * @author   Yang'ushan
 * @Date	 2015年7月28日		下午4:21:59
 *
 * @see
 */
public interface PositionReadMapper {

	/**
	 * 
	 * 列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月28日      新建
	 * </pre>
	 *
	 * @param pagination
	 * @return
	 */
	public List<Position> list(Pagination pagination);
	
	/**
	 * 
	 * 
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
	 * 获取权限的功能列表
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
	public List<PriFunction> getPriFunctions(@Param("appName") String appName, @Param("name") String name);
	
	/**
	 * 
	 * 获取存在的列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月29日      新建
	 * </pre>
	 *
	 * @param positionId
	 * @param functionIds
	 * @return
	 */
	public List<Integer> existsFunctionIds(@Param("positionId") int positionId, @Param("functionIds") List<Integer> functionIds);
	
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
	 * 根据菜单列表获取功能列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月30日      新建
	 * </pre>
	 *
	 * @param menuIds
	 * @return
	 */
	public List<Integer> getFunctionIdsByMenuIds(List<Integer> menuIds);
	
	/**
	 * 
	 * 判断存在的菜单列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月30日      新建
	 * </pre>
	 *
	 * @param positionId
	 * @param menuIds
	 * @return
	 */
	public List<Integer> existsFunctionIdsByMenuIds(@Param("positionId") int positionId, @Param("menuIds") List<Integer> menuIds);
	
	/**
	 * 
	 * 获取不存在functionIds里面的关系列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月30日      新建
	 * </pre>
	 *
	 * @param positionId
	 * @param functionIds
	 * @param type
	 * @return
	 */
	public List<Integer> getPositionFunctionRelationIdsAndNotExistsFunctionIds(@Param("positionId") int positionId, 
			@Param("functionIds") List<Integer> functionIds, @Param("type") int type);
	
	/**
	 * 
	 * 根据部门和组织架构，获取职位ID
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param areaOrgId
	 * @param department
	 * @return
	 */
	public int getPositionIdByDepartmentAndChildAreaOrgId(
			@Param("areaOrgId") int areaOrgId, @Param("department") byte department);
	
	/**
	 * 
	 * 获取职位下最大的code
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param areaOrgId
	 * @param department
	 * @return
	 */
	public String getMaxCodeByParentId(int parentId);
	
	/**
	 * 
	 * 获取code
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param areaOrgId
	 * @param department
	 * @return
	 */
	public String getCodeById(int id);
	
	/**
	 * 
	 * 列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月6日      新建
	 * </pre>
	 *
	 * @param pagination
	 * @return
	 */
	public List<Position> list2(Pagination pagination);
	
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
	public List<PriFunction> getPositionPriFunctions2(@Param("department") byte department, @Param("cityId") int cityId, 
			@Param("level") int level);
	
	/**
	 * 
	 * 获取职位列表
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
	public List<Integer> getPositionIdsByDepAndLevelAndCityId(@Param("department") byte department, @Param("cityId") int cityId, 
			@Param("level") int level);
	
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
	public List<Menu> getPositionMenuList2(@Param("department") byte department, @Param("cityId") int cityId, 
			@Param("level") int level, @Param("appName") String appName);
	
	/**
	 * 
	 * 获取相同等级，城市，部门职位的权限列表
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
	public List<Integer> getFunctionIdsByDepAndCityIdAndLevel(@Param("department") byte department, @Param("cityId") int cityId, 
			@Param("level") int level);
	
	/**
	 * 
	 * 验证是否有人事经理
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年11月3日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public Position validateAreaRenshiPositionIsExists(int id);
	
}
