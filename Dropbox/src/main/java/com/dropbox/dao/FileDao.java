package com.dropbox.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.StoredProcedureQuery;

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
	
	public List<File> getFilesFromUserGroup(int groupId) {
		/*TODO*/
		return null;
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
	