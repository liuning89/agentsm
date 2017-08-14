package com.lifang.agentsm.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.base.model.WkCoinConsumeExport;
import com.lifang.agentsm.base.model.WkCoinPayExport;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.ComboModel;
import com.lifang.agentsm.model.LfAgentFeeSet;
import com.lifang.agentsm.model.LfYfykAmoutSet;
import com.lifang.agentsm.model.LfYfykAmoutSetLog;
import com.lifang.agentsm.model.WkCoinDetailModel;
import com.lifang.agentsm.model.WkCoinGiveExport;
import com.lifang.agentsm.model.WkCoinGivelModel;
import com.lifang.agentsm.model.WkCoinReportExport;
import com.lifang.agentsm.model.WkcoinReport;
import com.lifang.agentsm.service.LfYfykAmoutSetService;
import com.lifang.agentsm.utils.ExcelUtils;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.PositionInfo;
import com.lifang.agentsoa.model.enums.SystemInfo;
import com.lifang.model.Response;
import com.lifang.sso.SsoClientUtil;

/**
 * 
 * Function: 有房有客充值金额配置
 *
 * @author   ln
 * @Date	 2016年3月14日		下午2:48:13
 *
 * @see
 */
@Controller
@RequestMapping("/amoutset")
public class LfYfykAmoutSetController extends BaseController {

    @Autowired
    private LfYfykAmoutSetService lfYfykAmoutSetService;
    @Autowired
    private AgentSOAServer agentSOAServer;
    @Autowired
    private SsoClientUtil ssoClientUtil;
    /**
     * 页面加载
     */
    @RequestMapping("amoutPage")
    public String getPage(){
        return "set/amout_coin_set";
    }
   /**
    * 充值金额列表
    */
    @RequestMapping("/list")
    @ResponseBody
    public List<LfYfykAmoutSet> list(LfYfykAmoutSet amoutSet){
        
        return lfYfykAmoutSetService.getList(amoutSet);
    }
    
    /**
     * 显示、隐藏修改
     */
    @RequestMapping("/updateStatus")
    @ResponseBody
    public Response updateStatus(int id,int yfykStatus){
        return lfYfykAmoutSetService.updateStatus(id,yfykStatus);
    }
    /**
     * 修改页面
     */
    @RequestMapping("/updatePage")
    public String updatePage(int id,ModelMap model){
        LfYfykAmoutSet amoutSet= lfYfykAmoutSetService.getAmoutSetById(id);
        model.addAttribute("amoutSet",amoutSet);
        return "set/amout_coin_set_update";
    }
    /**
     * 保存修改数据
     */
    @RequestMapping("/save")
    @ResponseBody
    public Response save(LfYfykAmoutSet amoutSet){
       return lfYfykAmoutSetService.save(amoutSet);
    }
    /**
     * 增加页面
     */
    @RequestMapping("/addPage")
    public String addPage(){
        return "set/amout_coin_set_add";
    }
    
    /**
     * 增加保存
     */
    @RequestMapping("/add")
    @ResponseBody
    public Response add(@RequestParam("price") int price,@RequestParam("wkCoinDenomination") int wkCoinDenomination){
        LfYfykAmoutSet amoutSet = new LfYfykAmoutSet();
        amoutSet.setPrice(price);
        amoutSet.setWkCoinDenomination(wkCoinDenomination);
        return lfYfykAmoutSetService.add(amoutSet);
    }
    /**
     * 抢单费用配置页面
     */
    @RequestMapping("/agentFeeSetPage")
    public String feeSetPage(){
        return "set/agent_fee_set";
    }
    
    /**
     * 增加保存
     */
    @RequestMapping("/agentFeeSetList")
    @ResponseBody
    public List<LfAgentFeeSet> agentFeeSetList(){
        return lfYfykAmoutSetService.getFeeSetList(0);
    }
    
    /**
     * 抢单修改页面
     */
    @RequestMapping("/updateFeePage")
    public String updateFeePage(int id,ModelMap model){
        List<LfAgentFeeSet> feeSet = lfYfykAmoutSetService.getFeeSetList(id);
        model.addAttribute("feeSet", feeSet.get(0));
        return "set/agent_fee_set_update";
    }
    
