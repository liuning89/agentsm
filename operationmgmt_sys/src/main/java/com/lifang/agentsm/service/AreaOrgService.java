package com.lifang.agentsm.service;

import java.util.List;

import com.lifang.agentsm.entity.DicArea;
import com.lifang.agentsm.model.AreaOrg;
import com.lifang.agentsm.model.AreaOrgMiniuiEntity;
import com.lifang.agentsm.model.req.AreaOrgReq;
import com.lifang.model.Response;

/**
 * 
 * 组织架构
 *
 * @author   Yang'ushan
 * @Date	 2015年8月3日		下午6:29:39
 *
 * @see
 */
public interface AreaOrgService {

	/**
	 * 
	 * 城市列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月3日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	public Response list2City(AreaOrgReq req);
	
	/**
	 * 
	 * 添加
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月3日      新建
	 * </pre>
	 *
	 * @param areaOrg
	 * @param dicAreas
	 */
	public void add(AreaOrg areaOrg, List<DicArea> dicAreas);
	
	/**
	 * 
	 * 详情
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public AreaOrg city(int id);
	
	/**
	 * 
	 * 修改
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param areaOrg
	 * @param oldParentId
	 * @param towns
	 * @param deleteTowns
	 * @param oldFranchiseeId
	 */
	public int update(AreaOrg areaOrg, int oldParentId, List<DicArea> towns, List<DicArea> deleteTowns,
			Integer oldFranchiseeId);
	
	/**
	 * 
	 * 删除
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param code
	 * @return
	 */
	public int delete(String code, int level);
	
	/**
	 * 
	 * 验证code下是否有有效的门店
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param code
	 * @return
	 */
	public int validateStoreByCode(String code);
	
	/**
	 * 
	 * 区域组织架构
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	public Response list2Area(AreaOrgReq req);
	
	/**
	 * 
	 * 下拉框列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param level
	 * @param cityId
	 * @param parentId
	 * @return
	 */
	public List<AreaOrgMiniuiEntity> simple(int level, Integer cityId, Integer parentId);
	
	/**
	 * 
	 * 区域详情
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public AreaOrg area(int id);
	
	/**
	 * 
	 * 门店列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月5日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	public Response list2Store(AreaOrgReq req);
	
	/**
	 * 
	 * 门店详情
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月5日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public AreaOrg store(int id);
	
	/**
	 * 
	 * 验证城市组织架构是否存在
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月11日      新建
	 * </pre>
	 *
	 * @param cityId
	 * @return
	 */
	public int validateCityHaveCount(int cityId);
	
	/**
	 * 
	 * 获取添加门店时，专属显示的区域列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年9月29日      新建
	 * </pre>
	 *
	 * @param districtId
	 * @return
	 */
	public List<DicArea> getStorePageTownListByDistrictId(int districtId);
	
	/**
	 * 
	 * 获取门店的板块列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年9月30日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public List<DicArea> getStrowTownList(int id);
	
	/**
	 * 
	 * 验证名称是否存在
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年10月10日      新建
	 * </pre>
	 *
	 * @param parentId
	 * @param name
	 * @return
	 */
	public int validateNameIsExist(int parentId, String name);
	
	/**
	 * 
	 * 判断门店下面是否有有效的经纪人
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年10月30日      新建
	 * </pre>
	 *
	 * @param code
	 * @return
	 */
	public int validateStoreHaveEmployee(String code);
	
}
