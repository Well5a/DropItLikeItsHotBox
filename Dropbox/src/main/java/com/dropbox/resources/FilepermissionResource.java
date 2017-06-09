package com.dropbox.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;

import com.dropbox.dao.FileDao;
import com.dropbox.dao.FilepermissionDao;
import com.dropbox.dao.UserDao;
import com.google.gson.JsonObject;
import javax.servlet.http.HttpServletRequest;

import model.*;

@Path("/permit")
public class FilepermissionResource {
	
	@Context
	Request request;
	
	@Path("/grant")
	@Consumes(MediaType.APPLICATION_JSON)
	public void grantPermission(JsonObject json)
	{
		String thisUsersName;
		String username = json.get("user").getAsString();
		String filepath = json.get("file").getAsString();
		String permission = json.get("permission").getAsString();
		
		User u;
		File f;
		Filepermission fp;
		byte read = 0, write = 0;
		
		if ( (thisUsersName = (String)((HttpServletRequest)request).getSession().getAttribute("user")) == null)
		{
			return;
		}
		
		switch(permission)
		{
			case "r":
			{
				read = 1;
				break;
			}
			case "rw":
			{
				read = 1;
				write = 1;
				break;
			}
			default:
			{
				return;
			}
		}
		
		if((u = UserDao.getInstance().getUserByUsername(username)) == null)
		{
			return;
		}
		else if((f = FileDao.getInstance().getFileByPath(filepath)) == null)
		{
			return;
		}
		else if (!f.getUser().getUsername().equals(thisUsersName))
		{
			return;
		}
		else if((fp = FilepermissionDao.getInstance().getPermissionsOfUserForFile(f.getOId(), u.getOId())) == null)
		{
			int maxId = FilepermissionDao.getInstance().getMaxOId() + 1;
			fp = new Filepermission();
			fp.setOId(maxId);
			fp.setFile(f);
			fp.setUser(u);
			fp.setAllowRead(read);
			fp.setAllowRead(write);
			
			FilepermissionDao.getInstance().insertFilepermission(fp);
		}
		else //Filepermission existiert bereits
		{
			fp.setAllowRead(read);
			fp.setAllowRead(write);
		}
	}
	
	@Path("/revoke")
	@Consumes(MediaType.APPLICATION_JSON)
	public void revokePermission(JsonObject json)
	{
		String thisUsersName;
		String username = json.get("user").getAsString();
		String filepath = json.get("file").getAsString();
		String permission = json.get("permission").getAsString();
		
		User u;
		File f;
		Filepermission fp;
		byte read = 1, write = 1;
		
		if ( (thisUsersName = (String)((HttpServletRequest)request).getSession().getAttribute("user")) == null)
		{
			return;
		}
		
		switch(permission)
		{
			case "r":
			{
				write = 0;
				read = 0;
				
				break;
			}
			case "w":
			{
				write = 0;
				break;
			}
			default:
			{
				return;
			}
		}
		if((u = UserDao.getInstance().getUserByUsername(username)) == null)
		{
			return;
		}
		else if((f = FileDao.getInstance().getFileByPath(filepath)) == null)
		{
			return;
		}
		else if (!f.getUser().getUsername().equals(thisUsersName))
		{
			return;
		}
		else if((fp = FilepermissionDao.getInstance().getPermissionsOfUserForFile(f.getOId(), u.getOId())) != null)
		{
			fp.setAllowRead(read);
			fp.setAllowRead(write);
		}
		return;
	}
}
