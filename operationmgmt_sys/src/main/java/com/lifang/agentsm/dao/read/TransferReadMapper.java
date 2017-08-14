package com.lifang.agentsm.dao.read;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.FranchiseeDic;
import com.lifang.agentsm.model.FranchiseeArea;
import com.lifang.agentsm.model.LfFranchiseeInfo;
import com.lifang.agentsm.model.LfFranchiseeStore;
import com.lifang.agentsm.model.LfTownInfo;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.model.PublicReport;
import com.lifang.agentsm.model.TransferResouce;
import com.lifang.agentsm.model.TransferStore;


public interface TransferReadMapper {

	int findFranchiseeListCount(Map<String, Object> pars);

	List<LfFranchiseeInfo> findFranchiseeList(Map<String, Object> pars);

	List<LfFranchiseeInfo> findFranchiseeById(Map<String, Object> pars);

	int findFranchiseeStoreCount(Map<String, Object> pars);

	List<LfFranchiseeStore> findFranchiseeStoreList(Map<String, Object> pars);

	String findAreaOrg(Map<String, Object> pars);

	int findFranchiseeStoreById(Map<String, Object> pars);

	List<TransferResouce> findtransferKeyAgentList(Map<String, Object> pars);

	List<TransferResouce> findtransferKeyAgentInList(Map<String, Object> pars);

	List<TransferResouce> findPublishInList(Map<String, Object> pars);

	List<TransferResouce> findPictureAgentList(Map<String, Object> pars);

	List<TransferResouce> findCommAgentList(Map<String, Object> pars);

	List<TransferResouce> findMaintainAgentList(Map<String, Object> pars);

	List<TransferResouce> findCustomerList(Map<String, Object> pars);

	List<TransferResouce> findEstateList(Map<String, Object> pars);

	List<TransferResouce> findCusSeeList(Map<String, Object> pars);

	String findCode(Map<String, Object> pars);

	List<TransferStore> findNameById(Map<String, Object> pars);

	List<LfTownInfo> findTransferList(Map<String, Object> pars);
	List<LfTownInfo> findAllCountList(Map<String, Object> pars);

	int findHouseCount(Map<String, Object> pars);

	int findEaste(String orgCodeIn);

	int findCustomer(String orgCodeIn);

	int findcusSee(String orgCodeIn);

	List<TransferResouce> findPictureAgentListList(Map<String, Object> pars);

	List<TransferResouce> findCommAgentListOut(Map<String, Object> pars);

	List<TransferResouce> findMaintainAgentListListOut(Map<String, Object> pars);

	List<TransferResouce> findkeyAgentListOut(Map<String, Object> pars);

	List<TransferResouce> findcustomerListOut(Map<String, Object> pars);

	List<String> findTownIdIn(Map<String, Object> pars);

	List<String> findTownIdOutList(Map<String, Object> pars);

	List<String> findAgentIdInList(Map<String, Object> pars);

	List<String> findAgentIdOutList(Map<String, Object> pars);

	List<TransferResouce> findPublishInBoolean(Map<String, Object> pars);

	List<TransferResouce> findCustomerBoolean(Map<String, Object> pars);

	List<TransferResouce> findEstateBoolean(Map<String, Object> pars);

	List<TransferResouce> findCusSeeBoolean(Map<String, Object> pars);

	List<String> findFranchinseeByName(Map<String, Object> pars);

	List<TransferResouce> findCusGuestList(Map<String, Object> pars);

	List<TransferResouce> findHouseSeeList(Map<String, Object> pars);

	List<TransferResouce> findRequirementListList(Map<String, Object> pars);

	String findTownIdById(String townId);

	Integer findEstateCount(Map<String, Object> pars);

	Integer findCustomerCount(Map<String, Object> pars);

	Integer findCusSeeCount(Map<String, Object> pars);

	Integer findCusGuestCount(Map<String, Object> pars);

	Integer findHouseSeeCount(Map<String, Object> pars);

	Integer findRequirementCount(Map<String, Object> pars);

	List<FranchiseeDic> getAreaByCityId(Map<String, Object> pars);

	List<FranchiseeArea> findFranchiseeArea(Long id);

	List<FranchiseeDic> getData2AreaByAreaId(List<String> list);

	List<String> findAreaById(String string);

	List<TransferResouce> findPictureInBoolean(Map<String, Object> pars);

	List<TransferResouce> findKeyInBoolean(Map<String, Object> pars);

	List<TransferResouce> findCommInBoolean(Map<String, Object> pars);

	List<TransferResouce> findMaintainInBoolean(Map<String, Object> pars);

	int findCusGuestInBoolean(Map<String, Object> pars);

	int findhouseSeeInBoolean(Map<String, Object> pars);

	int findCustomerInBoolean(Map<String, Object> pars);

	int findCInBoolean(Map<String, Object> pars);

	int findEstateInBoolean(Map<String, Object> pars);

	int findcusSeeInBoolean(Map<String, Object> pars);
	
	/**
	 * 
	 * 根据城市编号获取加盟商列表，志远是特殊的
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年12月23日      新建
	 * </pre>
	 *
	 * @param cityId
	 * @return
	 */
	public List<MiniuiEntity> getSimpleFranchiseeListByCityId(int cityId);

    int findPubReportCount(Map<String, Object> map);

    List<PublicReport> findPubReport(Map<String, Object> map);

	String findIdByCityId(Map<String, Object> pars);

    PublicReport findPubReportByParm(Map<String, Object> pars);
}