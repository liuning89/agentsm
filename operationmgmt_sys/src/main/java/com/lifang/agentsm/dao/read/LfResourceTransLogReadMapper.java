package com.lifang.agentsm.dao.read;

import com.lifang.agentsm.model.LfResourceTransLog;

public interface LfResourceTransLogReadMapper {
    LfResourceTransLog selectByPrimaryKey(Long id);
}