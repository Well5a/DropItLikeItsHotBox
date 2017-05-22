package com.dropbox.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.dropbox.dao.DaoManager;

import model.File;
import model.Usergroup;

public class UsergroupDao {
	private static EntityManager em;
	private static UsergroupDao singleton;
	
	private UsergroupDao() {
		em = DaoManager.getInstance().getEntityManager();
	}

	public static UsergroupDao getInstance() {
		if (singleton == null) {
			singleton = new UsergroupDao();
		}
		return singleton;
	}
	
	public Usergroup getUsergroup(Integer id) {
		return em.find(Usergroup.class, id);
	}
}
