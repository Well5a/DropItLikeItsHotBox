package com.dropbox.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.dropbox.dao.DaoManager;

import model.User;


public class UserDao {
	private static EntityManager em;
	private static UserDao singleton;
	
	private UserDao() {
		em = DaoManager.getInstance().getEntityManager();
	}

	public static UserDao getInstance() {
		if (singleton == null) {
			singleton = new UserDao();
		}
		return singleton;
	}
}
