package com.hod.util;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChineseFilter implements Filter {
    private FilterConfig filterConfig;
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    /**
     * 解决中文问题.（对于post方法提交的请求适用）
     * 
     */
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain filterChain) {
    	try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse)response;
            res.setHeader("Pragma","No-cache");          
            res.setHeader("Cache-Control","no-cache");   
            res.setHeader("Cache-Control", "no-store");  //适用于火狐浏览器 
            res.setDateHeader("Expires",0); 
            String path = ((HttpServletRequest)request).getServletPath();
            HttpSession session = req.getSession();
            if(path.startsWith("/images/")||path.startsWith("/ext/")||path.startsWith("/js/")||path.startsWith("/css/")||path.equals("/login!doLogin.do")||path.equals("/login.jsp")){
				filterChain.doFilter(request, response);
            }else{
				if (session.getAttribute("user") == null && !path.endsWith("!httpClientMeterInterface.do")) {
					res.sendRedirect(req.getContextPath() + "/login.jsp");
				} else {
					filterChain.doFilter(request, response);
				}
            }
        } catch (Exception iox) {
            filterConfig.getServletContext().log(iox.getMessage());
        }
    }

    public void destroy() {
    }
}
