package com.lifang.agentsm.dao.write;

import com.lifang.agentsm.model.CommissionRule;
import com.lifang.agentsm.model.req.CommissionRuleReq;

import java.util.List;

/**
 * Created by admin on 2015/10/20.
 */
public interface CommissionRuleWriteMapper {

    public int update(CommissionRule commissionRule);

    public int delete(List<CommissionRule> commissionRules);

    public int addCRules(List<CommissionRule> commissionRules);

    public int insertPercentage(CommissionRule commissionRules);

    public int updatePercentage(CommissionRule commissionRules);

}
