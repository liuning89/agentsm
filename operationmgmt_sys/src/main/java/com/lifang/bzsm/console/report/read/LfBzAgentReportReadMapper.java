package com.lifang.bzsm.console.report.read;

import java.util.List;

import com.lifang.bzsm.console.entity.LfBzAgentReport;
import com.lifang.bzsm.console.model.LfBzAgentReportRequest;

public interface LfBzAgentReportReadMapper
{
    LfBzAgentReport selectByPrimaryKey(Long id);

    /***
     * 
     * @Title: getBzAgentReportList
     * @Description: 获取报表分页数据
     * @param @param map
     * @param @return 设定文件
     * @return List<LfBzAgentReport> 返回类型
     * @throws
     */
    List<LfBzAgentReport> getBzAgentReportList(LfBzAgentReportRequest req);

    /***
     * 
     * @Title: getBzAgentReportList
     * @Description: 获取报表分页数量
     * @param @param map
     * @param @return 设定文件
     * @return List<LfBzAgentReport> 返回类型
     * @throws
     */
    int getBzAgentReportListCount(LfBzAgentReportRequest req);
}