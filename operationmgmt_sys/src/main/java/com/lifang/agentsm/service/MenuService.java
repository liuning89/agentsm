package com.lifang.agentsm.service;

import java.util.List;

import com.lifang.agentsm.model.Menu;
import com.lifang.agentsm.model.req.MenuReq;
import com.lifang.model.Response;

/**
 * 
 * 菜单
 *
 * @author   Yang'ushan
 * @Date	 2015年7月27日		下午2:37:19
 *
 * @see
 */
public interface MenuService {

	/**
	 * 
	 * 列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月27日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	public Response list(MenuReq req);
	
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
	public List<Menu> getMenuParentTree(String name, String appName);
	
	/**
	 * 
	 * 添加
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月27日      新建
	 * </pre>
	 *
	 * @param menu
	 * @return
	 */
	public int add(Menu menu);
	
	/**
	 * 
	 * 详情
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月27日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public Menu menu(int id);
	
	/**
	 * 
	 * 修改
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月27日      新建
	 * </pre>
	 *
	 * @param menu
	 * @return
	 */
	public int update(Menu menu);
	
	/**
	 * 
	 * 删除
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月27日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public int delete(int id);
	
}
