package com.lifang.agentsm.service.impl;

import com.lifang.agentsm.dao.read.DicAreaNewReadMapper;
import com.lifang.agentsm.dao.read.RulePublicReadMapper;
import com.lifang.agentsm.entity.DicAreaNew;
import com.lifang.agentsm.model.HouseCountTown;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.service.DicAreaNewService;
import com.lifang.agentsm.service.HouseDataReport;
import com.lifang.bzsm.console.report.read.HouseDataReadMapper;
import com.lifang.bzsm.console.report.read.LfBzAreaReportReadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *房源数据报表
 */
@Service
public class HouseDataReportImpl implements HouseDataReport {
	@Autowired
	private HouseDataReadMapper rulePublicReadMapper;

	@Override
	public Map<String, Object>  getHouseDataTown(Map<String, Object> pars) {
		List<HouseCountTown> houseCountTownsList = null;
		int count = rulePublicReadMapper.getHouseDataTownCount(pars);
		if(count > 0) {
			houseCountTownsList = rulePublicReadMapper.getHouseDataTown(pars);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", houseCountTownsList);
		map.put("total", count);
		return map;
	}
}
