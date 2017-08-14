package com.lifang.agentsm.dao.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.model.AppnameMiniuiEntity;
import com.lifang.agentsm.model.PriFunction;
import com.lifang.agentsm.model.PriUrl;

/**
 * 
 * 功能，读
 *
 * @author   Yang'ushan
 * @Date	 2015年7月22日		下午4:48:46
 *
 * @see
 */
public interface PriFunctionReadMapper {

	/**
	 * 
	 * 列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月22日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	public List<PriFunction> list(Pagination req);
	
	/**
	 * 
	 * 详情
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public PriFunction priFunction(int id);
	
	/**
	 * 
	 * app列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @return
	 */
	public List<AppnameMiniuiEntity> appSimpleList();
	
	/**
	 * 
	 * 获取URL列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param functionId
	 * @param name
	 * @return
	 */
	public List<PriUrl> urlList(@Param("functionId") int functionId, @Param("name") String name);
	
	/**
	 * 
	 * 获取非菜单的功能列表，在添加菜单页上使用
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月27日      新建
	 * </pre>
	 *
	 * @param pagination
	 * @return
	 */
	public List<PriFunction> getUrlFunctionList(Pagination pagination);
	
	/**
	 * 
	 * 判断某个应用下，某个功能是否存在
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月28日      新建
	 * </pre>
	 *
	 * @param name
	 * @param appName
	 * @return
	 */
	public Integer getMenuPriFunction(@Param("name") String name, @Param("appName") String appName);
	
}
