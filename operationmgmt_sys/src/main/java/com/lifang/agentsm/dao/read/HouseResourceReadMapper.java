package com.lifang.agentsm.dao.read;
import java.util.List;
import java.util.Map;

import com.lifang.housesoa.model.HouseResourceConcact;
import org.apache.ibatis.annotations.Param;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.entity.LfSellHouseAgent;
import com.lifang.agentsm.model.AgentSupport;
import com.lifang.agentsm.model.ComboModel;
import com.lifang.agentsm.model.HouseInvalidStatusReason;
import com.lifang.agentsm.model.HouseSellResource;
import com.lifang.agentsm.model.LfAuditInvalid;
import com.lifang.agentsm.model.LfHouseFollowUp;
import com.lifang.agentsm.model.LfHouseFollowUpReq;
import com.lifang.agentsm.model.LfHouseOwnReq;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.model.SellHouseListModel;

public interface HouseResourceReadMapper {

	HouseSellResource loadHouseSellDetail(@Param("houseId")Integer houseId);
	
	/**
	 * @param req 查询条件
	 * @return
	 */
	List<LfHouseFollowUp> getLfHouseFollowUpList(LfHouseFollowUpReq req);

	List<LfHouseOwnReq> getAchievementOwnList(LfHouseOwnReq req);

	AgentSupport getAgentSupport(@Param("houseId")Integer houseId);

	List<ComboModel> getStoreListByHouseId(@Param("houseId")Integer houseId,@Param("cityId")Integer cityId ,@Param("franchiseeId") int franchiseeId);

	List<ComboModel> getAgentListByStoreId(@Param("houseId")Integer houseId,@Param("storeId")Integer storeId);

	List<LfHouseFollowUp> getLfHouseFollowUpList(Pagination pagination);
	
	
	/**
	 * @param id
	 * @return 获取详情
	 */
	LfHouseFollowUp getLfHouseFollowUpDetail(@Param("id")Integer id);
	
	
	/**
	 * 功能描述:查询出售房源列表
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     CK:   2015年5月29日      新建
	 * </pre>
	 *
	 * @param pagination {cityId,estateName,estateId,buildingId,room,startTime,endTime,status,agentType,store,agent}
	 * @return
	 */
	List<SellHouseListModel> getSellHouseList(Pagination pagination);

    /**
     * 功能描述：查询发布房源的审核记录
     * @param params
     * */
    List<Map<String,Object>> getHouseSellCheckRecordList(Map<String, Object> params);

    Integer getHouseSellCheckRecordCount(Map<String, Object> params);

	List<LfSellHouseAgent> getOwnByHouseId(int houseId);

	List<LfHouseFollowUp> getLfHouseFollowUpListAll(Pagination pagination);

    List<ComboModel> getFranchiseeList(int houseId);

    List<LfSellHouseAgent> getLfSellHouseAgent(int houseId);

    LfHouseOwnReq getOperationList(Map map);

    LfHouseOwnReq getCommAgent(@Param("houseId")int houseId,@Param("operator")Long operator);

    int findCityIdById(int cityCode);

    List<ComboModel> getFranchiseeListByCityId(@Param("cityId")int cityId);

    List<LfAuditInvalid> houseValidReasonList(int houseId);

    List<MiniuiEntity> getReasionList();

    List<HouseInvalidStatusReason> getInvalidReasonByHouseId(int houseId);

	

    List<HouseInvalidStatusReason> invalidReasonList(Pagination pagination);

    List<SellHouseListModel> getPublisherList(@Param("houseId")int houseId);
	HouseResourceConcact getHouseContactDataByContactId(int contactId);
	List<SellHouseListModel> getCommonList(@Param("houseId")int houseId);

	List<SellHouseListModel> getPictureList(@Param("houseId")int houseId);

	List<SellHouseListModel> getKeyList(@Param("houseId")int houseId);



}
