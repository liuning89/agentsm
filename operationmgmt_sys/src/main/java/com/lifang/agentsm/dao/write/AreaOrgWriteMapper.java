package com.lifang.agentsm.dao.write;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lifang.agentsm.entity.DicArea;
import com.lifang.agentsm.model.AreaOrg;

/**
 * 
 * 组织架构，写
 *
 * @author   Yang'ushan
 * @Date	 2015年8月3日		下午6:30:42
 *
 * @see
 */
public interface AreaOrgWriteMapper {
	
	/**
	 * 
	 * 添加
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param areaOrg
	 * @return
	 */
	public int add(AreaOrg areaOrg);
	
	/**
	 * 
	 * 门店和板块的关系
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月27日      新建
	 * </pre>
	 *
	 * @param storeId
	 * @param townId
	 * @param districtId
	 * @return
	 */
	public int addStoreTownRelation(@Param("storeId") int storeId, @Param("townId") int townId, @Param("districtId") int districtId);
	
	/**
	 * 
	 * 添加门店和板块列表，此方式无法使用判断是否存在
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年9月30日      新建
	 * </pre>
	 *
	 * @param storeId
	 * @param dicAreas
	 * @return
	 */
	public int addStoreTownRelations(@Param("storeId") int storeId, @Param("dicAreas") List<DicArea> dicAreas);
	
	/**
	 * 
	 * 删除门店和板块的关系
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年10月8日      新建
	 * </pre>
	 *
	 * @param storeId
	 * @param dicAreas
	 * @return
	 */
	public int deleteStoreTownRelations(@Param("storeId") int storeId, @Param("dicAreas") List<DicArea> dicAreas);
	
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
	 * @return
	 */
	public int update(AreaOrg areaOrg);
	
	/**
	 * 
	 * 修改code
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param code
	 * @param oldCode
	 * @return
	 */
	public int updateCode(@Param("code") String code, @Param("oldCode") String oldCode);
	
	/**
	 * 
	 * 删除以code开头的架构
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param code
	 * @return
	 */
	public int delete(String code);
	
	/**
	 * 
	 * 把下级的type全部改为相同的
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年10月21日      新建
	 * </pre>
	 *
	 * @param code
	 * @param type
	 * @return
	 */
//	public int updateType(@Param("code") String code, @Param("type") byte type);

}
