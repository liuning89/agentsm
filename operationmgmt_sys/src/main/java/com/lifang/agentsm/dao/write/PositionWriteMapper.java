package com.lifang.agentsm.dao.write;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lifang.agentsm.model.Position;

public interface PositionWriteMapper {

	/**
	 * 
	 * 保存职位和功能
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
	public int addPositionFunctionRelation(@Param("positionId") int positionId, 
				@Param("functionIds") List<Integer> functionIds);
	
	/**
	 * 
	 * 删除职位和功能，删除在改functionids不存在的
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月29日      新建
	 * </pre>
	 *
	 * @param ids
	 * @return
	 */
	public int deletePositionFunctionRelationNotExistsFunctionIds(List<Integer> ids);
	
	/**
	 * 
	 * 添加
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param position
	 * @return
	 */
	public int add(Position position);
	
	/**
	 * 
	 * 修改
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param areaOrgId
	 * @param department
	 * @param name
	 * @return
	 */
	public int update(@Param("areaOrgId") int areaOrgId, @Param("department") int department, 
			@Param("name") String name);
	
	/**
	 * 
	 * 删除关系
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月6日      新建
	 * </pre>
	 *
	 * @param positionId
	 * @param functionIds
	 * @return
	 */
	public int deletePositionFunctionRelation(@Param("positionId") int positionId, 
			@Param("functionIds") List<Integer> functionIds);
	
	/**
	 * 
	 * 设置状态
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年11月3日      新建
	 * </pre>
	 *
	 * @param id
	 * @param status
	 * @return
	 */
	public int setStatus(@Param("id") int id, @Param("status") int status);
	
}
