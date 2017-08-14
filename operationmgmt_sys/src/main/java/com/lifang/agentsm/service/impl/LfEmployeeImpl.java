package com.lifang.agentsm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.read.LfEmployeeReadMapper;
import com.lifang.agentsm.dao.write.LfEmployeeWriteMapper;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.LfAppFeedbackInfo;
import com.lifang.agentsm.service.LfEmployeeService;

@Service(value = "lfEmployeeService")
public class LfEmployeeImpl implements LfEmployeeService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LfEmployeeReadMapper lfEmployeeReadMapper;

    @Autowired
    private LfEmployeeWriteMapper lfEmployeeWriteMapper;

    /**
     * 功能描述:登陆
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     bianjie:   2015年5月7日      新建
     * </pre>
     *
     * @param map
     * @return
     */
    @Override
    public LfEmployee loginCheck(Map map) {

        LfEmployee crmUser = null;
        List list = new ArrayList();
        list = lfEmployeeReadMapper.loginCheck(map);
        if (!list.isEmpty() && list.size() > 0) {
            crmUser = (LfEmployee) list.get(0);
        }
        return crmUser;
    }

    @Override
    public Map<String, Object> selectEmployeeListByPage(Map pars) {
        int count = lfEmployeeReadMapper.selectEmployeeListCount(pars);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", count > 0 ? lfEmployeeReadMapper.selectEmployeeListByPage(pars) : new ArrayList<>());
        map.put("total", count);
        return map;
    }

    @Override
    public int addLfEmployee(LfEmployee lfEmployee) {
        return lfEmployeeWriteMapper.insertSelective(lfEmployee);
    }

    @Override
    public int updateLfEmployeeById(LfEmployee employee) {
        return lfEmployeeWriteMapper.updateByPrimaryKeySelective(employee);
    }

    @Override
    public int removeLfEmployeeById(Integer id) {
        return lfEmployeeWriteMapper.deleteByPrimaryKey(id);
    }

    @Override
    public LfEmployee selectEmployeeById(Integer id) {
        return lfEmployeeReadMapper.selectByPrimaryKey(id);
    }

    @Override
    public int checkEmployeeExists(Map map) {
        
        return lfEmployeeReadMapper.checkEmployeeExists(map);
    }

    @Override
    public List<LfEmployee> getByAreaOrg(Map<String,Object> map){
        return lfEmployeeReadMapper.getByAreaOrg(map);
    }

	@Override
	public void updatePw(Map<String, Object> map) {
		lfEmployeeWriteMapper.updatePw(map);
	}
}
