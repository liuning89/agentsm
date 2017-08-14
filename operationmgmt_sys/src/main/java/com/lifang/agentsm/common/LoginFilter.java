package com.lifang.agentsm.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.lifang.agentsm.controller.ConfigReloadController;
import com.lifang.agentsm.utils.Constants;
import com.lifang.agentsm.utils.SessionUtils;
import com.lifang.agentsm.utils.SpringUtils;

public class LoginFilter implements Filter {
    /**
     * 被排除的路径集合
     */
    private String[] excludes;
    /**
     * 已经被排除过的地址集合
     */
    private final List<String> free = new ArrayList<String>();
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ResourceBundle rb = ResourceBundle.getBundle("config");
        String s = rb.getString("NO_LOGIN_CHECK_URL");
        if(!StringUtils.isEmpty(s)){
            excludes = s.split(";");
        }
      filterConfig.getServletContext().setAttribute("version",rb.getString("version"));
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String url = request.getServletPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+request.getContextPath()+"/";
        
        String host = request.getRemoteAddr();
        ConfigReloadController configReloadController =SpringUtils.getBean(ConfigReloadController.class);
        //ip地址放行
        if(configReloadController.isValid(host)){
        	if (isFree(url)) {
        		chain.doFilter(request, response);
        	}else{
        		HttpSession session = request.getSession();
        		Object object = session.getAttribute(Constants.LOGIN_SESSION);
        		if(object == null){
        			response.sendRedirect(basePath+"login.jsp");
        			return;
        		}
        		chain.doFilter(request, response);
        	}
        //ip验证没通过，跳转到限制访问页面
        }else{
        	if(url.endsWith("astrictAccess.jsp")){
                chain.doFilter(request, response);
            }else{
                response.sendRedirect(basePath+"limitAccess.jsp");
            }
        }
    }

    
    /**
     * 检查请求地址是否需要被放过
     * @author  程康
     * <p>创建时间 2014-4-23下午3:32:38</p>
     * @param url
     * @return
     */
    private boolean isFree(String url){
        if(free.contains(url)){
            return true;
        }else{
            Pattern pattern = null;
            Matcher matcher = null;
            if(excludes!=null){
                for(String s:excludes){
                    pattern = Pattern.compile(s);
                    matcher = pattern.matcher(url);
                    if(matcher.find()){
                        free.add(url);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    
    
    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }
    
    public static void main(String[] args) {
    	Pattern pattern = Pattern.compile("login");
    	Matcher matcher= pattern.matcher("/login.jsp");
        if(matcher.find()){
            System.out.println(true);
        }
	}

}
