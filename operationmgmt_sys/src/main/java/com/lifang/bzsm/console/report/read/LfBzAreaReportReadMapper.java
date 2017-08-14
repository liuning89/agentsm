package com.lifang.bzsm.console.report.read;

import com.lifang.agentsm.model.HouseCountTown;
import com.lifang.bzsm.console.entity.LfBzAgentReport;
import com.lifang.bzsm.console.model.LfBzAgentReportInfo;
import com.lifang.bzsm.console.model.LfBzAgentReportRequest;

import java.util.List;
import java.util.Map;

public interface LfBzAreaReportReadMapper
{

    /***
     * 
     * @Title: getBzAgentReportList
     * @Description: 获取报表分页数据
     * @param @param map
     * @param @return 设定文件
     * @return List<LfBzAgentReport> 返回类型
     * @throws
     */
    List<LfBzAgentReportInfo> getBzAreaReportList(LfBzAgentReportRequest req);

    /***
     * 
     * @Title: getBzAgentReportList
     * @Description: 获取报表分页数量
     * @param @param map
     * @param @return 设定文件
     * @return List<LfBzAgentReport> 返回类型
     * @throws
     */
    int getBzAreaReportListCount(LfBzAgentReportRequest req);

}