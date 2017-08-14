package com.lifang.agentsm.service;

import java.util.Map;

import com.lifang.agentsm.model.RulePublic;


/**
 * luogq
 * 2015-11-3
 */
public interface RulePublicService {


	String savePublicRule(Map<String, Object> pars);

	RulePublic findRulePublic();

	/**
	 * 转店公客,区公客线程
	 */
	void runMain();

    void runReport();

	void runHouseCountMain();
}
