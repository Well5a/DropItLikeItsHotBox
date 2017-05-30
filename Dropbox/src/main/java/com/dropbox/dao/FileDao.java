package com.dropbox.dao;

import java.util.List;

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
	
	public void insertFile(File f)
	{
		em.getTransaction().begin();
		em.persist(f);
		em.getTransaction().commit();
	}
	
	public File getFile(Integer id) {
		return em.find(File.class, id);
	}
	
	public File getFileByPath(String path)
	{
		Query q = em.createQuery("SELECT * FROM file WHERE file.path = :path");
		q.setParameter("path", path);
		return (File)q.getResultList().get(0);
	}
	
	
	public List<File> getFiles() {
		Query q = em.createQuery("select u from user u");
		List<File> files = q.getResultList();
		return files;
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
	