    /**
     * 保存修改数据
     */
    @RequestMapping("/saveFeeSet")
    @ResponseBody
    public Response saveFeeSet(LfAgentFeeSet set){
        return lfYfykAmoutSetService.saveFeeSet(set);
    }
    /**
     * 查看日志记录
     */
    @RequestMapping("/lookUpdateRecordPage")
    public String lookUpdateRecordPage(int id,ModelMap model){
        model.addAttribute("id", id);
        return "set/agent_fee_set_log";
    }
    @RequestMapping("/lookUpdateRecordList")
    @ResponseBody
    public Response lookUpdateRecordList(LfYfykAmoutSetLog req){
        return lfYfykAmoutSetService.getFeeSetLogList(req);
    }
    @RequestMapping("/getCountNum")
    @ResponseBody
    public Response getCountNum(int status){
        return lfYfykAmoutSetService.getCountNum(status);
    }
    
    /**
     * 悟空币消费明细查询页面
     */
    @RequestMapping("/getWkCoinConsumePage")
    public String getWkCoinConsumePage(){
        return "set/wk_coin_consume";
    }
    
    /**
     * 悟空币消费明细数据
     */
    @RequestMapping("/getWkCoinConsumeList")
    @ResponseBody
    public Response getWkCoinConsumeList(WkCoinDetailModel wkcoin,HttpSession session){
        LfEmployee employee = getLoginEmployeeInfo(session);
        return lfYfykAmoutSetService.getWkCoinConsumeList(wkcoin,employee);
    }
    @RequestMapping("/getWkCoinConsumeTotal")
    @ResponseBody
    public Response getWkCoinConsumeTotal(WkCoinDetailModel wkcoin,HttpSession session){
        LfEmployee employee = getLoginEmployeeInfo(session);
        return lfYfykAmoutSetService.getWkCoinConsumeTotal(wkcoin,employee);
    }
    
    /**
    * 悟空币充值明细查询页面
    */
   @RequestMapping("/getWkCoinPayPage")
   public String getWkCoinPayPage(){
       return "set/wk_coin_pay";
   }
   
   /**
    * 悟空币消费明细数据
    */
   @RequestMapping("/getWkCoinPayList")
   @ResponseBody
   public Response getWkCoinPayList(WkCoinDetailModel wkcoin,HttpSession session){
       LfEmployee employee = getLoginEmployeeInfo(session);
       return lfYfykAmoutSetService.getWkCoinPayList(wkcoin,employee);
   }
   
   /**
    * h
    * 功能描述:TODO(描述这个方法的作用)
    *
    * <pre>
    * Modify Reason:(修改原因,不需覆盖，直接追加.)
    *     ln:   2016年3月29日      新建
    * </pre>
    *
    * @param wkcoin
    * @param session
    * @return
    */
   @RequestMapping("/getFranchiseeList")
   @ResponseBody
   public List<ComboModel> getFranchiseeList(){
       return lfYfykAmoutSetService.getFranchiseeListByCityId();
   }
   @RequestMapping("/getWkCoinPayTotal")
   @ResponseBody
   public Response getWkCoinPayTotal(WkCoinDetailModel wkcoin,HttpSession session){
       LfEmployee employee = getLoginEmployeeInfo(session);
       return lfYfykAmoutSetService.getWkCoinPayTotal(wkcoin,employee);
   }
   
   
   /**
    * 悟空币赠送页面
    */
   @RequestMapping("/getWkCoinGivePage")
   public String getWkCoinGivePage(){
       return "set/wk_coin_give";
   }
   /**
   * 悟空币赠送明细数据
   */
  @RequestMapping("/getWkCoinGiveList")
  @ResponseBody
  public Response getWkCoinGiveList(WkCoinGivelModel wkgive,HttpSession session){
      LfEmployee employee = getLoginEmployeeInfo(session);
      return lfYfykAmoutSetService.getWkCoinGiveList(wkgive,employee);
  }
  
  @RequestMapping("/getWkCoinGiveTotal")
  @ResponseBody
  public Response getWkCoinGiveTotal(WkCoinGivelModel wkgive,HttpSession session){
      LfEmployee employee = getLoginEmployeeInfo(session);
      return lfYfykAmoutSetService.getWkCoinGiveTotal(wkgive,employee);
  }

