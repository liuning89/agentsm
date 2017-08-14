package com.lifang.agentsm.task;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.lifang.agentsm.model.AgentReportModel;
import com.lifang.agentsm.model.ReportModel;

/**
 * 数据中心统计任务计算工具类
 * @author zengqing
 *
 */
public class ReportTaskUtil {
	/**
	 * 计算门店月报表方法
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     zengqing:   2015年7月6日      新建
	 * </pre>
	 *
	 * @param params
	 * @return
	 */
	public void calculateStoreMonth(long storeId,List<AgentReportModel> datas,ReportModel report,int type){
		int count=0;
		for (AgentReportModel d : datas) {
			if(d==null)
				continue;
			if(d.getStoreId()==storeId)
				count++;
			
		}
		if(type==1)
			report.setPublishManNum(count);
		else if(type==2)
			report.setUploadPicManNum(count);
		else
			report.setShareHouseNum(count);
	}
	
	/**
	 * 计算房源业绩归属人数据
	 * @param operations
	 * @param agentId
	 * @return
	 */
	public int countOperation(List<AgentReportModel> operations,long agentId){
		int count=0;
		for (AgentReportModel a : operations) {
			if(a==null)
				continue;
			if(a.getOperator()==agentId)
					count++;
		}
		return count;
	}
	
	/**
	 * 计算数据
	 * @param agentId
	 * @param report
	 * @param list
	 * @param type
	 */
	public void calculate(long agentId,ReportModel report,List<AgentReportModel> list,int type){
		int count=0;
		for (AgentReportModel a : list) {
			if(a.getAgentId()==agentId)
				count++;
		}
		if(type==1)
			report.setAddMasterNum(count);
		if(type==2)
			report.setContactLandladyCount(count);
		if(type==3){
			report.setForwardHouseCount(count);
		}
		if(type==4)
			report.setLeadMasterNum(count);
		if(type==5)
			report.setShareHouseNum(count);
	}
	
	/**
	 * 累加经纪人报表
	 */
	public void sumAgentReport(ReportModel report,List<ReportModel> agentReports,long storeId){
		int publishManNum=0;
		int uploadPicManNum=0;
		for (ReportModel r : agentReports) {
			if(r.getStoreId()==storeId)
				report.sumReport(r);
			if(r.getPublishNum()>0)
				publishManNum++;
			if(r.getUploadPicNum()>0)
				uploadPicManNum++;
		}
		report.setPublishManNum(publishManNum);
		report.setUploadPicManNum(uploadPicManNum);
	}
	
	
	/**
	 * 统计经纪人的房源
	 * @param agentId
	 * @param report
	 * @param mapList
	 */
	public void countOperationReport(long agentId,ReportModel report,Map<String, List<AgentReportModel>> mapList){
		int publishNum=0; //'发布房源数',
		int uploadPicNum=0; //'上传图片套数',
		int keyNum=0; //'钥匙数',
		int soleEntrustNum=0; //'独家委托数',
		
		List<AgentReportModel> operations = mapList.get("push");
		publishNum=this.countOperation(operations, agentId);
		
		operations = mapList.get("pic");
		uploadPicNum=this.countOperation(operations, agentId);
		
		operations = mapList.get("comm");
		soleEntrustNum=this.countOperation(operations, agentId);
		
		operations = mapList.get("key");
		keyNum=this.countOperation(operations, agentId);
		
		report.setPublishNum(publishNum);
		report.setUploadPicNum(uploadPicNum);
		report.setKeyNum(keyNum);
		report.setEntrustNum(soleEntrustNum);
	}
	
	public Date getCurrentMonth(){
		Calendar ca = new GregorianCalendar(); 
		Date someDate=DateUtils.parseDate(DateUtils.formatDate(new Date(), "yyyy/MM/dd"), "yyyy/MM/dd");
		ca.setTime(someDate);   //  someDate 为你要获取的那个月的时间
		ca.set(Calendar.DAY_OF_MONTH,   1);
		Date  firstDate  =  ca.getTime();
		return firstDate;
	}
	
	public Date getNextMonth() {
		Calendar ca = new GregorianCalendar(); 
		Date someDate=DateUtils.parseDate(DateUtils.formatDate(new Date(), "yyyy/MM/dd"), "yyyy/MM/dd");
		ca.setTime(someDate);   //  someDate 为你要获取的那个月的时间
		ca.add(Calendar.MONTH,   1);
		ca.set(Calendar.DAY_OF_MONTH,   1);
		Date  firstDate  =  ca.getTime();
		return firstDate;
	}
	
	public Date getTimeFrom() {
		
		Date time_from=DateUtils.parseDate(DateUtils.formatDate(new Date(), "yyyy/MM/dd"), "yyyy/MM/dd");
		Calendar calendar = new GregorianCalendar(); 
	    calendar.setTime(time_from); 
	    calendar.add(calendar.DATE,0);//把日期往后增加一天.整数往后推,负数往前移动
	    time_from=calendar.getTime(); 
		return time_from;
	}
	public Date getTimeTo() {
		Date time_to=DateUtils.parseDate(DateUtils.formatDate(new Date(), "yyyy/MM/dd"), "yyyy/MM/dd");
		Calendar calendar = new GregorianCalendar(); 
	    calendar.setTime(time_to); 
	    calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
	    time_to=calendar.getTime(); 
		return time_to;
	}
}
