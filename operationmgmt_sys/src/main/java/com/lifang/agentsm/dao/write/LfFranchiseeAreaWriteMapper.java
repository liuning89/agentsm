/**
 *
 *   ver     date      	author
 * ──────────────────────────────────
 *   		2015年12月2日     Yang'ushan
 *
 * Copyright (c) 2015, Lifang All Rights Reserved.
*/

package com.lifang.agentsm.dao.write;

import org.apache.ibatis.annotations.Param;

/**
 * 加盟商和组织架构
 *
 * @author   Yang'ushan
 * @Date	 2015年12月2日		下午4:31:04
 *
 * @see 	  
 */
public interface LfFranchiseeAreaWriteMapper {

	/**
	 * 
	 * 添加志远和区域的关系
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年12月2日      新建
	 * </pre>
	 *
	 * @param areaId
	 * @param franchiseeId
	 */
	public void add(@Param("areaId") int areaId, @Param("franchiseeId") int franchiseeId);
	
	/**
	 * 
	 * 删除区域的关系
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年12月2日      新建
	 * </pre>
	 *
	 * @param areaId
	 */
	public void delete(int areaId);
	
}
