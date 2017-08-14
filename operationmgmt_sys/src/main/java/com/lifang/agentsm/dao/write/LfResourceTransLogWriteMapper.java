package com.lifang.agentsm.dao.write;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.model.TransferResouce;

public interface LfResourceTransLogWriteMapper {

	void insertPublishAgentLog(List<TransferResouce> transferPublishInList);

	void insertPictureAgentLog(List<TransferResouce> transferPictureAgentInList);

	void insertCommAgentLog(List<TransferResouce> transferCommAgentInList);

	void insertMaintainAgentLog(List<TransferResouce> transferMaintainAgentInList);

	void insertKeyAgentLog(List<TransferResouce> transferAgentIdInList);

	void insertCustomerLog(List<TransferResouce> transferCustomerList);

	void insertEstateLog(List<TransferResouce> transferEstateList);

	void insertCusSeeLog(List<TransferResouce> transferCusSeeList);

	void savePublishAgentLog(Map<String, Object> map);

	void savePictureAgentLog(Map<String, Object> map);

	void saveCommAgentLog(Map<String, Object> map);

	void savekeyAgentLog(Map<String, Object> map);

	void saveMaintainAgentLog(Map<String, Object> map);

	void insertCusGuestLog(List<TransferResouce> cusGuestList);

	void insertHouseSeeLog(List<TransferResouce> houseSeeList);

	void insertRequirementLog(List<TransferResouce> requirementList);

}