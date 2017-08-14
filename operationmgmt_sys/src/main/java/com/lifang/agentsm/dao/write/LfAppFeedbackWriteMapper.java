package com.lifang.agentsm.dao.write;

import com.lifang.agentsm.entity.LfAppFeedback;

public interface LfAppFeedbackWriteMapper {
    
    int deleteByPrimaryKey(Long id);

    int insert(LfAppFeedback record);

    int insertSelective(LfAppFeedback record);

    int updateByPrimaryKeySelective(LfAppFeedback record);

    int updateByPrimaryKey(LfAppFeedback record);
}