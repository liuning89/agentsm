package com.lifang.bzsm.console.controller;

import com.leo.common.util.DateUtil;
import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.utils.OrgCodeUtil;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.enums.SystemInfo;
import com.lifang.bzsm.console.entity.Employee;
import com.lifang.bzsm.console.model.LfBzAgentReportInfo;
import com.lifang.bzsm.console.model.LfBzAgentReportRequest;
import com.lifang.bzsm.console.service.BZAreaReportService;
import com.lifang.bzsm.console.service.BZLfAreaOrgService;
import com.lifang.bzsm.console.utils.ExcelUtil;
import com.lifang.model.PageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/areaexportExcle")
public class AreaExportExcle extends BaseController {

    @Autowired
    private BZAreaReportService bzReportService;

    @Autowired
    private BZLfAreaOrgService lfAreaOrgService;

    @Autowired
    private AgentSOAServer agentSOAServer;

    @RequestMapping(value = "download_excel.action")
    public String download(HttpServletRequest request, HttpServletResponse response, LfBzAgentReportRequest req) throws IOException {
        String fileName = "";
        if ("1".equals(req.getQueryType())) {
            fileName="区域业务汇总表";
        } else {
            fileName="区域业务明细表";
        }
        // 填充projects数据
        List<LfBzAgentReportInfo> projects = createData(req, request.getSession());
        List<Map<String, Object>> list = createExcelRecord(projects, req);
        String columnNames[] = {"日期", "区域", "行程量", "平均行程量" ,"带盘", "带客", "悟空找房", "网络", "其它", "新增总数", "发布", "钥匙", "速销", "实景","无效审核数量", "房源"
                , "客户", "营销指数", "分享房源次数", "分享房源套数", "好评", "差评"};
        String keys[] = {"日期", "区域", "行程量", "平均行程量", "带盘", "带客", "悟空找房", "网络", "其它", "新增总数", "发布", "钥匙", "速销", "实景","无效审核数量", "房源"
                , "客户", "营销指数", "分享房源次数", "分享房源套数", "好评", "差评"};

        String columnNamessum[] = {"区域", "行程量", "平均行程量", "带盘", "带客", "悟空找房", "网络", "其它", "新增总数", "发布", "钥匙", "速销", "实景","无效审核数量", "房源"
                , "客户", "营销指数", "分享房源次数", "分享房源套数", "好评", "差评"};
        String keyssum[] = {"区域", "行程量", "平均行程量", "带盘", "带客", "悟空找房", "网络", "其它", "新增总数", "发布", "钥匙", "速销", "实景", "无效审核数量","房源"
                , "客户", "营销指数", "分享房源次数", "分享房源套数", "好评", "差评"};
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            if ("1".equals(req.getQueryType())) {
                ExcelUtil.createWorkBook(list, keyssum, columnNamessum).write(os);
            } else {
                ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }

    private List<LfBzAgentReportInfo> createData(LfBzAgentReportRequest req, HttpSession session) {
        PageResponse response = new PageResponse();

        // 计算出当前员工的组织结构的code
        LfEmployee emp = getLoginEmployeeInfo(session);

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
            req.setAreaIds(areaList);
        }

        //处理时间
        processDate(req);
        req.setPageIndex(0);
        req.setPageSize(10000);
        response = bzReportService.getAreaReportList(req);

        return response.getData() == null ? null : (List<LfBzAgentReportInfo>) response.getData();
    }

    private List<Map<String, Object>> createExcelRecord(List<LfBzAgentReportInfo> projects, LfBzAgentReportRequest req) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        LfBzAgentReportInfo project = null;
        for (int j = 0; j < projects.size(); j++) {
            //{"日期", "星期", "名称", "手机", "签约成功数", "签约失败数", "意向客户数", "一看", "二看", "总数", "悟空找房", "网络", "其它", "发布", "钥匙", "独家", "实景", "房源", "客户", "未跟进客户数", "营销指数", "分享房源次数", "分享房源套数", " 注册数", "好评", "差评"}
            project = projects.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            if ("2".equals(req.getQueryType())) {
                mapValue.put("日期", DateUtil.getYYYYMMDDFormatDate(project.getStatisticsDate()));
            }
            mapValue.put("区域", project.getAreaOrgName());
            mapValue.put("派单", project.getOrderAllocation());
            mapValue.put("抢单", project.getOrderGrab());
            mapValue.put("总单", project.getOrderTotal());
            mapValue.put("确认出价", project.getOrderSuccess());
            mapValue.put("撤销出价", project.getOrderFailed());
            mapValue.put("调价", project.getOrderEdit());
            mapValue.put("出价", project.getOrderCreate());
            mapValue.put("一看", project.getHouseSeeFirst());
            mapValue.put("二看", project.getHouseSeeSecond());
            mapValue.put("悟空找房", project.getNewCustomerWK());
            mapValue.put("网络", project.getNewCustomerNet());
            mapValue.put("其它", project.getNewCustomerOther());
            mapValue.put("新增总数", project.getNewCustomerWK()+ project.getNewCustomerNet()+project.getNewCustomerOther());
            mapValue.put("发布", project.getHousePublish());
            mapValue.put("钥匙", project.getHouseKey());
            mapValue.put("速销", project.getHouseEntrust());
            mapValue.put("实景", project.getHouseImage());
            mapValue.put("维护", project.getHouseMaintain());
            mapValue.put("房源", project.getFollowHouse());
            mapValue.put("客户", project.getFollowCustomer());
            mapValue.put("无效审核数量", project.getInvalidReview());
            if("2".equals(req.getQueryType())){
                mapValue.put("未跟进客户", project.getFollowCustomerN());
            }
            mapValue.put("营销指数", project.getShareTotal());
            mapValue.put("分享房源次数", project.getShareHouseCount());
            mapValue.put("分享房源套数", project.getShareHouse());
            mapValue.put("注册数", project.getAgentRegist());
            mapValue.put("好评", project.getOpinionHight());
            mapValue.put("差评", project.getOpinionLow());
            mapValue.put("行程量", project.getAgentStroke());
            mapValue.put("带盘", project.getHouseSeeHCount());
            mapValue.put("带客", project.getHouseSeeCCount());

            double doubleStroke = project.getAgentStroke();
            double doubleDataTotal = project.getDataTotalCount();

            DecimalFormat decFormat = new DecimalFormat("0.00");
            decFormat.setRoundingMode(RoundingMode.HALF_UP);
            String strokeAVG = "0";
            if(doubleDataTotal > 0)
            {
                strokeAVG = decFormat.format(doubleStroke / doubleDataTotal);
            }
            mapValue.put("平均行程量", strokeAVG);

            listmap.add(mapValue);
        }
        return listmap;
    }

    /***
     * @param @param req    设定文件
     * @return void    返回类型
     * @throws
     * @Title: processDate
     * @Description: 处理一下结束时间
     */
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
