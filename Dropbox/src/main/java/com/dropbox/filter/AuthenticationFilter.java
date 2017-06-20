package com.dropbox.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;

public class AuthenticationFilter implements Filter
{
	//Defines, which resources can be accessed without authentication
	public final String [] whitelist = {
			"/DropBox/login.html",
			"/DropBox/rest/authenticate/login",
			"/DropBox/rest/authenticate/logout",
			"/DropBox/rest/authenticate",
			"/DropBox/rest/register"
	};
	
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
		HttpSession session = request.getSession();
		
		boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = checkWhitelisted(request.getRequestURI());

        if (loggedIn || loginRequest) {
            chain.doFilter(request, response);
        } else {
            response.setStatus(401);
        }
	}

	private boolean checkWhitelisted(String requestUri)
	{
		for (String uri : whitelist)
		{
			if (uri.equals(requestUri))
				return true;
		}
		return false;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}