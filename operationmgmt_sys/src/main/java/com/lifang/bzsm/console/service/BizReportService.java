package com.lifang.bzsm.console.service;

import com.lifang.bzsm.console.model.BizReportRequest;
import com.lifang.bzsm.console.model.LfBzAgentReportRequest;
import com.lifang.bzsm.console.report.read.BizSimpReportReadMapper;
import com.lifang.bzsm.console.report.read.LfBzAgentReportReadMapper;
import com.lifang.bzsm.console.report.read.StoreBusinessReportReadMapper;
import com.lifang.model.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BizReportService {

    @Autowired
    private LfBzAgentReportReadMapper agentReportReadMapper;
    @Autowired
    private StoreBusinessReportReadMapper storeBusinessReportReadMapper;
    @Autowired
    private BizSimpReportReadMapper bizSimpReportReadMapper;

    public PageResponse getAgentReportList(LfBzAgentReportRequest req) {
        req.setPageIndex(req.getPageIndex() * req.getPageSize());
        PageResponse pageResponse = new PageResponse("success", 1, agentReportReadMapper.getBzAgentReportList(req));
        pageResponse.setTotal(agentReportReadMapper.getBzAgentReportListCount(req));
        return pageResponse;
    }


    public PageResponse getStoreBizReport(LfBzAgentReportRequest req) {
        req.setPageIndex(req.getPageIndex() * req.getPageSize());
        PageResponse reponse = new PageResponse("success", 1, storeBusinessReportReadMapper.getStoreBizReport(req));
        reponse.setTotal(storeBusinessReportReadMapper.getStoreBizReportTotal(req));
        return reponse;
    }

    public List<Map<String, Object>> getExportAgentBizSimpReport(BizReportRequest req) {
        return bizSimpReportReadMapper.getExportAgentBizSimpReport(req);
    }

    public PageResponse getStoreBizSimpReport(BizReportRequest req) {
        req.setPageIndex(req.getPageIndex() * req.getPageSize());
        PageResponse reponse = new PageResponse("success", 1, bizSimpReportReadMapper.getStoreBizSimpReport(req));
        reponse.setTotal(bizSimpReportReadMapper.getStoreBizSimpReportTotal(req));
        return reponse;
    }
    public List<Map<String, Object>> getExportStoreBizSimpReport(BizReportRequest req) {
        return bizSimpReportReadMapper.getExportStoreBizSimpReport(req);
    }

    public PageResponse getAreaBizSimpReport(BizReportRequest req) {
        req.setPageIndex(req.getPageIndex() * req.getPageSize());
        PageResponse reponse = new PageResponse("success", 1, bizSimpReportReadMapper.getAreaBizSimpReport(req));
        reponse.setTotal(bizSimpReportReadMapper.getAreaBizSimpReportTotal(req));
        return reponse;
    }

    public List<Map<String, Object>> getExportAreaBizSimpReport(BizReportRequest req) {
        return bizSimpReportReadMapper.getExportAreaBizSimpReport(req);
    }

    public PageResponse getAgentBizSimpReport(BizReportRequest req) {
        req.setPageIndex(req.getPageIndex() * req.getPageSize());
        PageResponse reponse = new PageResponse("success", 1, bizSimpReportReadMapper.getAgentBizSimpReport(req));
        reponse.setTotal(bizSimpReportReadMapper.getAgentBizSimpReportTotal(req));
        return reponse;
    }


}
