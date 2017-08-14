package com.lifang.bzsm.console.controller;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.utils.OrgCodeUtil;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.enums.SystemInfo;
import com.lifang.bzsm.console.entity.Employee;
import com.lifang.bzsm.console.model.LfBzAgentReportRequest;
import com.lifang.bzsm.console.service.BZAreaReportService;
import com.lifang.bzsm.console.service.BZLfAreaOrgService;
import com.lifang.model.PageResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/bzareareport")
public class LfBzAreaReportController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(LfBzAreaReportController.class);

    @Autowired
    private BZAreaReportService bzAreaReportService;

    @Autowired
    private BZLfAreaOrgService lfAreaOrgService;

    @Autowired
    private AgentSOAServer agentSOAServer;
 
    @RequestMapping(value = "/gotoAreaBzReport.action")
    public String gotoAgentBzReport(HttpServletRequest request, HttpServletResponse response) {
        return "bzreport/areaBzReport";
    }

    @RequestMapping(value = "/getAreaBzReport.action")
    @ResponseBody
    public PageResponse getAgentBzReport(HttpSession session, LfBzAgentReportRequest req) {
        PageResponse response = null;

        LfEmployee emp = getLoginEmployeeInfo(session);

        if (OrgCodeUtil.isAgent(agentSOAServer, emp)) {
            req.setAgentId(emp.getId());
        }
        else {
            List<Integer> areaList = new ArrayList();
            if (req.getAreaId() != null && req.getAreaId() > 0) {
                areaList.add(req.getAreaId());
            }
            else {
                areaList = OrgCodeUtil.getAreadListByEmployeeId(req.getCityId(), agentSOAServer, emp);
            }

            if (areaList != null && areaList.size() > 0) {
                req.setAreaIds(areaList);
            }
        }

        //处理时间
        processDate(req);

        response = bzAreaReportService.getAreaReportList(req);
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
