package com.lifang.agentsm.controller;

import java.util.ArrayList;
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

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.service.InformService;

@Controller
@RequestMapping("/inform")
public class InformImageController extends BaseController {

	protected Logger logger = LoggerFactory.getLogger(InformImageController.class);

	@Autowired
	private InformService informService;

	/**
	 * 跳转图片举报管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "/gotoImageInform.action")
	public String showImageInform() {
		return "inform/imageInform";
	}

	
	@RequestMapping(value = "/gotosellPointInform.action")
	public String showSellPoint() {
		return "inform/sellPointInform";
	}
	
	//加载图片举报列表
    @RequestMapping(value = "/getImageInformList.action")
  	@ResponseBody
  	public  Map<String, Object> getImageAuditList(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars){
    	
    	processMiniParam(pars);
    	
    	LfEmployee employee= new LfEmployee();
		try {
			employee = getLoginEmployeeInfo(request.getSession());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		pars.put("agentCode",employee.getId());
		pars.put("agentCity", employee.getCityId());

    	if(pars.get("accusationReasonId") != null){
    		
    		String str = pars.get("accusationReasonId")+"";
    		String s[] = str.split(",");
    		
    		List<String> slist = new ArrayList<String>();
    		for(String s1 : s){
    			slist.add(s1);
    		}
    	
    		pars.put("list", slist);
    	}
    	
  		return informService.getInformList(pars);
  	}
	
    
    @RequestMapping(value = "/updateInform.action")
    @ResponseBody
    public Object updateInform(HttpServletRequest request,HttpServletResponse response, @RequestParam Map<String, Object> pars)
    {
    	String result = null;
    	LfEmployee employee= new LfEmployee();
		try {
			employee = getLoginEmployeeInfo(request.getSession());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	pars.put("employeeId", employee.getId()+"");
    	try{
			informService.updateInform(pars);
			result = "1";
			response.getWriter().write(result);
    	}catch(Exception e){
    		result = "0";
    		logger.info("修改失败",e);
		}
    	return null;
    }
    @RequestMapping(value = "/deleteInform.action")
    @ResponseBody
    public Object deleteInform(HttpServletRequest request,HttpServletResponse response, @RequestParam Map<String, Object> pars)
    {
    	String result = null;
    	try{
			informService.deleteInform(pars.get("id")+"");
			result = "1";
			response.getWriter().write(result);
    	}catch(Exception e){
    		result = "0";
    		logger.info("删除失败",e);
		}
    	return null;
    }
}
