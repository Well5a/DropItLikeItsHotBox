package com.dropbox.resources;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.dropbox.dao.UserDao;
import com.google.gson.Gson;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;

@Path("/authenticate")
public class AuthenticationResource
{
	@Context
	HttpServletRequest request;
	
	@GET
	public Response getAuthentication()
	{
		if (request.getSession().getAttribute("user") != null)
			return Response.ok().entity(request.getSession().getAttribute("user")).build();
		else 
			return Response.status(401).build();
	}
	
	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response loginUser(String data)
	{
		HttpSession session = request.getSession(false);
		if (session.getAttribute("user") != null)
			return Response.noContent().build();
		LoginData login = new Gson().fromJson(data, LoginData.class);
		String username = login.getUsername();//get("username").getAsString();
		String password = login.getPassword();//get("password").getAsString();
		
		if(UserDao.getInstance().authenticate(username, password))
		{
			session.setAttribute("user", username);
			return Response.ok().entity(data).build();
		}
		else
			return Response.status(401).build();
	}
	
	
	@Path("/logout")
	@GET
	public Response logoutUser()
	{
		HttpSession session = ((HttpServletRequest)request).getSession();
		session.invalidate();
		return Response.ok().build();
	}
	
	@XmlRootElement
	private class LoginData
	{
		@XmlElement private String username;
		@XmlElement private String password;
		
		public String getUsername(){
			return username;
		}
		public String getPassword(){
			return password;
		}
		public void setUsername(String name){
			username = name;
		}
		public void setPassword(String pwd){
			password = pwd;
		}
		public LoginData() {}
	}
}