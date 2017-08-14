package com.lifang.agentsm.service;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.LfStore;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.model.StoreInfoModel;

public interface StoreService {
	/**
	 * @author fangyouhui
	 * 门店列表
	 * @return
	 */
	public Map<String, Object> getStore(Map<String, Object> pars);
	/**
	 * @author fangyouhui
	 * 根据公司id获取门店列表
	 * @param companyId
	 * @return
	 */
	public List<LfStore> getStoreByCompanyId(Integer companyId);
	/**
	 * 添加门店
	 * @param pars
	 * @return
	 */
	public int addStore(LfStore pars);
	/**
	 * 更新门店信息
	 * @param pars
	 * @return
	 */
	public int updateStoreByStoreId(LfStore pars);
	/**
	 * 删除门店   将门店的状态改为2 不可用
	 * @param storeId
	 * @return
	 */
	public int deleteStoreByStoreId(Integer storeId);
	
	public List<StoreInfoModel> getStoreListByPars(Integer companyId,String storeName);
	
	/***
	 * 获取门店列表
	 */
	public List<LfStore> selectStoreListByPars(Map<String,Object> pars);
	
	
	/**
	 * 功能描述:获取门店简单信息列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     CK:   2015年5月30日      新建
	 * </pre>
	 *
	 * @param cityId
	 * @return
	 */
	public List<MiniuiEntity> getStoreSimpleList(Integer cityId);
}
