package com.lifang.agentsm.dao.read;

import java.util.List;

import com.lifang.agentsm.model.MiniuiEntity;

/**
 * Author:CK
 * 创建时间：2015年5月30日
 */
public interface SubEstateReadMapper {
	/**
	 * 功能描述:根据子小区编号获取有效楼栋列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     CK:   2015年5月30日      新建
	 * </pre>
	 *
	 * @param subEstateId
	 * @return
	 */
	List<MiniuiEntity> getBuildingListBySubEstateId(Integer subEstateId);
}

