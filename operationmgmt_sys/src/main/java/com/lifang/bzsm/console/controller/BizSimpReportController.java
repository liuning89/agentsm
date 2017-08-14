package com.lifang.bzsm.console.controller;

import com.leo.common.util.DateUtil;
import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.utils.OrgCodeUtil;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.PositionInfo;
import com.lifang.agentsoa.model.enums.SystemInfo;
import com.lifang.bzsm.console.entity.BusinessReport;
import com.lifang.bzsm.console.entity.Employee;
import com.lifang.bzsm.console.model.BizReportRequest;
import com.lifang.bzsm.console.model.LfBzAgentReportRequest;
import com.lifang.bzsm.console.service.BizReportService;
import com.lifang.bzsm.console.service.BZLfAreaOrgService;
import com.lifang.bzsm.console.utils.ExcelUtil;
import com.lifang.model.PageResponse;
import com.lifang.sso.SsoClientUtil;
import com.lifang.sso.entity.SsoUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by admin on 2016/1/7.
 */
@Controller
@RequestMapping("/bizsimpreport")
public class BizSimpReportController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(BizSimpReportController.class);

    @Autowired
    private BizReportService bizReportService;

    @Autowired
    private BZLfAreaOrgService lfAreaOrgService;

    @Autowired
    private AgentSOAServer agentSOAServer;

    @Autowired
    private SsoClientUtil ssoClientUtil;

    //跳转到经纪人接单出价情况表
    @RequestMapping(value = "/agentbzsimpreport.action")
    public String getAgentBzSimpReport() {
        return "bzreport/agentBzSimpReport";
    }

    //跳转到门店接单出价情况表
    @RequestMapping(value = "/areabzsimpreport.action")
    public String getAreaBzSimpReport() {
        return "bzreport/areaBzSimpReport";
    }

    //跳转区域接单出价情况表
    @RequestMapping(value = "/storebzsimpreport.action")
    public String getStoreBzSimpReport() {
        return "bzreport/storeBzSimpReport";
    }


    @RequestMapping(value = "/getAgentBizSimpReport.action")
    @ResponseBody
    public Object getAgentBizSimpReport(BizReportRequest req, HttpServletRequest request) {
        LfEmployee emp = getLoginEmployeeInfo(request.getSession());
        String json = request.getParameter("json");
        if (null != json) {
            int pageIndex = req.getPageIndex();
            int pageSize = req.getPageSize();
            String dateEnd = req.getDateEnd();
            String dateStart = req.getDateStart();
            String sortField = req.getSortField();
            String sortOrder = req.getSortOrder();
            req = (BizReportRequest) JSONObject.toBean(JSONObject.fromObject(json), BizReportRequest.class);
            req.setDateEnd(dateEnd);
            req.setDateStart(dateStart);
            req.setPageIndex(pageIndex);
            req.setPageSize(pageSize);
            req.setSortField(sortField);
            req.setSortOrder(sortOrder);
        }

        if (OrgCodeUtil.isAgent(agentSOAServer, emp)) {
            req.setAgentId(emp.getId());
        }
        else {
            List<Integer> storeList = calcStoreList(req, emp);
            req.setStoreIds(storeList);
        }

        return bizReportService.getAgentBizSimpReport(req);
    }

    @RequestMapping(value = "/exportAgentBizSimpReport.action", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object exportAgentBizSimpReport(HttpServletRequest request, HttpServletResponse response, BizReportRequest req) throws IOException {

        String data = request.getParameter("data");
        String title = java.net.URLDecoder.decode(request.getParameter("title"), "UTF-8");
        if (null != data) {
            String dateEnd = req.getDateEnd();
            String dateStart = req.getDateStart();
            req = (BizReportRequest) JSONObject.toBean(JSONObject.fromObject(java.net.URLDecoder.decode(data, "UTF-8")), BizReportRequest.class);
            req.setDateEnd(dateEnd);
            req.setDateStart(dateStart);
        }
        String fileName = "";
        if (1 == req.getQueryType()) {
            fileName = req.getTitle() + "汇总表";
        } else {
            fileName = req.getTitle() + "明细表";
        }
        String keys[] = null;
        String colums[] = null;
        BizReportRequest obj = null;
        if (null != title) {
            JSONArray heads = JSONArray.fromObject(title);
            keys = new String[heads.size() - req.getQueryType()];
            colums = new String[heads.size() - req.getQueryType()];
            for (int i = 0; i < heads.size(); i++) {
                obj = (BizReportRequest) JSONObject.toBean(heads.getJSONObject(i), BizReportRequest.class);
                if (1 == req.getQueryType() && "statisticsDate".equalsIgnoreCase(obj.getField())) {
                    heads.remove(i);
                    i--;
                } else {
                    colums[i] = obj.getHeader().replace("\n", "").trim();
                    keys[i] = obj.getField().replace("\n", "").trim();
                }
            }

        }

        LfEmployee emp = getLoginEmployeeInfo(request.getSession());
        if (OrgCodeUtil.isAgent(agentSOAServer, emp)) {
            req.setAgentId(emp.getId());
        }
        else {
            List<Integer> storeList = calcStoreList(req, emp);
            req.setStoreIds(storeList);
        }

        List<Map<String, Object>> list = bizReportService.getExportAgentBizSimpReport(req);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(list, keys, colums).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            out = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }


    @RequestMapping(value = "/getAreaBizSimpReport.action")
    @ResponseBody
    public Object getAreaBizSimpReport(BizReportRequest req, HttpServletRequest request) {
        LfEmployee emp = getLoginEmployeeInfo(request.getSession());
        String json = request.getParameter("json");
        if (null != json) {
            int pageIndex = req.getPageIndex();
            int pageSize = req.getPageSize();
            String dateEnd = req.getDateEnd();
            String dateStart = req.getDateStart();
            String sortField = req.getSortField();
            String sortOrder = req.getSortOrder();
            req = (BizReportRequest) JSONObject.toBean(JSONObject.fromObject(json), BizReportRequest.class);
            req.setDateEnd(dateEnd);
            req.setDateStart(dateStart);
            req.setPageIndex(pageIndex);
            req.setPageSize(pageSize);
            req.setSortField(sortField);
            req.setSortOrder(sortOrder);
        }
        if (OrgCodeUtil.isAgent(agentSOAServer, emp)) {
            req.setAgentId(emp.getId());
        }
        else {
            List<Integer> areaList = calcAreaList(req, emp);
            req.setAreaIds(areaList);
        }
        return bizReportService.getAreaBizSimpReport(req);
    }


    @RequestMapping(value = "/exportAreaBizSimpReport.action", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object getExportAreaBizSimpReport(HttpServletRequest request, HttpServletResponse response, BizReportRequest req) throws IOException {
        String data = request.getParameter("data");
        String title = java.net.URLDecoder.decode(request.getParameter("title"), "UTF-8");
        if (null != data) {
            String dateEnd = req.getDateEnd();
            String dateStart = req.getDateStart();
            req = (BizReportRequest) JSONObject.toBean(JSONObject.fromObject(java.net.URLDecoder.decode(data, "UTF-8")), BizReportRequest.class);
            req.setDateEnd(dateEnd);
            req.setDateStart(dateStart);
        }
        String fileName = "";
        if (1 == req.getQueryType()) {
            fileName = req.getTitle() + "汇总表";
        } else {
            fileName = req.getTitle() + "明细表";
        }
        String keys[] = null;
        String colums[] = null;
        BizReportRequest obj = null;
        if (null != title) {
            JSONArray heads = JSONArray.fromObject(title);
            keys = new String[heads.size() - req.getQueryType()];
            colums = new String[heads.size() - req.getQueryType()];
            for (int i = 0; i < heads.size(); i++) {
                obj = (BizReportRequest) JSONObject.toBean(heads.getJSONObject(i), BizReportRequest.class);
                if (1 == req.getQueryType() && "statisticsDate".equalsIgnoreCase(obj.getField())) {
                    heads.remove(i);
                    i--;
                } else {
                    colums[i] = obj.getHeader().replace("\n", "").trim();
                    keys[i] = obj.getField().replace("\n", "").trim();
                }
            }

        }
        LfEmployee emp = getLoginEmployeeInfo(request.getSession());
        List<Integer> areaList = calcAreaList(req, emp);
        req.setAreaIds(areaList);
        List<Map<String, Object>> list = bizReportService.getExportAreaBizSimpReport(req);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(list, keys, colums).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            out = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }

    @RequestMapping(value = "/getStoreBizSimpReport.action")
    @ResponseBody
    public Object getStoreBizSimpReport(BizReportRequest req, HttpServletRequest request) {
        String json = request.getParameter("json");
        if (null != json) {
            int pageIndex = req.getPageIndex();
            int pageSize = req.getPageSize();
            String dateEnd = req.getDateEnd();
            String dateStart = req.getDateStart();
            String sortField = req.getSortField();
            String sortOrder = req.getSortOrder();
            req = (BizReportRequest) JSONObject.toBean(JSONObject.fromObject(json), BizReportRequest.class);
            req.setDateEnd(dateEnd);
            req.setDateStart(dateStart);
            req.setPageIndex(pageIndex);
            req.setPageSize(pageSize);
            req.setSortField(sortField);
            req.setSortOrder(sortOrder);
        }

        LfEmployee emp = getLoginEmployeeInfo(request.getSession());
        List<Integer> storeList = calcStoreList(req, emp);
        req.setStoreIds(storeList);

        return bizReportService.getStoreBizSimpReport(req);
    }

    @RequestMapping(value = "/exportStoreBizSimpReport.action", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object getExportStoreBizSimpReport(HttpServletRequest request, HttpServletResponse response, BizReportRequest req) throws IOException {
        String data = request.getParameter("data");
        String title = java.net.URLDecoder.decode(request.getParameter("title"), "UTF-8");
        if (null != data) {
            String dateEnd = req.getDateEnd();
            String dateStart = req.getDateStart();
            req = (BizReportRequest) JSONObject.toBean(JSONObject.fromObject(java.net.URLDecoder.decode(data, "UTF-8")), BizReportRequest.class);
            req.setDateEnd(dateEnd);
            req.setDateStart(dateStart);
        }
        String fileName = "";
        if (1 == req.getQueryType()) {
            fileName = req.getTitle() + "汇总表";
        } else {
            fileName = req.getTitle() + "明细表";
        }
        String keys[] = null;
        String colums[] = null;
        BizReportRequest obj = null;
        if (null != title) {
            JSONArray heads = JSONArray.fromObject(title);
            keys = new String[heads.size() - req.getQueryType()];
            colums = new String[heads.size() - req.getQueryType()];
            for (int i = 0; i < heads.size(); i++) {
                obj = (BizReportRequest) JSONObject.toBean(heads.getJSONObject(i), BizReportRequest.class);
                if (1 == req.getQueryType() && "statisticsDate".equalsIgnoreCase(obj.getField())) {
                    heads.remove(i);
                    i--;
                } else {
                    colums[i] = obj.getHeader().replace("\n", "").trim();
                    keys[i] = obj.getField().replace("\n", "").trim();
                }
            }

        }

        LfEmployee emp = getLoginEmployeeInfo(request.getSession());
        List<Integer> storeList = calcStoreList(req, emp);
        req.setStoreIds(storeList);

        List<Map<String, Object>> list = bizReportService.getExportStoreBizSimpReport(req);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(list, keys, colums).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            out = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }



    private List<Integer> calcStoreList(BizReportRequest req, LfEmployee emp)
    {
        List<Integer> storeList = new ArrayList();
        if(req.getStoreId() != null && req.getStoreId() > 0)
        {
            storeList.add(req.getStoreId());
        }
        else
        {
            storeList = OrgCodeUtil.getStoreListByEmployeeId(req.getCityId(), req.getAreaId(), agentSOAServer, emp);
        }

        if(storeList != null && storeList.size() > 0)
        {
            return storeList;
        }

        return null;
    }


    private List<Integer> calcAreaList(BizReportRequest req, LfEmployee emp)
    {
        List<Integer> areaList = new ArrayList();
        if(req.getAreaId() != null && req.getAreaId() > 0)
        {
            areaList.add(req.getAreaId());
        }
        else
        {
            areaList = OrgCodeUtil.getAreadListByEmployeeId(req.getCityId(), agentSOAServer, emp);
        }

        if(areaList != null && areaList.size() > 0)
        {
            return areaList;
        }

        return null;
    }


//    private String getFinalCode() {
//        //权限 code
//        int finalOrgId = 0;
//        String finalOrgCold = "";
//        if (req.getCityId() != 0) {
//            finalOrgId = req.getCityId();
//        }
//        if (req.getAreaId()  != 0) {
//            finalOrgId = req.getAreaId();
//        }
//        if (req.getStoreId() != 0) {
//            finalOrgId = req.getStoreId();
//        }
//        finalOrgCold = lfAreaOrgService.selectByPK(finalOrgId).getCode();
//        SsoUser ssoUser = null;
//        try {
//            ssoUser = ssoClientUtil.getCurrentUser();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        com.lifang.agentsoa.model.Employee agent = agentSOAServer.getEmployee(ssoUser.getId(), SystemInfo.BusinessManagementSystem);
//        String currentUserCode = agent.getCodes().get(0);
//        // 如果登陆员工的code长于参数的code那么用登陆员工code代替页面参数code
//        if (finalOrgCold == null || currentUserCode.length() > finalOrgCold.length()) {
//            return currentUserCode;
//        } else {
//            return finalOrgCold;
//        }
//    }


}
