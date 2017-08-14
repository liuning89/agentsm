package com.lifang.bzsm.console.controller;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.utils.OrgCodeUtil;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.enums.SystemInfo;
import com.lifang.bzsm.console.entity.Employee;
import com.lifang.bzsm.console.model.LfBzAgentReportRequest;
import com.lifang.bzsm.console.service.BizReportService;
import com.lifang.bzsm.console.service.BZLfAreaOrgService;
import com.lifang.model.PageResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by guanshuli on 2015/9/25.
 */

@Controller
@RequestMapping("/storebizreport")
public class StoreBusinessReportController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(StoreBusinessReportController.class);

    @Autowired
    private BizReportService bZReportService;


    @Autowired
    private BZLfAreaOrgService lfAreaOrgService;

    @Autowired
    private AgentSOAServer agentSOAServer;

    //导航请求路径
    @RequestMapping(value = "/storeBizReport.action")
    public String agentListMiniIndex() {
        return "bzreport/storeBizReport";
    }


    @RequestMapping(value = "/getstorebizreport.action")
    @ResponseBody
    public Object agentListData(HttpSession session, LfBzAgentReportRequest req) {
        PageResponse response = new PageResponse();
        // 计算出当前员工的组织结构的code
        LfEmployee emp = getLoginEmployeeInfo(session);

        if (OrgCodeUtil.isAgent(agentSOAServer, emp)) {
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

        return bZReportService.getStoreBizReport(req);
    }

    private void processDate(LfBzAgentReportRequest req) {
        if (req.getDateEnd() != null) {
            Calendar g = Calendar.getInstance();
            g.setTime(req.getDateEnd());
            g.add(Calendar.DATE, 1);
            Date d2 = g.getTime();
            req.setDateEnd(d2);
        }
    }

}
