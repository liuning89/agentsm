package com.lifang.bzsm.console.report.read;

import com.lifang.bzsm.console.entity.BusinessReport;
import com.lifang.bzsm.console.model.LfBzAgentReportRequest;

import java.util.List;

/**
 * Created by admin on 2015/9/25.
 */
public interface StoreBusinessReportReadMapper {

    //查询总条数
    int getStoreBizReportTotal(LfBzAgentReportRequest req);
    /**
     * 功能描述:门店业务汇总表
     */
    List<BusinessReport> getStoreBizReport(LfBzAgentReportRequest req);

}
