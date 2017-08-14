package com.lifang.agentsm.dao.write;

import com.lifang.agentsm.entity.LfCompany;

public interface LfCompanyWriteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LfCompany record);

    int insertSelective(LfCompany record);

    int updateByPrimaryKeySelective(LfCompany record);

    int updateByPrimaryKey(LfCompany record);
}