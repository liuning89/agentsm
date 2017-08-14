package com.lifang.agentsm.dao.write;

import java.util.Map;

/**
 * 
 *
 * @author   luogq
 * @Date	 2015年11月3日
 *
 * @see
 */
public interface RulePublicWriteMapper {


	void insertPublicRule(Map<String, Object> pars);

	void updatePublicRule(Map<String, Object> pars);

	void updatePubCustomer(Map<String, Object> pars);

	void insertPubCustomer(Map<String, Object> map);

	void insertPubCustomerStore(Map<String, Object> map);

    void insertPubreport(Map<String, Object> map);

	void updatePubReport(Map<String, Object> pars);

	void insertPubreportrl(Map<String, Object> pars);

	void updateAreaReport(Map<String, Object> pars);

	void insertAreaReportrl(Map<String, Object> areapars);
}
