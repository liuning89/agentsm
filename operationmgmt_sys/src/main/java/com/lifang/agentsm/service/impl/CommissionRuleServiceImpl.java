package com.lifang.agentsm.service.impl;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.dao.read.CommissionRuleReadMapper;
import com.lifang.agentsm.dao.write.CommissionRuleWriteMapper;
import com.lifang.agentsm.model.CommissionRule;
import com.lifang.agentsm.model.req.CommissionRuleReq;
import com.lifang.agentsm.service.CommissionRuleService;
import com.lifang.model.PageResponse;
import com.lifang.model.Response;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2015/10/20.
 */
@Service
public class CommissionRuleServiceImpl implements CommissionRuleService {

    @Autowired
    CommissionRuleWriteMapper commissionRuleWriteMapper;

    @Autowired
    CommissionRuleReadMapper commissionRuleReadMapper;


    @Override
    public Response list(CommissionRuleReq req) {
        if (req.getPageIndex() != null) {
            req.setPageIndex(req.getPageIndex() + 1);
        }
        Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
        PageResponse response = new PageResponse("success", 1, commissionRuleReadMapper.list(pagination));
        response.setTotal(pagination.getTotal());
        return response;
    }


    public void insert(List<CommissionRule> commissionRules) {
        commissionRuleWriteMapper.addCRules(commissionRules);
    }


    public void delete(List<CommissionRule> commissionRules) {
        commissionRuleWriteMapper.delete(commissionRules);
    }


    public void update(CommissionRule commissionRule) {
        commissionRuleWriteMapper.update(commissionRule);
    }

    @Override
    public int insertPercentage(CommissionRule commissionRules) {
        return commissionRuleWriteMapper.insertPercentage(commissionRules);
    }
    @Override
    public int updatePercentage(CommissionRule commissionRules) {
        return commissionRuleWriteMapper.updatePercentage(commissionRules);
    }


    @Override
    public CommissionRuleReq getPercentage() {
        return commissionRuleReadMapper.getPercentage();
    }

}
