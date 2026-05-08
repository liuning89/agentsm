package com.lifang.sso;

import javax.servlet.*;
import java.io.IOException;

public class SsoFilter implements Filter {
    private String ssoServerUrl;
    private String ssoClientUrl;
    private String whiteList;

    public void setSsoServerUrl(String ssoServerUrl) { this.ssoServerUrl = ssoServerUrl; }
    public void setSsoClientUrl(String ssoClientUrl) { this.ssoClientUrl = ssoClientUrl; }
    public void setWhiteList(String whiteList) { this.whiteList = whiteList; }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
