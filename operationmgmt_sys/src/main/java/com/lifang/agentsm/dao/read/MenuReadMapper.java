package com.lifang.agentsm.dao.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.model.Menu;

/**
 * 
 * 菜单，读
 *
 * @author   Yang'ushan
 * @Date	 2015年7月27日		下午2:58:00
 *
 * @see
 */
public interface MenuReadMapper {

	/**
	 * 
	 * 列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月27日      新建
	 * </pre>
	 *
	 * @param pagination
	 * @return
	 */
	public List<Menu> list(Pagination pagination);
	
	/**
	 * 
	 * 获取父级树图
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月27日      新建
	 * </pre>
	 *
	 * @param name
	 * @param appName
	 * @return
	 */
	public List<Menu> getMenuParentTree(@Param("name") String name, @Param("appName") String appName);
	
	/**
	 * 
	 * 详情
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月28日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public Menu menu(int id);
	
	/**
	 * 
	 * 获取子菜单列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月28日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public List<Integer> getChildMenuIds(int id);
	
}
