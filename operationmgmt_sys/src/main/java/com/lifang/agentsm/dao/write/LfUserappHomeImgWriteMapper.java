package com.lifang.agentsm.dao.write;

import com.lifang.agentsm.entity.LfUserappHomeImg;

public interface LfUserappHomeImgWriteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LfUserappHomeImg record);

    int insertSelective(LfUserappHomeImg record);

    int updateByPrimaryKeySelective(LfUserappHomeImg record);

    int updateByPrimaryKey(LfUserappHomeImg record);
    
    int updateByType(LfUserappHomeImg record);
}