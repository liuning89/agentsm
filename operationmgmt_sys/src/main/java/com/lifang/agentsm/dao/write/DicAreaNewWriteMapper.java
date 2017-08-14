package com.lifang.agentsm.dao.write;

import com.lifang.agentsm.entity.DicAreaNew;

public interface DicAreaNewWriteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DicAreaNew record);

    int insertSelective(DicAreaNew record);

    int updateByPrimaryKeySelective(DicAreaNew record);

    int updateByPrimaryKeyWithBLOBs(DicAreaNew record);

    int updateByPrimaryKey(DicAreaNew record);
}