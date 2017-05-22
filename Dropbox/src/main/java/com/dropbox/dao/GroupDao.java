package com.dropbox.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.dropbox.dao.DaoManager;

import model.Group;

public class GroupDao {
	
	private static EntityManager em;
	private static GroupDao singleton;
	
	private GroupDao() {
		em = DaoManager.getInstance().getEntityManager();
	}

	public static GroupDao getInstance() {
		if (singleton == null) {
			singleton = new GroupDao();
		}
		return singleton;
	}
}
