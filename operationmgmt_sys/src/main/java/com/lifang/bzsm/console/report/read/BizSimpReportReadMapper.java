package com.lifang.bzsm.console.report.read;

import com.lifang.bzsm.console.entity.BusinessReport;
import com.lifang.bzsm.console.model.BizReportRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/1/7.
 */
public interface BizSimpReportReadMapper {


    /**
     * 功能描述:经纪人业务汇总表
     */
    List<BusinessReport> getAgentBizSimpReport(BizReportRequest req);
    //查询总条数
    int getAgentBizSimpReportTotal(BizReportRequest req);
    List<Map<String,Object>> getExportAgentBizSimpReport(BizReportRequest req);

    /**
     * 功能描述:门店业务汇总表
     */
    List<BusinessReport> getStoreBizSimpReport(BizReportRequest req);
    //查询总条数
    int getStoreBizSimpReportTotal(BizReportRequest req);
    List<Map<String,Object>> getExportStoreBizSimpReport(BizReportRequest req);

     /**
     * 功能描述:区域业务汇总表
     */
    List<BusinessReport> getAreaBizSimpReport(BizReportRequest req);
    //查询总条数
    int getAreaBizSimpReportTotal(BizReportRequest req);
    List<Map<String,Object>> getExportAreaBizSimpReport(BizReportRequest req);




}
