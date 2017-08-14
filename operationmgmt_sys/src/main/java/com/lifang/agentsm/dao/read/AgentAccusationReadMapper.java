package com.lifang.agentsm.dao.read;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.model.AccusAtion;

/**
 * 举报dao
 * @author luogq
 *
 */
public interface AgentAccusationReadMapper {

    
    /**
     * 功能描述:根据参数获取区域、门店等信息
     * @param param
     * @return
     */
    List<AccusAtion> selectBy(Map<String, Object> param);

    int selectByCount(Map<String, Object> param);
}