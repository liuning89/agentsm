package com.lifang.agentsm.service;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.LfCompany;
import com.lifang.agentsm.model.CompanyInfoModel;

public interface CompanyService {
	/**
	 * @author fangyouhui
	 * 公司列表
	 * @return
	 */
	public Map<String, Object> getCompany(Map<String, Object> pars);
	
	 /**
     * @author fangyouhui
     * 添加公司
     * @param pars
     * @return
     */
    public int addCompany(LfCompany pars);
    /**
     * @author fangyouhui
     * @param companyId
     * @return
     */
    public int updateCompanyById(LfCompany pras);
    
    public int deleteCompanyById(Integer companyId);
    
    public List<CompanyInfoModel> getCompany();
    
    public List<LfCompany> selectByPars(Integer cityId,String name);
	
}
