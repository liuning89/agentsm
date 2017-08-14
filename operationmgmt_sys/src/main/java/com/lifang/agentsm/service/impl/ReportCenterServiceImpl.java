package com.lifang.agentsm.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.read.AgentReportReadMapper;
import com.lifang.agentsm.dao.read.ConfigReadMapper;
import com.lifang.agentsm.dao.write.AgentReportWriteMapper;
import com.lifang.agentsm.entity.Config;
import com.lifang.agentsm.model.AgentReportModel;
import com.lifang.agentsm.model.LfAgentReportRequest;
import com.lifang.agentsm.model.ReportModel;
import com.lifang.agentsm.service.ConfigService;
import com.lifang.agentsm.service.ReportCenterService;
import com.lifang.agentsm.task.DateUtils;
import com.lifang.agentsm.task.ReportTaskUtil;

@Service
public class ReportCenterServiceImpl implements ReportCenterService {

	@Autowired
	private AgentReportReadMapper reportReadMapper;
	
	@Autowired
	private AgentReportWriteMapper reportWriteMapper;
	
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	private ReportTaskUtil reportUtil = new ReportTaskUtil();
	
	/**
	 * 统计经纪人业绩数据
	 * @return
	 */
	private List<ReportModel> countAgentReport(){
		map.put("timeFrom", reportUtil.getTimeFrom());
		map.put("timeTo", reportUtil.getTimeTo());
		return calculateAgentReport();
	}

	/**
	 * 统计经纪人业绩数据
	 * @return
	 */
	private List<ReportModel> countAgentReport(LfAgentReportRequest agentReportRequest){
		map.put("timeFrom", agentReportRequest.getCreateTimestart());
		map.put("timeTo", agentReportRequest.getCreateTimeend());
		return calculateAgentReport();
	}
	
	/**
	 * 计算经济人个人业绩数据
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     zengqing:   2015年7月6日      新建
	 * </pre>
	 *
	 * @param params
	 * @return
	 */
	private List<ReportModel> calculateAgentReport() {
		map.put("status", null);
		//获取所有的经纪人
		List<AgentReportModel> agents = reportReadMapper.getAgents();
		//获取当天所有的房源业绩经纪人
		Map<String, List<AgentReportModel>> mapList = this.getAgentOperationReport();
		//获取当天所有录入的客源信息
		List<AgentReportModel> addAgents = reportReadMapper.getAddCustomers(map);
		//获取当天所有的联系房东信息
		List<AgentReportModel> callHouses = reportReadMapper.getCallHouses(map);
		//获取当天所有的分享房源次数信息
		List<AgentReportModel> shareHouses = reportReadMapper.getShareHouses(map);
		//获取当天所有的分享房源套数
		List<AgentReportModel> shareHouseNum = reportReadMapper.getDistinctHouses(map);
		//获取当天所有的房源带看信息
		List<AgentReportModel> houseSees = reportReadMapper.getHouseSees(map);
		
		List<ReportModel> reports = new ArrayList<ReportModel>();
		for (AgentReportModel a : agents) {
			ReportModel report = new ReportModel();
			long agentId=a.getId();
			report.setAgentId(agentId);
			report.setStoreId(a.getStoreId());
			//计算房源业绩经纪人数据
			reportUtil.countOperationReport(agentId, report, mapList);
			//计算客源录入数据
			reportUtil.calculate(agentId, report, addAgents, 1);
			//计算联系房东数据
			reportUtil.calculate(agentId, report, callHouses, 2);
			//计算分享房源次数数据
			reportUtil.calculate(agentId, report, shareHouses, 3);
			//计算分享房源次数数据
			reportUtil.calculate(agentId, report, shareHouseNum, 5);
			//计算房源带看数据
			reportUtil.calculate(agentId, report, houseSees, 4);
			
			
			map.put("agentId",agentId);
			Long id = reportReadMapper.checkAgentReport(map);
			if(id!=null){
				report.setId(id);
				reportWriteMapper.updateAgentReport(report);
			}
			else
				reportWriteMapper.insert(report);
			reports.add(report);
		}
		return reports;
	}
	
