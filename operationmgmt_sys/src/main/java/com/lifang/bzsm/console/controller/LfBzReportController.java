package com.lifang.bzsm.console.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.utils.OrgCodeUtil;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.enums.SystemInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.bzsm.console.entity.Employee;
import com.lifang.bzsm.console.model.LfBzAgentReportRequest;
import com.lifang.bzsm.console.service.BizReportService;
import com.lifang.bzsm.console.service.lfAreaOrgBzService;
import com.lifang.model.PageResponse;

@Controller
@RequestMapping("/bzreport")
public class LfBzReportController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(LfBzReportController.class);

    @Autowired
    private BizReportService bizReportService;


    @Autowired
    private lfAreaOrgBzService lfAreaOrgService;

    @Autowired
    private AgentSOAServer agentSOAServer;
 
    @RequestMapping(value = "/gotoAgentBzReport.action")
    public String gotoAgentBzReport(HttpServletRequest request, HttpServletResponse response) {
        return "bzreport/agentBzReport";
    }

    @RequestMapping(value = "/getAgentBzReport.action")
    @ResponseBody
    public PageResponse getAgentBzReport(HttpSession session, LfBzAgentReportRequest req) {
        PageResponse response = new PageResponse();

        // 计算出当前员工的组织结构的code
        LfEmployee emp = getLoginEmployeeInfo(session);

        if(OrgCodeUtil.isAgent(agentSOAServer, emp))
        {
            req.setAgentId(emp.getId());
        }
        else {
            List<Integer> storeList = new ArrayList();
            if (req.getStoreId() != null && req.getStoreId() > 0) {
                storeList.add(req.getStoreId());
            }
            else {
                storeList = OrgCodeUtil.getStoreListByEmployeeId(req.getCityId(), req.getAreaId(), agentSOAServer, emp);
            }

            if (storeList != null && storeList.size() > 0) {
                req.setStoreIds(storeList);
            }
        }

        //处理时间
        processDate(req);

        response = bizReportService.getAgentReportList(req);
        return response;
    }

    private void processDate(LfBzAgentReportRequest req)
    {
        if(req.getDateEnd() != null)
        {
            Calendar g = Calendar.getInstance();
            g.setTime(req.getDateEnd());
            g.add(Calendar.DATE, 1);
            Date d2 = g.getTime();
            req.setDateEnd(d2);
        }
    }

}
