package com.lifang.agentsm.utils;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lifang.agentsm.controller.ConfigReloadController;

public class SessionInterceptor extends HandlerInterceptorAdapter {
	
	
	private List<String> allowHosts;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<String> getAllowHosts() {
		return allowHosts;
	}

	public void setAllowHosts(List<String> allowHosts) {
		this.allowHosts = allowHosts;
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();
//		/** 暴露的外部接口中，一律包含/rest/，其余的内部接口不得包含/rest/ */
//		if(path.indexOf("/rest/") >= 0){
//			return super.preHandle(request, response, handler);
//		}
		

		String host = request.getRemoteAddr();
        ConfigReloadController configReloadController =SpringUtils.getBean(ConfigReloadController.class);
        
        if (configReloadController.isValid(host)) {
            //ip合法时,session不存在且访问非首页，非登陆页，则将其导向首页
            if (SessionUtils.getSession(request) == null && ! request.getServletPath().startsWith("/index.action") &&
                    ! request.getServletPath().startsWith("/login.action") ) {
                PrintWriter out = response.getWriter();
                out.write("<script>window.parent.location.href='" + request.getContextPath() + "/index.action" + "'</script>");
                out.flush();
                
                return false;
            }else {
                return super.preHandle(request, response, handler);
            }
            
        }
        
        return false;
		
	}

	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	public void init() {
		if (allowHosts != null && allowHosts.size() > 0) {
			for (int i = 0; i < allowHosts.size(); i++) {
				String one = allowHosts.get(i);
				if (one !=null && one.endsWith("*")) {
					one = one.substring(0,one.length()-1);
					allowHosts.set(i, one);
				}
				System.out.println("ip:" + one);
			}
		}
		
	}

	

}
