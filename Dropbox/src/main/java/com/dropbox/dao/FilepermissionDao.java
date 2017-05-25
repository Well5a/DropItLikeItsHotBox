package com.dropbox.dao;

import javax.persistence.EntityManager;
import com.dropbox.dao.DaoManager;

import model.Filepermission;

public class FilepermissionDao {

	private static EntityManager em;
	private static FilepermissionDao singleton;
	
	private FilepermissionDao() {
		em = DaoManager.getInstance().getEntityManager();
	}

	public static FilepermissionDao getInstance() {
		if (singleton == null) {
			singleton = new FilepermissionDao();
		}
		return singleton;
	}
	
	public Filepermission getFilepermission(Integer id) {
		return em.find(Filepermission.class, id);
	}
	
	public void insertFilepermission(Filepermission fp)
	{
		em.getTransaction().begin();
		em.persist(fp);
		em.getTransaction().commit();
	}
}
