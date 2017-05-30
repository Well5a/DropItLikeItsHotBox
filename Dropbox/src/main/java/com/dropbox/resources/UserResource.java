package com.dropbox.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import com.dropbox.dao.UserDao;
import model.User;

@Path("user/{username}")
public class UserResource 
{
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public User userExists(@PathParam("username") String username)
	{
		return UserDao.getInstance().getUserByUsername(username);	
	}
}
