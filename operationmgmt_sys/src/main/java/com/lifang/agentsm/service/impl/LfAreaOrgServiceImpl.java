package com.lifang.agentsm.service.impl;

import com.lifang.agentsm.dao.read.LfAreaOrgReadMapper;
import com.lifang.agentsm.entity.LfAreaOrg;
import com.lifang.agentsm.service.LfAreaOrgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 15-7-20.
 */
@Service(value="lfAreaOrgService")
public class LfAreaOrgServiceImpl implements LfAreaOrgService {

    @Autowired
    private LfAreaOrgReadMapper lfAreaOrgReadMapper;

    @Override
    public List<LfAreaOrg> selectBy(HashMap<String, Object> param) {
        return lfAreaOrgReadMapper.selectBy(param);
    }
    public LfAreaOrg selectByCode(String code){
        return lfAreaOrgReadMapper.selectByCode(code);
    }
	@Override
	public List<LfAreaOrg> selectAgent(HashMap<String, Object> param) {
		
		return lfAreaOrgReadMapper.selectAgent(param);
	}
	@Override
	public LfAreaOrg selectByPK(Integer id) {
		 return lfAreaOrgReadMapper.selectByPrimaryKey(id.longValue());
	}
	@Override
	public LfAreaOrg selectByEmployeeId(Integer empId) {
		return lfAreaOrgReadMapper.selectByEmployeeId(empId);
	}
	@Override
	public List<LfAreaOrg> selectByType(HashMap<String, Object> param) {
		return lfAreaOrgReadMapper.selectByType(param);
	}
	@Override
	public List<LfAreaOrg> selectOnlyByType(HashMap<String, Object> param) {
		return lfAreaOrgReadMapper.selectOnlyByType(param);
	}
	@Override
	public LfAreaOrg selectByParentId(String parentId) {
		return lfAreaOrgReadMapper.selectByParentId(parentId);
	}
}
