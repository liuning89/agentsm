package com.lifang.agentsm.service;

import java.util.List;

import com.lifang.agentsm.model.AppnameMiniuiEntity;
import com.lifang.agentsm.model.PriFunction;
import com.lifang.agentsm.model.PriUrl;
import com.lifang.agentsm.model.req.PriFunctionReq;
import com.lifang.model.Response;

/**
 * 
 * 功能
 *
 * @author   Yang'ushan
 * @Date	 2015年7月22日		下午4:24:13
 *
 * @see
 */
public interface PriFunctionService {

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
	public Response list(PriFunctionReq req);
	
	/**
	 * 
	 * 获取app列表
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
	 * 添加
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param priFunction
	 * @return
	 */
	public int add(PriFunction priFunction);
	
	/**
	 * 
	 * 获取详情
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
	 * 修改
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param priFunction
	 * @return
	 */
	public int update(PriFunction priFunction);
	
	/**
	 * 
	 * 删除
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public int delete(int id);
	
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
	public List<PriUrl> urlList(int functionId, String name);
	
	/**
	 * 
	 * 保存URL列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param urls
	 * @param functionId
	 * @param deleteUrlIds
	 * @return
	 */
	public void saveUrls(List<PriUrl> urls, int functionId, List<Integer> deleteUrlIds);
	
	/**
	 * 
	 * 获取非菜单的功能列表，在添加菜单页上使用
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月27日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	public Response getUrlFunctionList(PriFunctionReq req);
	
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
	public Integer getMenuPriFunction(String name, String appName);
	
}
