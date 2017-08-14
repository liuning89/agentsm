package com.lifang.agentsm.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentcommsoa.service.AgentCommonService;
import com.lifang.agentcommsoa.util.ResponseStatus;
import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.Employee;
import com.lifang.agentsm.service.RefuseGuestService;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.callsoa.facade.CallSOAService;
import com.lifang.callsoa.model.BindPhoneResponse;
import com.lifang.model.Response;

@Controller
@RequestMapping("/refuse")
public class RefuseGuestController extends BaseController {

	protected Logger logger = LoggerFactory
			.getLogger(RefuseGuestController.class);

	@Autowired
	private RefuseGuestService refuseGuestService;
//	@Autowired
//	private CallSOAService callSoaClient;
	@Autowired
	private CallSOAService callSOAService ;
	@Autowired
	private AgentCommonService agentCommonService;
	
	@Autowired
	private AgentSOAServer agnetSOA;
	
	/**
	 * 跳转添加黑名单列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/refuseshow.action")
	public String agentListIndex() {
		return "agent/refuseGuest";
	}
	/**
	 * 跳转到手机解锁页面
	 * @return
	 */
	@RequestMapping(value = "/phoneunlock.action")
	public String showPhoneunlock() {
		return "agent/phoneUnlock";
	}

	/**
	 * 添加黑名單
	 * 
	 * @param request
	 * @param response
	 * @param pars
	 * @return
	 */
	@RequestMapping(value = "/addrefuseGuest.action")
	@ResponseBody
	public Object updateAudit(HttpServletRequest request,HttpServletResponse response, @RequestParam Map<String, Object> pars) {
		LfEmployee employee= new LfEmployee();
		try {
			employee = getLoginEmployeeInfo(request.getSession());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		pars.put("employeeId", employee.getId());
		String result = null;
		try{
			List<LfEmployee> empList = refuseGuestService.selectById(pars);
			if(empList != null && empList.size() > 0){
				result = "2";
				logger.info("黑名单已存在!");
			}else{
				refuseGuestService.insertRefuseGuest(pars);
				result = "1";
				logger.info("添加黑名单成功!");
			}
		}catch(Exception e){
			result = "0";
			logger.info("添加黑名单失败");
		}
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 手机解锁
	 * 
	 * @param request
	 * @param response
	 * @param pars
	 * @return
	 */
	@RequestMapping(value = "/updateLfEmp.action")
	@ResponseBody
	public Response updateLfEmp(HttpServletRequest request,HttpServletResponse response, @RequestParam Map<String, Object> pars) {
		LfEmployee employee= new LfEmployee();
		ResponseStatus rs=null;
		try {
			employee = getLoginEmployeeInfo(request.getSession());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		pars.put("employeeId", employee.getId());
		String result = null;
		try{
		    
		    Employee e = refuseGuestService.getEmployeeByMobile(String.valueOf(pars.get("mobile")));
            if(e!=null){
                rs = agentCommonService.clearAgentCache(e.getId());
            }else{
                rs = new ResponseStatus();
                rs.setMessage("经纪人不存在");
                rs.setStatus(0);
            }
			//refuseGuestService.updateLfEmp(pars);
			
			logger.info("解锁成功!"+pars.get("mobile"));
		}catch(Exception e){
			result = "0";
			logger.info("解锁失败失败",e);
		}
		
		return new Response(rs.getMessage(),rs.getStatus());
	}
	
	/**
	 * 手机解锁
	 * 
	 * @param request
	 * @param response
	 * @param pars
	 * @return
	 */
	@RequestMapping(value = "/selectPhoneCount.action")
	@ResponseBody
	public Object selectPhoneCount(HttpServletRequest request,HttpServletResponse response, @RequestParam Map<String, Object> pars) {
		LfEmployee employee= new LfEmployee();
		try {
			employee = getLoginEmployeeInfo(request.getSession());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		pars.put("employeeId", employee.getId());
		String result = null;
		try{
			Date date=new Date();//取时间
			Date endDate=new Date();//取时间
		    Calendar calendar = new GregorianCalendar();
		    calendar.setTime(date);
		    calendar.add(calendar.MONTH,-1);//把日期往后增加一天.整数往后推,负数往前移动
		    date=calendar.getTime(); //这个时间就是日期往后推一天的结果
		    
		    
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    System.out.println(sdf.format(date));
			pars.put("startDate",sdf.format(date));
			pars.put("endDate",sdf.format(endDate));
			
			int count = refuseGuestService.selectPhoneCount(pars);
			
			result = count + "";
			logger.info("查询成功!"+count);
		}catch(Exception e){
			result = "-1";
			logger.info("查询解锁次数失败",e);
		}
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
    /**
     * 功能描述:跳转白名单列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月17日      新建
     * </pre>
     *
     * @return
     */
    @RequestMapping(value = "/whitelistShow.action")
    public String whitelistShow() {
        return "agent/whitelistShow";
    }
    
    /**
     * 功能描述:添加白名单
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月17日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/addWhitelist.action")
    @ResponseBody
    public Object addWhitelist(HttpServletRequest request,HttpServletResponse response, @RequestParam Map<String, Object> pars) {
        String result = null;
        try{
            BindPhoneResponse bpr = callSOAService.addBindPhoneRet(pars.get("mobile") + "", 1);
            result = bpr.getStatus()+"";
        }catch(Exception e){
            result = "1";
            logger.info("添加白名单失败",e);
        }
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 功能描述:删除白名单
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月17日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/delWhitelist.action")
    @ResponseBody
    public Object delWhitelist(HttpServletRequest request,HttpServletResponse response, @RequestParam Map<String, Object> pars) {
        String result = null;
        try{
			callSOAService.delBindPhone(pars.get("mobile")+"");
            result = "1";
        }catch(Exception e){
            result = "0";
            logger.info("删除白名单失败");
        }
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
