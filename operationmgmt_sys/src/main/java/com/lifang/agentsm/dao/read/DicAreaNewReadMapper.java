package com.lifang.agentsm.dao.read;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.DicAreaNew;
import com.lifang.agentsm.model.MiniuiEntity;
/**
 * 
 * Function:区域接口类
 *
 * @author   zhoujun
 * @Date	 2015年4月13日		上午9:52:35
 *
 * @see
 */
public interface DicAreaNewReadMapper {
    DicAreaNew selectByPrimaryKey(Long id);
    
    /**
     * 
     * 功能描述:获取城市接口
     *
     * <pre>
     *     zhoujun:   2015年4月13日      新建
     * </pre>
     *
     * @return
     */
    List<DicAreaNew> selectCity();
    
    /**
     * 
     * 功能描述:根据城市cityid获取区域信息
     *
     * <pre>
     *     zhoujun:   2015年4月13日      新建
     * </pre>
     *
     * @param map
     * @return
     */
    
    List<DicAreaNew> selectDistrictidByCity(HashMap<String,Object> map);
    
    
    /**
     * 功能描述:获取可以用的城市简单信息列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     CK:   2015年5月28日      新建
     * </pre>
     * @param cityId 
     *
     * @return
     */
    List<MiniuiEntity> getEnableCitySimpleList(Map map);
    
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

    MiniuiEntity getEnableCity(int cityId);

    List<MiniuiEntity> getEnableCityUsedArea(Map map);
}