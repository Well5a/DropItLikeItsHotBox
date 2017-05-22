package com.dropbox.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.dropbox.dao.FilepermissiontableDao;
import com.dropbox.dao.DaoManager;

import model.Filepermissionstable;

public class FilepermissiontableDao {

	private static EntityManager em;
	private static FilepermissiontableDao singleton;
	
	private FilepermissiontableDao() {
		em = DaoManager.getInstance().getEntityManager();
	}

	public static FilepermissiontableDao getInstance() {
		if (singleton == null) {
			singleton = new FilepermissiontableDao();
		}
		return singleton;
	}
}
