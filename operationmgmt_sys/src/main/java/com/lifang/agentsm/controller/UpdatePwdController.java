package com.lifang.agentsm.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.service.LfEmployeeService;
import com.lifang.agentsm.utils.Constants;
import com.lifang.utils.Md5Utils;
@RequestMapping("/update")
@Controller
public class UpdatePwdController{

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LfEmployeeService lfEmployeeService;
	
	
	  // 跳转到修改密码窗
    @RequestMapping(value = "/openUpPwdPage.action")
    public String openUpPwdPage(HttpServletRequest request, HttpServletResponse response) {
        return "upPwdPage";
    }
    
	/**
	 * 修改密码
	 * @param oldPw
	 * @param newPw
	 * @param session
	 * @return
	 */
	@RequestMapping("/updatePw.action")
	@ResponseBody
	private String updatePw(String oldPw,String newPw,HttpServletResponse response,HttpSession session) {
		String result = "";
		Map<String,Object> map = new HashMap<>();
		oldPw = Md5Utils.md5(oldPw);
		newPw = Md5Utils.md5(newPw);
		//验证旧密码
		LfEmployee employee = (LfEmployee)session.getAttribute(Constants.LOGIN_SESSION);
//		LfEmployee employee = getLoginEmployeeInfo(session);
    	
		
		map.put("loginName", employee.getMobile());
		map.put("password", oldPw);
		
		LfEmployee lfEmployee = lfEmployeeService.loginCheck(map);
		if(lfEmployee != null){//验证通过
			map.put("id", employee.getId());
			map.put("newpassword", newPw);
			lfEmployeeService.updatePw(map);		
			
			result = "1";
			
		}else{
			//验证不通过
			result = "0";
		}
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if("1".equals(result)){
			session.removeAttribute(Constants.LOGIN_SESSION);
		}
		
		return null;
	}
	
}
