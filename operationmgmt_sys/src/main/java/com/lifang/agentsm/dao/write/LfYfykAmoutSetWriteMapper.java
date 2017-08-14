package com.lifang.agentsm.dao.write;

import java.util.Map;

import com.lifang.agentsm.model.LfAgentAccountDetail;
import com.lifang.agentsm.model.LfAgentFeeSet;
import com.lifang.agentsm.model.LfYfykAmoutSet;
import com.lifang.agentsm.model.LfYfykAmoutSetLog;
import com.lifang.agentsm.model.WkCoinGivelModel;

public interface LfYfykAmoutSetWriteMapper {

    int updateStatus(Map map);

    void insertLog(LfYfykAmoutSetLog log);

    int save(LfYfykAmoutSet amoutSet);

    int add(LfYfykAmoutSet amoutSet);

    int saveFeeSet(LfAgentFeeSet set);

    void saveLog(LfYfykAmoutSetLog log);

    int addByCompanySave(WkCoinGivelModel wkgive);

    void insertDetail(LfAgentAccountDetail detail);

}
