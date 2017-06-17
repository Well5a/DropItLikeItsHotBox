package com.dropbox.resources;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

//http://localhost:8080/Dropbox/file/Julian/home/test
@Path("/box")
public class FileResource 
{
	private final String fileRoot = "./files/";
	private FileDao dao = FileDao.getInstance();
	
	@Context
	HttpServletRequest request;
	
	@GET
	@Path("/browse/{path}")
	public Response getFile(@PathParam("path") String p)
	{
		File requested;
		java.io.File fsFile;
		ResponseBuilder response = Response.ok();
		if ((requested = dao.getFileByPath(p)) != null)
		{
			fsFile = dao.getFileFromFilesystem(requested);
			
			response = Response.ok();
			if (fsFile.isDirectory())
			{
				response.entity(toJsonDirectory(fsFile));
			}
			else
			{
				byte [] bytes = new byte[(int)fsFile.length()];
				try {
					FileInputStream fis = new FileInputStream(fsFile);
					fis.read(bytes);
					response.entity(bytes);
					fis.close();
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
			response.header("Content-Disposition", "attachment; filename=\"" +  fsFile.getName() + "\"");
		}
		return response.build();
	}
	
	@POST
	@Path("/insertFile/{path}")
	public Response addFile(byte [] data, @PathParam("path") String path)
	{
		if (dao.getFileByPath(path) == null)
		{
			User user = UserDao.getInstance().getUserByUsername(
												(String)request.getSession(false)
												.getAttribute("user")
												);
			if(data.length == 0)
				dao.createFile(path, user);
			else
				dao.createFile(path, user, data);
			return Response.ok().build();
		}
		return Response.status(404).build();
	}
	
	@GET
	@Path("/insertDir/{path}")
	public Response addDirectory(@PathParam("path") String path)
	{
		if (dao.getFileByPath(path) == null)
		{
			User user = UserDao.getInstance().getUserByUsername(
												(String)request.getSession(false)
												.getAttribute("user")
												);
			if (user != null){
				dao.createDirectory(path, user);
				return Response.ok().build();
			}
		}
		return Response.status(404).build();
	}
	
	private JsonObject toJsonDirectory(java.io.File dir)
	{
		JsonObject result = null;
		if (dir.isDirectory())
		{
			result = new JsonObject();
			result.addProperty("path", dir.getPath());
			if (dir.listFiles().length != 0)
			{
				result.add("subdirectories", new JsonArray());
				for (java.io.File f : dir.listFiles())
				{
					try
					{
						result.getAsJsonArray("subdirectories").add(f.getCanonicalPath());
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}
}
