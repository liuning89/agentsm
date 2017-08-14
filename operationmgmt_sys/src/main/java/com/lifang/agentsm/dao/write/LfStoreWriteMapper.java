package com.lifang.agentsm.dao.write;

import com.lifang.agentsm.entity.LfStore;

public interface LfStoreWriteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LfStore record);

    int insertSelective(LfStore record);

    int updateByPrimaryKeySelective(LfStore record);

    int updateByPrimaryKey(LfStore record);
}