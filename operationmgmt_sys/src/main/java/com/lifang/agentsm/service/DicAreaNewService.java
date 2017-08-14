package com.lifang.agentsm.service;

import java.util.HashMap;
import java.util.List;

import com.lifang.agentsm.entity.DicAreaNew;
import com.lifang.agentsm.model.MiniuiEntity;

public interface DicAreaNewService {
    DicAreaNew selectByPrimaryKey(Long id);
    
    List<DicAreaNew> selectCity();
    
    List<DicAreaNew> selectDistrictidByCity(HashMap<String,Object> map);
    
    List<MiniuiEntity> getEnableCitySimpleList(Integer cityId);
    
    /**
     * 
     * 根据上级区域，获取下级区域
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     Yang'ushan:   2015年8月27日      新建
     * </pre>
     *
     * @param parentId
     * @return
     */
    List<MiniuiEntity> getDicAreaNewByParentId(int parentId);

    MiniuiEntity getEnableCity(Integer cityId);

    List<MiniuiEntity> getEnableCityUsedArea(Integer cityId);
}
