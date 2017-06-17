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

import com.dropbox.dao.FileDao;
import com.dropbox.dao.UserDao;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.User;

@Path("/register")
public class RegistrationResource
{
	@Context
	Request request;
	UserDao dao = UserDao.getInstance();
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(User user)
	{
		String username = user.getUsername();	//json.get("username").getAsString();
		String email = user.getEmail();			//json.get("email").getAsString();
		String password = user.getPasswd();		//json.get("password").getAsString();
		//checkt, ob nutzername schon vergeben ist
		if (dao.getUserByUsername(username) != null)
		{
			return Response.serverError().build();
		}
		//checkt, ob nutzername schon vergeben ist
		if (dao.getUserByEmail(email) != null)
		{
			return Response.serverError().build();
		}
		//Erstellt user neu, da oid gesetzt und password gehasht werden muss
		User u = new User();
		u.setEmail(email);
		u.setUsername(username);
		u.setOId(dao.getMaxId() + 1);
		u.setPasswd(password);
		dao.insertUser(u);
		FileDao.getInstance().createDirectory("./" + username, u);
		return Response.ok().build();
	}
	
}
