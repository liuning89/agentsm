package com.lifang.agentsm.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.AgentWkCoinResultModel;
import com.lifang.agentsm.model.AgentWkCoinSearchModel;
import com.lifang.agentsm.service.OperatingSystemService;
import com.lifang.agentsm.utils.ExcelUtils;
import com.lifang.model.PageResponse;

/**
 * 运营管理系统
 * @author lixiong
 *
 * 2016年4月5日
 */
@Controller
@RequestMapping("/operatingSystem")
public class OperatingSystemController extends BaseController {
	@Resource
	private OperatingSystemService operatingSystemService;
	
	
    /**
     * 跳转到经纪人悟空币奖励查询页面
     * @注释:
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     zhanglong:   2016年4月5日      新建
     * </pre>
     * @return String       
     *
     */
    @RequestMapping(value= "/operationManagement/gotoAgentWKCoinRewardSearch.action")
    public String gotoAgentWKCoinRewardSearch(){
    	return "operatingSystem/agent_wk_coin_reward_search";
    }
    /**
     * 跳转页面
     * @注释:
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     zhanglong:   2016年4月7日      新建
     * </pre>
     * @return String       
     *
     */
    @RequestMapping(value = "/operationManagement/gotoAgentWKCoinSurplusSearch.action")
    public String gotoAgentWKCoinSurplusSearch(){
    	return "operatingSystem/agent_wk_coin_surplus_search";
    }
    
    @RequestMapping(value= "/operationManagement/queryAgentWKCoinRewardList.action")
    @ResponseBody
    public Object queryAgentWKCoinRewardList(AgentWkCoinSearchModel searchModel,HttpSession session){
    	if(null == searchModel.getStartTime())
    		searchModel.setStartTime("1900-01-01");
    	if(null == searchModel.getEndTime())
    		searchModel.setEndTime(getCurrentDate());
    	LfEmployee employee = getLoginEmployeeInfo(session);
    	return operatingSystemService.queryAgentWKCoinRewardList(searchModel,employee);
    }
    
    @RequestMapping(value= "/operationManagement/queryAgentWKCoinSurplusList.action")
    @ResponseBody
    public PageResponse<List<AgentWkCoinResultModel>> queryAgentWKCoinSurplusList(AgentWkCoinSearchModel searchModel,HttpSession session){
    	LfEmployee employee = getLoginEmployeeInfo(session);
    	return operatingSystemService.queryAgentWKCoinSurplusList(searchModel,employee);
    }
    
    @RequestMapping("/operationManagement/getAgentWKCoinSurplusTotal.action")
    @ResponseBody
    public PageResponse<String> getAgentWKCoinSurplusTotal(AgentWkCoinSearchModel searchModel,HttpSession session){
    	LfEmployee employee = getLoginEmployeeInfo(session);
        return operatingSystemService.getAgentWKCoinSurplusTotal(searchModel,employee);
    }
    
    @RequestMapping("/operationManagement/agentWkCoinSurplusExport.action")
    @ResponseBody
    public void agentWkCoinSurplusExport(HttpServletResponse res,AgentWkCoinSearchModel searchModel,HttpSession session){
        LfEmployee employee = getLoginEmployeeInfo(session);
        String xlsName = "经济人悟空币剩余情况报表";
        String[] arr = {"公司", "城市", "区域", "门店", "经纪人", "剩余悟空币币额"};
        List<AgentWkCoinResultModel> exportList = operatingSystemService.agentWkCoinSurplusExport(searchModel, employee);
//      List<WkCoinReportExport> list = lfYfykAmoutSetService.getWkCoinReportListNotPage(model, employee);
        try {
            ExcelUtils.export(res, xlsName, arr, exportList, AgentWkCoinResultModel.class);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
  }
    
  private String getCurrentDate(){
	  Date date = new Date();
	  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	  return simpleDateFormat.format(date);
  }
}
