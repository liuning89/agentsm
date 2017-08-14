package com.lifang.agentsm.service;

import com.lifang.agentsm.model.CommissionRule;
import com.lifang.agentsm.model.req.CommissionRuleReq;
import com.lifang.model.Response;

import java.util.List;

/**
 * Created by admin on 2015/10/20.
 */

public interface CommissionRuleService {


    /**
     * 佣金分配角色列表
     *
     * @param req
     * @return
     */

    public Response list(CommissionRuleReq req);

    public void insert(List<CommissionRule> commissionRules);

    public void delete(List<CommissionRule> commissionRules);

    public void update(CommissionRule commissionRule);

    public int insertPercentage(CommissionRule commissionRules);

    public int updatePercentage(CommissionRule commissionRules);

    public CommissionRuleReq getPercentage();

}