  /**
   * 悟空币赠送以公司为纬度
   */
  @RequestMapping("/addByCompanyPage")
  public String addByCompanyPage(){
      return "set/wk_coin_add_company";
  }
  /**
   * 悟空币赠送以公司为纬度保存
   */
  @RequestMapping("/addByCompanySave")
  @ResponseBody
  public Response addByCompanySave(WkCoinGivelModel wkgive){
      return lfYfykAmoutSetService.addByCompanySave(wkgive);
  }
  @RequestMapping("/getFranchiseeInfoList")
  @ResponseBody
  public Response getFranchiseeInfoList(WkCoinGivelModel wkgive,HttpSession session) throws Exception{
      LfEmployee employee = getLoginEmployeeInfo(session);
      if(employee.getCityId()==1){
          wkgive.setCityId(null);
      }else{
          PositionInfo info = agentSOAServer.getEmployeePositionInfo(employee.getId(), SystemInfo.OperationManagementSystem);
          Integer rCityOrgId = info.getCityAreaOrgId();
          wkgive.setCityId(rCityOrgId);
      }

      
      return lfYfykAmoutSetService.getFranchiseeInfoList(wkgive,employee);
  }
  /**
   * 悟空币赠送以经纪人为纬度
   */
  @RequestMapping("/addByAgentPage")
  public String addByAgentPage(){
      return "set/wk_coin_add_agent";
  }
  
  
  @RequestMapping("/getAgentList")
  @ResponseBody
  public Response getAgentList(WkCoinGivelModel wkgive,HttpSession session){
      LfEmployee employee = getLoginEmployeeInfo(session);
      return lfYfykAmoutSetService.getAgentList(wkgive,employee);
  }
  /**
   * 悟空币赠送以经纪人为纬度保存
   */
  @RequestMapping("/addByAgentSave")
  @ResponseBody
  public Response addByAgentSave(WkCoinGivelModel wkgive){
      return lfYfykAmoutSetService.addByAgentSave(wkgive);
  }
  
  
  /**
   * export 赠送列表
   */
  @RequestMapping("/exportGive")
  @ResponseBody
  public void exportGive(HttpServletResponse res,WkCoinGivelModel wkgive,HttpSession session){
        LfEmployee employee = getLoginEmployeeInfo(session);
        String xlsName = "悟空币赠送明细导出";
        String[] arr = {"公司名称", "城市", "区域", "门店", "经纪人", "获赠币额", "赠送日期", "操作人", "赠送原因" };
        List<WkCoinGiveExport> list = lfYfykAmoutSetService.getWkCoinGiveListNotPage(wkgive, employee);
        try {
            ExcelUtils.export(res, xlsName, arr, list, WkCoinGiveExport.class);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
  }
  @RequestMapping("/exportConsume")
  @ResponseBody
  public void exportConsume(HttpServletResponse res,WkCoinDetailModel model,HttpSession session){
        LfEmployee employee = getLoginEmployeeInfo(session);
        String xlsName = "悟空币消费明细导出";
        String[] arr = {"公司名称", "城市", "区域", "门店", "经纪人", "消费币额", "消费日期", "消费类型" };
        List<WkCoinConsumeExport> list = lfYfykAmoutSetService.getWkCoinConsumeListNotPage(model, employee);
        try {
            ExcelUtils.export(res, xlsName, arr, list, WkCoinConsumeExport.class);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
  }
  
  @RequestMapping("/exportPay")
  @ResponseBody
  public void exportPay(HttpServletResponse res,WkCoinDetailModel model,HttpSession session){
        LfEmployee employee = getLoginEmployeeInfo(session);
        String xlsName = "悟空币充值明细导出";
        String[] arr = {"公司名称", "城市", "区域", "门店", "经纪人", "充值悟空币额", "交易金额", "充值方式","交易流水","交易时间" };
        List<WkCoinPayExport> list = lfYfykAmoutSetService.getWkCoinPayListNotPage(model, employee);
        try {
            ExcelUtils.export(res, xlsName, arr, list, WkCoinPayExport.class);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
  }
  
  @RequestMapping("gotoWkCoinPage")
  public String gotoWkCoinPage(){
      return "operatingSystem/agent_wk_coin";
  }
  
  @RequestMapping("/getWkCoinReportList")
  @ResponseBody
  public  Response getWkCoinReportList(WkcoinReport report,HttpSession session){
        LfEmployee employee = getLoginEmployeeInfo(session);
        return lfYfykAmoutSetService.getWkCoinReportList(report,employee);
  }
  @RequestMapping("/getWkCoinReportExport")
  @ResponseBody
  public  void getWkCoinReportExport(HttpServletResponse res,WkcoinReport model,HttpSession session){
      LfEmployee employee = getLoginEmployeeInfo(session);
      String xlsName = "悟空币报表";
      String[] arr = {"公司名称", "城市", "区域", "门店", "经纪人", "行程奖励币额", "获赠币额", "充值币额","消耗币额" };
      List<WkCoinReportExport> list = lfYfykAmoutSetService.getWkCoinReportListNotPage(model, employee);
      try {
          ExcelUtils.export(res, xlsName, arr, list, WkCoinReportExport.class);
      }
      catch (IllegalArgumentException e) {
          e.printStackTrace();
      }
      catch (IllegalAccessException e) {
          e.printStackTrace();
      }
}
  
}
