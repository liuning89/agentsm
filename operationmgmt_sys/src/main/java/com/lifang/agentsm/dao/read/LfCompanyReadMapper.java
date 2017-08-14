package com.lifang.agentsm.dao.read;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.LfCompany;
import com.lifang.agentsm.model.CompanyInfoModel;

public interface LfCompanyReadMapper {
    LfCompany selectByPrimaryKey(Integer id);
    /**
     * @author fangyouhui
     * @return
     */
    int getCompanyCount(Map<String, Object> pars);;
    /**
     * 根据公司名称和cityId查询是否存在该公司
     * @param pars
     * @return
     */
    LfCompany selectExitCompany(Map<String, Object> pars);
    /**
     *  查询所有的公司
     * @param name   cityId
     * @return
     */
    List<CompanyInfoModel> selectCompanyList(Map<String, Object> pars);
    
    List<LfCompany> selectByPars(Map<String, Object> pars);
   
}