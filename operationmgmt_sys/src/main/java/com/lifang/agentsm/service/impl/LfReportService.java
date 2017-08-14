package com.lifang.agentsm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.dao.read.AgentReportReadMapper;
import com.lifang.agentsm.model.LfAgentReportRequest;
import com.lifang.agentsm.model.ReportModel;
import com.lifang.model.PageResponse;


@Service
public class LfReportService {
    
    @Autowired
    private AgentReportReadMapper agentReportReadMapper;
    
    public PageResponse getAgentReportList(LfAgentReportRequest req){
        if (req.getPageIndex() != null) {
            req.setPageIndex(req.getPageIndex() + 1);
        }
        Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
        PageResponse pageResponse = new PageResponse("success", 1, agentReportReadMapper.getAgentReportList(pagination));
        pageResponse.setTotal(pagination.getTotal());
        return pageResponse;
    }
    
    public PageResponse getStoreReportList(LfAgentReportRequest req){
        if (req.getPageIndex() != null) {
            req.setPageIndex(req.getPageIndex() + 1);
        }
        Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
        PageResponse pageResponse = new PageResponse("success", 1, agentReportReadMapper.getStoreReportList(pagination));
        pageResponse.setTotal(pagination.getTotal());
        return pageResponse;
    }
    
    public PageResponse getStoreMonReportList(LfAgentReportRequest req){
        if (req.getPageIndex() != null) {
            req.setPageIndex(req.getPageIndex() + 1);
        }
        Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
        PageResponse pageResponse = new PageResponse("success", 1, agentReportReadMapper.getStoreMonReportList(pagination));
        pageResponse.setTotal(pagination.getTotal());
        return pageResponse;
    }
}
