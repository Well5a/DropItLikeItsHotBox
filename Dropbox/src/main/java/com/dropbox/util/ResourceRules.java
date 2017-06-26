package com.dropbox.util;

public class ResourceRules
{
	public static final String [] whitelist = {
			"/",
			"/index.html",
			"/login.html",
			"/authenticate/login",
			"/authenticate/logout",
			"/authenticate",
			"/register"
	};
	
	public static final boolean checkWhitelisted(String requestUri)
	{
		for (String uri : whitelist)
		{
			if (uri.equals(requestUri))
				return true;
		}
		return false;
	}
}