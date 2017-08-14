package com.lifang.agentsm.service;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.base.model.Response;
import com.lifang.agentsm.model.AgentSupport;
import com.lifang.agentsm.model.ComboModel;
import com.lifang.agentsm.model.HouseContactSeeLog;
import com.lifang.agentsm.model.HouseEditRecord;
import com.lifang.agentsm.model.HouseInvalidReasonRecord;
import com.lifang.agentsm.model.HouseInvalidStatusReason;
import com.lifang.agentsm.model.HouseSellResource;
import com.lifang.agentsm.model.LfAuditInvalid;
import com.lifang.agentsm.model.LfHouseFollowUp;
import com.lifang.agentsm.model.LfHouseFollowUpReq;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.model.SellHouseListModel;
import com.lifang.agentsm.model.SellHouseListRequest;
import com.lifang.agentsm.model.LfHouseOwnReq;
import com.lifang.housesoa.model.HouseResourceConcact;
import com.lifang.model.PageResponse;

public interface HouseResourceService {

	HouseSellResource loadHouseDetail(Integer houseId);
	
	/**
	 * @param req 查询条件
	 * @return 结果集
	 */
	PageResponse  getLfHouseFollowUpList(LfHouseFollowUpReq req);

	List<LfHouseOwnReq> getAchievementOwnList(LfHouseOwnReq req);

	AgentSupport getAgentSupport(Integer houseId);

	List<ComboModel> getStoreListByHouseId(Integer houseId,Integer cityId,int franchiseeId );

	List<ComboModel> getAgentListByStoreId(Integer houseId,Integer storeId);

	boolean saveUpdateAgent(Integer houseAgentId, Integer houseId,String flag,int id);

	
	/**
	 * @param id
	 * @return 详情
	 */
	LfHouseFollowUp getLfHouseFollowUpDetail(Integer id);
	
	
	/**
	 * 功能描述:获取出售房源列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     CK:   2015年5月29日      新建
	 * </pre>
	 *
	 * @param request
	 * @return
	 */
	PageResponse getSellHouseList(SellHouseListRequest request);

    /**
     * 功能描述：获取发布房源的审核记录列表
     * @param params
     * */
    Map<String, Object> getHouseSellCheckRecordList(Map<String, Object> params);

	boolean deleteAgent(Integer houseAgentId, Integer houseId, String flag,int id);

	Response addOwn(Integer houseId);

	PageResponse getLfHouseFollowUpListAll(LfHouseFollowUpReq req);

    void saveHouseEditRecord(HouseEditRecord eRecord);

    void updateHouseInfoByHouseId(Integer houseId, Integer houseResourceStatus);

    Response saveHouseContactLog(HouseContactSeeLog log);

    List<ComboModel> getFranchiseeList(int houseId);

    List<ComboModel> getFranchiseeListByCityId(int cityId);

    List<LfAuditInvalid> houseValidReasonList(int houseId);

    List<MiniuiEntity> getReasionList();

    List<HouseInvalidStatusReason> getInvalidReasonByHouseId(Integer houseId);

	void updateShield(Map<String, Object> pars);

    void saveUnstatusReason(HouseInvalidReasonRecord hirr);
	List<SellHouseListModel> getKeyList(int houseId);
    PageResponse invalidReasonList(HouseInvalidReasonRecord hir);

    List<SellHouseListModel> getPublisherList(int houseId);


	List<SellHouseListModel> getCommonList(int houseId);
	List<SellHouseListModel> getPictureList(int houseId);

	HouseResourceConcact getHouseContactDataByContactId(int contactId);

}
