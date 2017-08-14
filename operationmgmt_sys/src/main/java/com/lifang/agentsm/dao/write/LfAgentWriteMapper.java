package com.lifang.agentsm.dao.write;

import com.lifang.agentsm.entity.LfAgent;

public interface LfAgentWriteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LfAgent record);

    int insertSelective(LfAgent record);

    int updateByPrimaryKeySelective(LfAgent record);

    int updateByPrimaryKey(LfAgent record);
}