package com.lifang.agentsm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.model.LfAgentReportRequest;
import com.lifang.agentsm.model.ReportModel;
import com.lifang.agentsm.service.ReportCenterService;
import com.lifang.agentsm.service.impl.LfReportService;
import com.lifang.model.PageResponse;

@Controller
@RequestMapping("/report")
public class LfReportController {
    
    protected Logger logger = LoggerFactory.getLogger(LfReportController.class);
    
    @Autowired
    private LfReportService lfReportService;
    
    @Autowired
    private ReportCenterService reportService;
    
    
    @RequestMapping(value = "/gotoAgentBizReport.action")
    public String gotoEmployeeList(HttpServletRequest request, HttpServletResponse response) {
        return "report/agentBizReport";
    }
    
    @RequestMapping(value = "/gotoStoreBizReport.action")
    public String gotoStoreBizReport(HttpServletRequest request, HttpServletResponse response) {
        return "report/storeBizReport";
    }
    
    @RequestMapping(value = "/gotoStoreMonReport.action")
    public String gotoStoreMonReport(HttpServletRequest request, HttpServletResponse response) {
        return "report/storeMonReport";
    }
    
    @RequestMapping(value = "/agentBizReportList.action")
    @ResponseBody
    public PageResponse agentBizReportList(LfAgentReportRequest lfAgentReportRequest){
        PageResponse response = new PageResponse();
        response = lfReportService.getAgentReportList(lfAgentReportRequest);
        return response;
    }
    
    @RequestMapping(value = "/storeBizReportList.action")
    @ResponseBody
    public PageResponse storeBizReportList(LfAgentReportRequest lfAgentReportRequest){
        PageResponse response = new PageResponse();
        response = lfReportService.getStoreReportList(lfAgentReportRequest);
        return response;
    }
    
    @RequestMapping(value = "/storeMonReportList.action")
    @ResponseBody
    public PageResponse storeMonReportList(LfAgentReportRequest lfAgentReportRequest){
        PageResponse response = new PageResponse();
        response = lfReportService.getStoreMonReportList(lfAgentReportRequest);
        return response;
    }
    

    @RequestMapping(value = "/runReportSchedule.action")
    @ResponseBody
    public String runReportSchedule(LfAgentReportRequest agentReportRequest) {
        reportService.countReport(agentReportRequest);
        return "OK";
    }
    
    
    @RequestMapping(value = "/runReportMonthSchedule.action")
    @ResponseBody
    public String runReportMonthSchedule(LfAgentReportRequest agentReportRequest) {
        reportService.countStoreMonthReport(agentReportRequest);
        return "OK";
    }
}
