package com.lifang.agentsm.task;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lifang.agentsm.controller.AgentController;
import com.lifang.agentsm.service.ReportCenterService;

@Component
public class ReportTask {
	
	protected Logger logger = LoggerFactory.getLogger(AgentController.class);
	@Autowired
	private ReportCenterService reportService;
	
//	@Value("${hostName}")
//	private String hostName;
	
	@Scheduled(cron="${reportTaskCorn}")
	public void executeTask(){
		try {
				logger.info("数据中心统计开始");
				logger.info("当前主机："+getHostName());
				//reportService.countReport();
				logger.info("数据中心统计结束");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	private boolean checkHost(){
//		return hostName.equals(getHostName());
//	}
	
	private String getHostName(){
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Scheduled(cron="${reportMonthTaskCorn}")
	public void executeMonthTask(){
		try {
				logger.info("数据中心月统计开始");
				logger.info("当前主机："+getHostName());
				//reportService.countStoreMonthReport();
				logger.info("数据中心月统计结束");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
