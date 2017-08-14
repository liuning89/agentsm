package com.lifang.agentsm.dao.write;

import com.lifang.agentsm.model.ReportModel;

public interface AgentReportWriteMapper {
	
	void insert(ReportModel report);
	
	void insertStore(ReportModel report);
	
	void insertStoreMonth(ReportModel report);
	
	void updateAgentReport(ReportModel report);
	
	void updateStoreMonthReport(ReportModel report);
	
	void updateStoreReport(ReportModel report);
}
