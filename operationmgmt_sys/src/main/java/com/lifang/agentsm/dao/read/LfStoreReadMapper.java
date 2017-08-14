package com.lifang.agentsm.dao.read;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.LfStore;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.model.StoreInfoModel;

public interface LfStoreReadMapper {
    LfStore selectByPrimaryKey(Integer id);
    /**
     * @author fangyouhui
     * @return
     */
    int selectStortCount(Map<String, Object> pars);
    /**
     * @author fangyouhui
     * 根绝公司id查村该公司下所有的门店
     * @param companyId
     * @return
     */
    List<LfStore> selectStoreByCompanyId(Integer companyId);
    /**
     * @author fangyouhui
     * 查询所有的门店
     * @return
     */
    List<StoreInfoModel> selectStortList(Map<String, Object> pars);
    /**
     * @author fangyouhui
     * 查询是否存在该门店
     * @param pars
     * @return
     */
    List<LfStore> selectStortExit(Map<String, Object> pars);
    
    
    
    /**
     * 功能描述:获取门店的简单信息列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     CK:   2015年5月30日      新建
     * </pre>
     *
     * @param cityId
     * @return
     */
    List<MiniuiEntity> getStoreSimpleList(Integer cityId);
}