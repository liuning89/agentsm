package com.lifang.agentsm.utils;

import javax.servlet.http.HttpServletRequest;

import com.lifang.agentsm.entity.LfEmployee;

public class SessionUtils {
	
	public static LfEmployee getSession(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(Constants.LOGIN_SESSION);
		return  null == obj ? null : (LfEmployee) obj;
		//LoginSessionInterface lgObj = SessionHelper.getLoginSession(request);
		//return lgObj == null ? null : (EmployeeModel)lgObj;
	}
	
	public static void remove(HttpServletRequest request){
		request.getSession().removeAttribute(Constants.LOGIN_SESSION);
		//SessionHelper.remove(request);
	}
	
	public static void setLoginSession(HttpServletRequest request,LfEmployee emp){
		request.getSession().setAttribute(Constants.LOGIN_SESSION, emp);
		//SessionHelper.setLoginSession(request, emp);
	}

}
