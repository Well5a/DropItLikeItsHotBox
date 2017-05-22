package com.dropbox.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.dropbox.dao.DaoManager;

import model.File;


public class FileDao {
	private static EntityManager em;
	private static FileDao singleton;
	
	private FileDao() {
		em = DaoManager.getInstance().getEntityManager();
	}

	public static FileDao getInstance() {
		if (singleton == null) {
			singleton = new FileDao();
		}
		return singleton;
	}
	
	public File getFile(Integer id) {
		return em.find(File.class, id);
	}
	
}
	