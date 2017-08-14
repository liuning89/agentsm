package com.lifang.agentsm.dao.write;

import java.util.Map;

import com.lifang.agentsm.entity.LfEmployee;

public interface LfEmployeeWriteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LfEmployee record);

    int insertSelective(LfEmployee record);

    int updateByPrimaryKeySelective(LfEmployee record);

    int updateByPrimaryKey(LfEmployee record);

	void updatePw(Map<String, Object> map);
}