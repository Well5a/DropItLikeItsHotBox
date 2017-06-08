package com.dropbox.filter;

import javax.servlet.http.*;

import com.dropbox.dao.FileDao;
import com.dropbox.dao.FilepermissionDao;
import com.dropbox.dao.UserDao;

import model.File;
import model.Filepermission;
import model.User;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

class AuthorizationFilter implements Filter
{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		boolean authorized = false;
		
		HttpServletRequest request = (HttpServletRequest) req; 
		HttpServletResponse response = (HttpServletResponse) res; 
		
		//Es handelt sich garnicht um eine Dateiabfrage
		if (!request.getContextPath().startsWith("/browse"))
		{
			chain.doFilter(request, response); //nächster Filter
		}
			
		String filepath = request.getContextPath().replace("/browse", "");
		String username = (String) request.getSession().getAttribute("user");
		
		User user = UserDao.getInstance().getUserByUsername(username);
		File requestedFile = FileDao.getInstance().getFileByPath(filepath);
		
		// dem User gehört die Datei
		authorized = requestedFile.getUser().equals(user);
		
		// Ist der User nicht Besitzer, wird geprüft, ob ihm das leserecht
		// auf die datei gegeben wurde
		if (!authorized)
		{
			Filepermission fp = FilepermissionDao.getInstance()
							 					 .getPermissionsOfUserForFile(
							 							requestedFile.getOId()
							 							, user.getOId());
			if (fp.getAllowRead() != 0)
			{
				authorized = true;
			}
		}
		// User ist authorisiert
		if (authorized)
		{
			chain.doFilter(request, response);
		}
		// User ist nicht authorisiert
		else
		{
			response.resetBuffer();
			response.setStatus(401); //httpstatus = 401 (unauthorized)
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}