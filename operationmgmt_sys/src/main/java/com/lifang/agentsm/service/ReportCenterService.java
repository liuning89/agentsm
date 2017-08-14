package com.lifang.agentsm.service;

import java.util.Date;

import com.lifang.agentsm.model.LfAgentReportRequest;

public interface ReportCenterService {
	void countReport();
	
	void countReport(LfAgentReportRequest agentReportRequest);
	
	void countStoreMonthReport();

	void countStoreMonthReport(LfAgentReportRequest agentReportRequest);
}
