package com.lifang.agentsm.dao.write;

import java.util.Map;

import com.lifang.agentsm.entity.LfSellHouseAgent;
import com.lifang.agentsm.entity.ResourceTransferRequest;

public interface LfSellHouseAgentWriteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LfSellHouseAgent record);

    int insertSelective(LfSellHouseAgent record);

    int updateByPrimaryKeySelective(LfSellHouseAgent record);

    int updateByPrimaryKey(LfSellHouseAgent record);
    
    /**
     * 
     * 功能描述:设置新的钥匙经纪人
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     bianjie:   2015年5月28日      新建
     * </pre>
     *
     * @param param
     * @return
     */
    int updateKeyAgentToNew(ResourceTransferRequest resourceTransferRequest);
    
    /**
     * 功能描述:设置新的发布人
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     bianjie:   2015年5月28日      新建
     * </pre>
     *
     * @param param
     * @return
     */
    int updatePublishAgentToNew(ResourceTransferRequest resourceTransferRequest);
    
    /**
     * 功能描述:设置新的委托经纪人
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     bianjie:   2015年5月28日      新建
     * </pre>
     *
     * @param param
     * @return
     */
    int updateCommAgenttToNew(ResourceTransferRequest resourceTransferRequest);
    
    /**
     * 功能描述:设置新的实景发布人(照片人)
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     bianjie:   2015年5月28日      新建
     * </pre>
     *
     * @param param
     * @return
     */
    int updatePictureAgentToNew(ResourceTransferRequest resourceTransferRequest);
    
    /**
     * 功能描述:设置新的买方经纪人
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     bianjie:   2015年5月28日      新建
     * </pre>
     *
     * @param param
     * @return
     */
    int updateBuyAgentIdToNew(ResourceTransferRequest resourceTransferRequest);
    
    /**
     * 
     * 功能描述:关联更改客源需求agnetId
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     bianjie:   2015年5月30日      新建
     * </pre>
     *
     * @param resourceTransferRequest
     * @return
     */
    int updateCustRequirement(ResourceTransferRequest resourceTransferRequest);
    
    
    /**
     * 
    * @Title: updateHouseSeeAgentToNew 
    * @Description: 更新越看客户到新的经纪人
    * @param @param resourceTransferRequest
    * @param @return    设定文件 
    * @return int    返回类型 
    * @throws
     */
    int updateHouseSeeAgentToNew(ResourceTransferRequest resourceTransferRequest);

	void updateHouseAgent(Map<String, Object> pars);

	void insertHouseAgent(Map<String, Object> pars);
}