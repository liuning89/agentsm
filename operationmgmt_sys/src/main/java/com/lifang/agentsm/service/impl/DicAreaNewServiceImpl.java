package com.lifang.agentsm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.read.DicAreaNewReadMapper;
import com.lifang.agentsm.entity.DicAreaNew;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.service.DicAreaNewService;


/**
 * 查找区域信息
 * @author zhoujun
 *
 */
@Service(value="dicAreaNewService")
public class DicAreaNewServiceImpl implements DicAreaNewService {

	@Autowired
	private DicAreaNewReadMapper dicAreaReadNewMapper;

	@Override
	public DicAreaNew selectByPrimaryKey(Long id) {

		return dicAreaReadNewMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<DicAreaNew> selectCity() {

		return dicAreaReadNewMapper.selectCity();
	}

	@Override
	public List<DicAreaNew> selectDistrictidByCity(HashMap<String, Object> map) {
	
		return dicAreaReadNewMapper.selectDistrictidByCity(map);
	}
	
	
	@Override
	public List<MiniuiEntity> getEnableCitySimpleList(Integer cityId) {
	    Map map = new HashMap();
	    map.put("cityId",cityId);
		return dicAreaReadNewMapper.getEnableCitySimpleList(map);
	}

	/**
	 * @see com.lifang.agentsm.service.DicAreaNewService#getDicAreaNewByParentId(int)
	 */
	@Override
	public List<MiniuiEntity> getDicAreaNewByParentId(int parentId) {
		return dicAreaReadNewMapper.getDicAreaNewByParentId(parentId);
	}

    @Override
    public MiniuiEntity getEnableCity(Integer cityId) {
        return dicAreaReadNewMapper.getEnableCity(cityId);
    }

    @Override
    public List<MiniuiEntity> getEnableCityUsedArea(Integer cityId) {
        Map map = new HashMap();
        map.put("cityId",cityId);
        return dicAreaReadNewMapper.getEnableCityUsedArea(map);
    }

}
