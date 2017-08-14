package com.lifang.agentsm.dao.read;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.model.AgentReportModel;
import com.lifang.agentsm.model.LfAgentReportRequest;
import com.lifang.agentsm.model.ReportModel;

public interface AgentReportReadMapper {
	
	/**
	 * 获取当天所有的经纪人业务信息
	 * @return
	 */
	List<AgentReportModel> getAgentOperationReport(Map<String, Object> map);
	
	/**
	 * 获取当天所有的经纪人跟进信息
	 * @return
	 */
	List<AgentReportModel> getAddCustomers(Map<String, Object> map);
	
	/**
	 * 获取当天所有的经纪人联系房东信息
	 * @return
	 */
	List<AgentReportModel> getCallHouses(Map<String, Object> map);
	/**
	 * 获取所有的经纪人
	 * @return
	 */
	List<AgentReportModel> getAgents();
	
	/**
	 * 获取所有的门店
	 * @return
	 */
	List<AgentReportModel> getStores();
	
	/**
	 * 获取当天所有的经纪人分享房源次数
	 * @return
	 */
	List<AgentReportModel> getShareHouses(Map<String, Object> map);
	
	
	/**
	 * 获取当天所有的经纪人分享房源套数
	 * @return
	 */
	List<AgentReportModel> getDistinctHouses(Map<String, Object> map);
	
	/**
	 * 获取当天所有的经纪人房源带看信息
	 * @return
	 */
	List<AgentReportModel> getHouseSees(Map<String, Object> map);
	
	/**
	 * 获取当天所有的经纪人上传图片数据
	 * @return
	 */
	List<AgentReportModel> getHouseImages(Map<String, Object> map);
	
	
	/**
	 * 获取当月所有分享房源的套数数据
	 * @return
	 */
	List<AgentReportModel> getHouseMonthShares(Map<String, Object> map);
	
	/**
	 * 检查是否当天已新增经纪人报表记录
	 * @param map
	 * @return
	 */
	Long checkAgentReport(Map<String, Object> map);
	
	/**
	 * 检查是否当天已新增门店报表记录
	 * @param map
	 * @return
	 */
	Long checkStoreReport(Map<String, Object> map);
	
	
	/**
	 * 检查是否当月已新增门店月报表记录
	 * @param map
	 * @return
	 */
	Long checkStoreMonthReport(Map<String, Object> map);
	
	 /**
	 * 功能描述: 获取经纪人报表列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     wangbiao:   2015年5月30日      新建
	 * </pre>
	 *
	 * @param lfAgentReportRequest
	 * @return
	 */
	List<ReportModel> getAgentReportList(Pagination pagination);
	
	List<ReportModel> getStoreReportList(Pagination pagination);
	
	/**
	 * 
	* @Title: getStoreReportList 
	* @Description: 门店月统计报表
	* @param @param pagination
	* @param @return    设定文件 
	* @return List<ReportModel>    返回类型 
	* @throws
	 */
	List<ReportModel> getStoreMonReportList(Pagination pagination);
	
}
