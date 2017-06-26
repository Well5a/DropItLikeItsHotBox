package com.dropbox.resources;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.dropbox.dao.FileDao;
import com.dropbox.dao.UserDao;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import model.File;
import model.User;

//http://localhost:8080/Dropbox/file/Julian/home/test
@Path("/box")
public class FileResource 
{
	private FileDao dao = FileDao.getInstance();
	
	@Context
	HttpServletRequest request;
	
	/**
	 * Resource handler for browsing the dropbox.
	 * Returns the File or Directory associated with
	 * parameter path
	 * 
	 * @param p path to the file or directory
	 * @return file or json representation of directory
	 */
	@GET
	@Path("/browse/{path : .+}")
	public Response getFile(@PathParam("path") String p)
	{
		File requested;
		java.io.File fsFile;
		ResponseBuilder response = Response.ok();
		//file exists
		if ((requested = dao.getFileByPath(p)) != null)
		{
			//get file form filesystem
			fsFile = dao.getFileFromFilesystem(requested);
			
			if (!fsFile.exists())
				return Response.status(404).build();
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
			response.header("Content-disposition", "attachment; filename=" +  fsFile.getName());
			response.header("Content-Type", "text/plain");
		}
		else
			return response.status(404).build();
		return response.build();
	}
	
	/**
	 * Resource handler for removing files in the dropbox
	 * 
	 * @param p path of the file to be removed
	 * @return Resonse with status 200 on success else 400
	 */
	@DELETE
	@Path("/remove/{path : .+}")
	public Response removeFile(@PathParam("path") String p)
	{
		File requested = dao.getFileByPath(p);
		java.io.File fsFile;
		ResponseBuilder response = Response.ok();
		//file exists
		dao.deleteFile(requested);
		return response.build();
	}
	
	/**
	 * Resource handler for inserting Files into the
	 * Dropbox.
	 * 
	 * @param data bytes of file
	 * @param path path of file
	 * @return status 200 on success, status 404 if file alreay exists
	 */
	@POST
	@Path("/insertFile/{path : .+}")
	public Response addFile(byte [] data, @PathParam("path")String p)
	{
		if (dao.getFileByPath(p) == null)
		{
			User user = UserDao.getInstance().getUserByUsername(
												(String)request.getSession(false)
												.getAttribute("user")
												);
			if(data.length == 0)
				dao.createFile(p, user);
			else
				dao.createFile(p, user, data);
			return Response.ok().build();
		}
		return Response.status(404).build();
	}
	
	/**
	 * inserts a new directory into the Dropbox.
	 * 
	 * @param path path to new directory
	 * @return status 200 on success, else 404 
	 */
	@POST
	@Path("/insertDir/{path : .+}")
	public Response addDirectory(@PathParam("path")String p)
	{
		if (dao.getFileByPath(p) == null)
		{
			User user = UserDao.getInstance().getUserByUsername(
												(String)request.getSession(false)
												.getAttribute("user")
												);
			if (user != null){
				dao.createDirectory(p, user);
				return Response.ok().build();
			}
		}
		return Response.status(404).build();
	}
	
	/**
	 * Creates a JSON Object representing the designated
	 * directory.
	 * 
	 * @param dir path to directory
	 * @return json representation of directory
	 */
	private String toJsonDirectory(java.io.File dir)
	{
		JsonObject result = null;
		if (dir.isDirectory())
		{
			result = new JsonObject();
			result.add("self", toJsonFileEntry(dir));
			if (getParentsPath(dir) != null)
				result.add("parent", toJsonFileEntry(dir.getParentFile()));
			if (dir.listFiles().length != 0)
			{
				result.add("subdirectories", new JsonArray());
				for (java.io.File f : dir.listFiles())
				{
					result.getAsJsonArray("subdirectories").add(toJsonFileEntry(f));	
				}
			}
		}
		return result.toString();
	}
	
	/**
	 * Converts the designated File to a JSON representation for display
	 * in the clients filebrowser.
	 * 
	 * @param f File to be converted
	 * @return JSON representation of the File
	 */
	private JsonObject toJsonFileEntry(java.io.File f)
	{
		if (!f.exists()) 
			return null;
		JsonObject ret = new JsonObject();
		ret.addProperty("type", f.isDirectory() ? "directory" : "file");
		ret.addProperty("name", f.getName());
		ret.addProperty("path", toResourcePath(f.getAbsolutePath()));
		ret.addProperty("lastChanged", f.lastModified());
		return ret;
	}
	
	/**
	 * Returns the path of the File parent as
	 * resource path
	 * 
	 * @param child
	 * @return path to the Files parent
	 */
	private String getParentsPath(java.io.File child)
	{
		File parentFileDescriptor = FileDao.getInstance()
											.getFileByPath(toResourcePath(child.getParent()).replace("\\", "/"));
		if (parentFileDescriptor != null 
				&& parentFileDescriptor
				.getUser()
				.getUsername()
				.equals( request.getSession(false)
						.getAttribute("user")) )
		{
			return toResourcePath(child.getParent());
		}
		else 
			return null;
	}
	
	/**
	 * Converts an absoluten path to a relaitve path
	 * which can be used by the client to query the File.
	 * 
	 * @param path absolute path to be converted
	 * @return relative path to resource
	 */
	private String toResourcePath(String path)
	{
		return new String(path).replace(dao.getFileRoot(), "");
	}
}
