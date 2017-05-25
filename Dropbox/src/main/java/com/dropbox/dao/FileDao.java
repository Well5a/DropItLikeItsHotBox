package com.dropbox.dao;

import javax.persistence.EntityManager;
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
	
	public void insertFile(File f)
	{
		em.getTransaction().begin();
		em.persist(f);
		em.getTransaction().commit();
	}
	
	public File getFile(Integer id) {
		return em.find(File.class, id);
	}
	
		
	public void deleteFile(Integer id) {
		
		File f = em.find(File.class, id);
		if (f != null) {
			em.getTransaction().begin();
			em.remove(f);
			em.getTransaction().commit();
		}
	}
}
	