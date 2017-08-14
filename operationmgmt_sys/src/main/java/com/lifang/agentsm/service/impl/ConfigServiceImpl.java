package com.lifang.agentsm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.read.ConfigReadMapper;
import com.lifang.agentsm.entity.Config;
import com.lifang.agentsm.service.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	private ConfigReadMapper configReadMapper;

	@Override
	public List<Config> selectByConfigList() {
		return configReadMapper.selectByConfigList();
	}

}
