package com.lifang.agentsm.dao.write;

import com.lifang.agentsm.model.FranchiseeArea;
import com.lifang.agentsm.model.TransferResouce;

import java.util.List;
import java.util.Map;

public interface TransferWriteMapper {

	Integer insertSelective(Map<String, Object> pars);

	void updateFranchiseeInfo(Map<String, Object> pars);

	void saveFranchiseeStore(Map<String, Object> pars);

	void updateFranchiseeStore(Map<String, Object> pars);

	void updateKeyAgentBath(List<TransferResouce> trList);

	void updatePublishAgentBath(List<TransferResouce> transferPublishInList);

	void updatePictureAgentBath(List<TransferResouce> transferPictureAgentInList);

	void updateCommAgentBath(List<TransferResouce> transferCommAgentInList);

	void updateMaintainAgentBath(List<TransferResouce> transferMaintainAgentInList);

	void updateCustomerBath(List<TransferResouce> transferCustomerList);

	void updateEstateBath(List<TransferResouce> transferEstateList);

	void updateCusSeeBath(List<TransferResouce> transferCusSeeList);

	void updatePublishAgent(Map<String, Object> map);

	void updatePictureAgent(Map<String, Object> map);

	void updateCommAgent(Map<String, Object> map);

	void updatekeyAgent(Map<String, Object> map);

	void updateMaintainAgent(Map<String, Object> map);

	void updateCustomer(Map<String, Object> map);

	void updateCusGuestBath(List<TransferResouce> cusGuestList);

	void updateHouseSeeBath(List<TransferResouce> houseSeeList);

	void updateRequirementBath(List<TransferResouce> requirementList);

	void insertFranchiseeAreaBatch(List<FranchiseeArea> fList);

	void deleteFranchiseeAreaById(String id);

    void updatePush(Map<String, Object> pars);

	void inserHouseAuto(String keyString);

    int resumOperator(int id);
}
