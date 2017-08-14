package com.lifang.agentsm.dao.write;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lifang.agentsm.model.HouseContactSeeLog;
import com.lifang.agentsm.model.HouseEditRecord;
import com.lifang.agentsm.model.HouseInvalidReasonRecord;
import com.lifang.agentsm.model.LfAgentOperation;

public interface HouseResourceWriteMapper {

	int saveUpdateAgent(@Param("houseAgentId")Integer houseAgentId, @Param("houseId")Integer houseId,@Param("flag")String flag,@Param("id")Integer id);
	int deleteAgent(@Param("houseAgentId")Integer houseAgentId, @Param("houseId")Integer houseId,@Param("flag")String flag,@Param("id")Integer id);
	int updateAgentOperation(@Param("houseId") int houseId, @Param("type") int type);
	void updateCommAgent(int houseId);
	void insertCommAgent(LfAgentOperation o );
	int add(int houseId);
    void saveHouseEditRecord(HouseEditRecord eRecord);
    void updateHouseInfoStatus(Map map);
    int saveHouseContactLog(HouseContactSeeLog log);
    void saveUnstatusReason(HouseInvalidReasonRecord hirr);
    void updateShield(Map<String, Object> pars);
}
