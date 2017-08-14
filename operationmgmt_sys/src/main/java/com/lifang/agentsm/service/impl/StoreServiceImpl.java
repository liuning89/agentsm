package com.lifang.agentsm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.read.LfAgentReadMapper;
import com.lifang.agentsm.dao.read.LfStoreReadMapper;
import com.lifang.agentsm.dao.write.LfStoreWriteMapper;
import com.lifang.agentsm.entity.LfStore;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.model.StoreInfoModel;
import com.lifang.agentsm.service.StoreService;

@Service(value="storeService")
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	private LfStoreReadMapper lfStoreReadMapper;
	
	@Autowired
	private LfStoreWriteMapper lfStoreWriteMapper;
	
	@Autowired
	private LfAgentReadMapper lfAgentReadMapper;
	
	@Override
	public Map<String, Object> getStore(Map<String, Object> pars) {
		int count = lfStoreReadMapper.selectStortCount(pars);
		Map<String, Object> map = new HashMap<String, Object>();
    	map.put("rows", count > 0 ? lfStoreReadMapper.selectStortList(pars) : new ArrayList<>());
		map.put("total", count);
		return map;
	}

	@Override
	public List<LfStore> getStoreByCompanyId(Integer companyId) {
		return lfStoreReadMapper.selectStoreByCompanyId(companyId);
	}
	/**
	 * 添加门店
	 */
	@Override
	public int addStore(LfStore pars) {
		//根据门店名称和公司id查重
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", pars.getCompanyId());
		map.put("storeName", pars.getStoreName());
		List<LfStore> storeList = lfStoreReadMapper.selectStortExit(map);
		if (storeList.size() <= 0) {
			pars.setStatus((byte) 1);
			pars.setCreateTime(new Date());
			return lfStoreWriteMapper.insertSelective(pars);
		} else {
			LfStore store = storeList.get(0);
			BeanUtils.copyProperties(pars, store);
			store.setStatus((byte) 1);
			store.setCreateTime(new Date());
			lfStoreWriteMapper.updateByPrimaryKeySelective(store);
		}
		return 0;
	}
	/**
	 * 删除门店
	 */
	@Override
	public int deleteStoreByStoreId(Integer storeId) {
		LfStore store = lfStoreReadMapper.selectByPrimaryKey(storeId);
		if(store != null){
			Map<String, Object> pars = new HashMap<>();
			pars.put("storeId", storeId);
			if(lfAgentReadMapper.selectAgentListByPars(pars).size()>0){
				return -1;
			}
//			store.setStatus((byte)2);
//			return lfStoreWriteMapper.updateByPrimaryKeySelective(store);
			return lfStoreWriteMapper.deleteByPrimaryKey(store.getId());
		}
		return 0;
	}
	/**
	 * 更新门店
	 */
	@Override
	public int updateStoreByStoreId(LfStore pars) {
		if(lfStoreReadMapper.selectByPrimaryKey(pars.getId())!=null){
//			Map<String, Object> map = new HashMap<>();
//			map.put("companyId", pars.getCompanyId());
//			map.put("storeName", pars.getStoreName());
//			if(lfStoreReadMapper.selectStortList(map).size()<=0){
//				return lfStoreWriteMapper.updateByPrimaryKeySelective(pars);
//			}
			return lfStoreWriteMapper.updateByPrimaryKeySelective(pars);
		}
		return 0;
	}
	/**
	 * 根据条件查询门店列表
	 */
	@Override
	public List<StoreInfoModel> getStoreListByPars(Integer companyId,String storeName) {
		Map<String, Object> pars = new HashMap<>();
		pars.put("companyId", companyId);
		pars.put("storeName", storeName);
		return lfStoreReadMapper.selectStortList(pars);
	}

    @Override
    public List<LfStore> selectStoreListByPars(Map<String, Object> pars) {
        return lfStoreReadMapper.selectStortExit(pars);
    }
    
    @Override
    public List<MiniuiEntity> getStoreSimpleList(Integer cityId) {
    	return lfStoreReadMapper.getStoreSimpleList(cityId);
    }
}
