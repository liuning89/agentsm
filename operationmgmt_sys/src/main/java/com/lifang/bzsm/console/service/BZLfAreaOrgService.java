package com.lifang.bzsm.console.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.read.LfAreaOrgReadMapper;
import com.lifang.agentsm.entity.LfAreaOrg;



/**
 * Created by Administrator on 15-7-20.
 */
@Service(value="BZlfAreaOrgService")
public class BZLfAreaOrgService{

    @Autowired
    private LfAreaOrgReadMapper lfAreaOrgReadMapper;

    public List<LfAreaOrg> selectBy(Map<String, Object> param) {
        return lfAreaOrgReadMapper.selectBy(param);
    }
    
    public LfAreaOrg selectByPK(Integer id)
    {
        return lfAreaOrgReadMapper.selectByPrimaryKey(id.longValue());
    }
    
    public LfAreaOrg selectByCode(String code)
    {
        return lfAreaOrgReadMapper.selectByCode(code);
    }
    
    
//    public LfAreaOrg selectByEmployeeId(Integer empId)
//    {
//        return lfAreaOrgReadMapper.selectByEmployeeId(empId);
//    }

    public List<LfAreaOrg> selectByCodeAndLevel(Map<String, Object> param)
    {
        return lfAreaOrgReadMapper.selectByCodeAndLevel(param);
    }
    
}
