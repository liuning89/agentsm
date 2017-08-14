package com.lifang.bzsm.console.report.read;

import java.util.List;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.model.WkcoinReport;

public interface WkCoinReportReadMapper {

    List<WkcoinReport> getWkCoinReportList(WkcoinReport req);

    int getWkCoinReportListTotal(WkcoinReport req);

}
