package com.lifang.agentsm.dao.read;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.model.CommissionRule;
import com.lifang.agentsm.model.req.CommissionRuleReq;

import java.util.List;

/**
 * Created by admin on 2015/10/20.
 */
public interface CommissionRuleReadMapper {

    public List<CommissionRule> list(Pagination req);


    CommissionRuleReq getPercentage( );
}
