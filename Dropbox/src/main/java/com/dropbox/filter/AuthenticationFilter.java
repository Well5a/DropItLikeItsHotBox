package com.dropbox.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;

import com.dropbox.util.ResourceRules;

public class AuthenticationFilter implements Filter
{
	//Defines, which resources can be accessed without authentication
	private FilterConfig filterConfig;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		
		boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = ResourceRules.checkWhitelisted(request.getPathInfo());

        if (loggedIn || loginRequest) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect("/DropBox/index.html");
        }
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}