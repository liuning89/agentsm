package com.lifang.agentsm.dao.write;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lifang.agentsm.model.Menu;

/**
 * 
 * 菜单，写
 *
 * @author   Yang'ushan
 * @Date	 2015年7月27日		下午6:47:39
 *
 * @see
 */
public interface MenuWriteMapper {

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
	 * 修改
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月28日      新建
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
	 *     Yang'ushan:   2015年7月28日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public int delete(int id);
	
	/**
	 * 
	 * 批量删除
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月28日      新建
	 * </pre>
	 *
	 * @param ids
	 * @return
	 */
	public int deletes(@Param("ids") List<Integer> ids);
	
}
