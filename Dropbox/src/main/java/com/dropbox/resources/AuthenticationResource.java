package com.dropbox.resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.dropbox.dao.UserDao;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Path("/authenticate")
public class AuthenticationResource
{
	@Context
	Request request;
	
	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response loginUser(JsonObject json)
	{
		HttpSession session = ((HttpServletRequest)request).getSession();
		if (session.getAttribute("user") != null)
			return Response.noContent().build();
		String username = json.get("username").getAsString();
		String password = json.get("password").getAsString();
		
		if(UserDao.getInstance().authenticate(username, password))
			session.setAttribute("user", username);
		return Response.ok().build();
	}
	
	
	@Path("/logout")
	public Response logoutUser()
	{
		HttpSession session = ((HttpServletRequest)request).getSession();
		session.invalidate();
		return Response.ok().build();
	}
}