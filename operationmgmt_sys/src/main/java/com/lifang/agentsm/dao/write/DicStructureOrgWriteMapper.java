package com.lifang.agentsm.dao.write;

import com.lifang.agentsm.entity.DicStructureOrg;

public interface DicStructureOrgWriteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DicStructureOrg record);

    int insertSelective(DicStructureOrg record);

    int updateByPrimaryKeySelective(DicStructureOrg record);

    int updateByPrimaryKey(DicStructureOrg record);
}