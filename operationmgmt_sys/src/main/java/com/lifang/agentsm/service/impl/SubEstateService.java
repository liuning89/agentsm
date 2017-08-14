package com.lifang.agentsm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.read.SubEstateReadMapper;
import com.lifang.agentsm.model.MiniuiEntity;

/**
 * Author:CK
 * 创建时间：2015年5月30日
 */
@Service
public class SubEstateService {
	@Autowired
	private SubEstateReadMapper subEstateReadMapper;
	
	
	public List<MiniuiEntity> getBuildingListBySubEstateId(Integer subEstateId){
		return subEstateReadMapper.getBuildingListBySubEstateId(subEstateId);
	}
}

