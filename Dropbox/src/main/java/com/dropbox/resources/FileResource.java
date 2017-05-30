package com.dropbox.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.JAXBElement;
import javax.ws.rs.PathParam;

import com.dropbox.dao.FileDao;
import com.dropbox.dao.UserDao;
import model.User;
import model.File;

//http://localhost:8080/Dropbox/file/Julian/home/test
@Path("/file/{user}/{path}")
public class FileResource 
{
	@GET
	@Produces("/text/plain")
	public Response getFile(@PathParam("user") String u, @PathParam("path") String p)
	{
		model.File file_owner = FileDao.getInstance().getFileByPath(p);
		if (!file_owner.getUser().getUsername().matches(u))
			return null;
		java.io.File result = new java.io.File(p);
		
		if (!result.exists()) 
			return null;
		
		ResponseBuilder response = Response.ok((Object) result);
		response.header("Content-Disposition", "attacchment; filename=\"" +  result.getName() + "\"");
		return response.build();
	}
	
	
}