	@Override
	public void countReport() {
		this.countStoreReport(countAgentReport());
	}
	
    public void countReport(LfAgentReportRequest agentReportRequest) {
        this.countStoreReport(countAgentReport(agentReportRequest));
    }

	/**
	 * 统计门店业绩数据
	 * @param agentReports
	 */
	private void countStoreReport(List<ReportModel> agentReports){
		//获取所有的门店
		List<AgentReportModel> stores = reportReadMapper.getStores();
		
		for (AgentReportModel a : stores) {
			ReportModel report = new ReportModel();
			long storeId=a.getStoreId();
			report.setStoreId(storeId);
			reportUtil.sumAgentReport(report, agentReports, storeId);
			
			map.put("storeId", storeId);
			Long id= reportReadMapper.checkStoreReport(map);
			
			if(id!=null){
				report.setId(id);
				reportWriteMapper.updateStoreReport(report);
			}else
				reportWriteMapper.insertStore(report);
		}
	}
	
	

	/**
	 * 获得所有的房源业绩归属人数据
	 * @param report
	 */
	private Map<String, List<AgentReportModel>> getAgentOperationReport(){
		Map<String, List<AgentReportModel>> mapList = new HashMap<String, List<AgentReportModel>>();
		
		
		
		//添加当天所有的发布房源数据
		map.put("type", 1);
		List<AgentReportModel> operations = reportReadMapper.getAgentOperationReport(map);
		mapList.put("push", operations);
		
		//添加当天所有的上传图片套数
		map.put("type", 2);
		operations=reportReadMapper.getAgentOperationReport(map);
		mapList.put("pic", operations);
		
		//获取当天所有的委托
		map.put("type", 3);
		operations=reportReadMapper.getAgentOperationReport(map);
		mapList.put("comm", operations);
		
		//获取当天所有的钥匙
		map.put("type", 4);
		operations=reportReadMapper.getAgentOperationReport(map);
		mapList.put("key", operations);
		
		return mapList;
	}
	
	
	
	@Override
	public void countStoreMonthReport() {
		map.put("monthFrom", reportUtil.getCurrentMonth());
		map.put("monthTo", reportUtil.getNextMonth());
		calculateStoreMonthReport();
	}

	/**
	 * 计算门店月报表
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     zengqing:   2015年7月6日      新建
	 * </pre>
	 *
	 * @param params
	 * @return
	 */
	private void calculateStoreMonthReport() {
		//添加当天所有的发布房源数据
		map.put("type", 1);
		map.put("status", 1);
		List<AgentReportModel> operations = reportReadMapper.getAgentOperationReport(map);
		List<AgentReportModel> pics = reportReadMapper.getHouseImages(map);
		List<AgentReportModel> shares = reportReadMapper.getHouseMonthShares(map);
		//获取所有的门店
		List<AgentReportModel> stores = reportReadMapper.getStores();
		
		for (AgentReportModel store : stores) {
			ReportModel report = new ReportModel();
			long storeId = store.getStoreId();
			reportUtil.calculateStoreMonth(storeId, operations, report, 1);
			reportUtil.calculateStoreMonth(storeId, pics, report, 2);
			reportUtil.calculateStoreMonth(storeId, shares, report, 3);
			
			report.setStoreId(storeId);
			map.put("storeId",storeId);
			Long id = reportReadMapper.checkStoreMonthReport(map);
			if(id!=null){
				report.setId(id);
				reportWriteMapper.updateStoreMonthReport(report);
			}
			else
				reportWriteMapper.insertStoreMonth(report);
		}
	}
	
	

	@Override
	public void countStoreMonthReport(LfAgentReportRequest agentReportRequest) {
		map.put("monthFrom", agentReportRequest.getCreateTimestart());
		map.put("monthTo", agentReportRequest.getCreateTimeend());
		calculateStoreMonthReport();
	}
}
