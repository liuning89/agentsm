package com.lifang.agentsm.dao.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.entity.DicArea;
import com.lifang.agentsm.model.AreaOrg;
import com.lifang.agentsm.model.AreaOrgMiniuiEntity;
import com.lifang.agentsm.model.req.AreaOrgReq;

/**
 * 
 * 组织架构，读
 *
 * @author   Yang'ushan
 * @Date	 2015年8月3日		下午6:30:25
 *
 * @see
 */
public interface AreaOrgReadMapper {

	/**
	 * 
	 * 城市列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月3日      新建
	 * </pre>
	 *
	 * @param pagination
	 * @return
	 */
	public List<AreaOrg> list2City(Pagination pagination);
	
	/**
	 * 
	 * 获取code
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public String getCodeById(int id);
	
	/**
	 * 
	 * 获取父级下最大的code
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param parentId
	 * @return
	 */
	public String getParentMaxCode(int parentId);
	
	/**
	 * 
	 * 城市详情
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
	 * 验证有效门店
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
	 * 区域列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param pagination
	 * @return
	 */
	public List<AreaOrg> list2Area(Pagination pagination);

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
	public List<AreaOrgMiniuiEntity> simple(@Param("level")int level, @Param("cityId")Integer cityId, 
			@Param("parentId")Integer parentId);
	
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
	public List<AreaOrg> list2Store(AreaOrgReq req);
	
	/**
	 * 
	 * 门店列表总数
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年9月30日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	public int list2StoreCount(AreaOrgReq req);
	
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
	 * 根据父节点获取子列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年9月25日      新建
	 * </pre>
	 *
	 * @param parentId
	 * @return
	 */
	public List<AreaOrg> getChildByParentId(int parentId);
	
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
	public int validateNameIsExist(@Param("parentId") int parentId, @Param("name") String name);
	
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
	
	/**
	 * 
	 * 根据code获取详情
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年12月2日      新建
	 * </pre>
	 *
	 * @param code
	 * @return
	 */
	public AreaOrg getAreaOrgByCode(String code);
	
}
