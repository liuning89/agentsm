package com.lifang.agentsm.dao.read;

import java.util.List;

import com.lifang.agentsm.entity.Config;

public interface ConfigReadMapper {
    Config selectByPrimaryKey(Integer id);
    public List<Config> selectByConfigList();
}