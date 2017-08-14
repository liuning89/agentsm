package com.lifang.bzsm.console.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.bzsm.console.entity.LfAreaOrg;
import com.lifang.bzsm.console.report.read.LfBzAreaOrgReadMapper;

/**
 * Created by Administrator on 15-7-20.
 */
@Service(value="lfAreaOrgBzService")
public class lfAreaOrgBzService{

    @Autowired
    private LfBzAreaOrgReadMapper lfAreaOrgReadMapper;

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
