package com.lifang.agentsm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.read.LfAgentReadMapper;
import com.lifang.agentsm.dao.read.LfCompanyReadMapper;
import com.lifang.agentsm.dao.read.LfStoreReadMapper;
import com.lifang.agentsm.dao.write.LfCompanyWriteMapper;
import com.lifang.agentsm.entity.LfCompany;
import com.lifang.agentsm.model.CompanyInfoModel;
import com.lifang.agentsm.service.CompanyService;
@Service(value = "companyService")
public class CompanyServiceImpl implements CompanyService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LfCompanyReadMapper lfCompanyReadMapper;
	
	@Autowired
	private LfCompanyWriteMapper lfCompanyWriteMapper;
	
	@Autowired
	private LfAgentReadMapper lfAgentReadMapper;
	
	@Autowired
	private LfStoreReadMapper lfStoreReadMapper;
	
	@Override
	public Map<String, Object> getCompany(Map<String, Object> pars) {
		int count = lfCompanyReadMapper.getCompanyCount(pars);
		Map<String, Object> map = new HashMap<String, Object>();
    	map.put("rows", count > 0 ? lfCompanyReadMapper.selectCompanyList(pars) : new ArrayList<>());
		map.put("total", count);
		return map;
	}
	
	
	@Override
	public int addCompany(LfCompany pars) {
		//根据公司名称和cityId查重
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cityId", pars.getCityId());
		map.put("companyName", pars.getCompanyName());
		LfCompany company = lfCompanyReadMapper.selectExitCompany(map);
		if(company==null){
			logger.info("新增公司，pars:{}",JSONObject.fromObject(pars).toString());
			pars.setStatus((byte)1);
			pars.setCreateTime(new Date());
			return lfCompanyWriteMapper.insertSelective(pars);
		}else{
			if(company.getStatus() == 2){
				logger.info("被删除的公司重新打开,pars:{}",JSONObject.fromObject(pars).toString());
				BeanUtils.copyProperties(pars, company);
				company.setStatus((byte)1);
				company.setCreateTime(new Date());
				return lfCompanyWriteMapper.updateByPrimaryKeySelective(company);
			}
		}
		return 0;
	}
	
	@Override
	public int updateCompanyById(LfCompany pras) {
		if(lfCompanyReadMapper.selectByPrimaryKey(pras.getId()) !=null ){
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("cityId", pras.getCityId());
//			map.put("companyName", pras.getCompanyName());
//			if(lfCompanyReadMapper.selectExitCompany(map)==null){
//				return lfCompanyWriteMapper.updateByPrimaryKeySelective(pras);
//			}
			return lfCompanyWriteMapper.updateByPrimaryKeySelective(pras);
		}
		return 0;
	}

	@Override
	public int deleteCompanyById(Integer companyId) {
		LfCompany company = lfCompanyReadMapper.selectByPrimaryKey(companyId);
		if(company != null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("companyId", companyId);
			if(lfStoreReadMapper.selectStortExit(map).size() > 0 ){
				return -1;
			}
			//company.setStatus((byte)2);
			//return lfCompanyWriteMapper.updateByPrimaryKeySelective(company);
			return lfCompanyWriteMapper.deleteByPrimaryKey(company.getId());
		}
		return 0;
	}
	
	@Override
	public List<CompanyInfoModel> getCompany() {
		return lfCompanyReadMapper.selectCompanyList(null);
	}
	
	@Override
	public List<LfCompany> selectByPars(Integer cityId,String name) {
		Map<String, Object> pars = new HashMap<>();
		pars.put("cityId", cityId);
		pars.put("name", name);
		return lfCompanyReadMapper.selectByPars(pars);
	}
}
