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
		
		JsonObject clientMessage = new JsonObject();
		
		//checkt, ob nutzername schon vergeben ist
		if (dao.getUserByUsername(username) != null)
		{
			clientMessage.addProperty("message", "username already in use");
			return Response.status(401).entity(clientMessage.toString()).build();
		}
		//checkt, ob nutzername schon vergeben ist
		if (dao.getUserByEmail(email) != null)
		{
			clientMessage.addProperty("message", "email already registered");
			return Response.status(401).entity(clientMessage.toString()).build();
		}
		clientMessage.addProperty("username", username);
		//Erstellt user neu, da oid gesetzt und password gehasht werden muss
		User u = new User();
		//u.setOId(dao.getMaxId());
		u.setEmail(email);
		u.setUsername(username);
		u.setAndHashPasswd(password);
		dao.insertUser(u);
		FileDao.getInstance().createDirectory(username, u);
		return Response.ok().entity(clientMessage.toString()).build();
	}
}
