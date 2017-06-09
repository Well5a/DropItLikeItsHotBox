package com.dropbox.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
	
	public int getMaxOId()
	{
		Query q = em.createQuery("SELECT TOP 1 oID FROM dropbox.Filepermission ORDER BY oID desc");
		return (int) q.getResultList().get(0);
	}
	
	public Filepermission getFilepermission(Integer id) {
		return em.find(Filepermission.class, id);
	}
	
	public Filepermission getPermissionsOfUserForFile(int fileid, int uid)
	{
		Query q = em.createQuery("SELECT * FROM dropbox.filepermission WHERE fileId=:fileid AND userId=:userid ");
		q.setParameter("fileid", fileid);
		q.setParameter("userid", uid);
		
		return (Filepermission) q.getResultList().get(0);
	}
		
	public void insertFilepermission(Filepermission fp)
	{
		em.getTransaction().begin();
		em.persist(fp);
		em.getTransaction().commit();
	}

	public void deleteFilepermission(Integer id) {
		
		Filepermission p = em.find(Filepermission.class, id);
		if (p != null) {
			em.getTransaction().begin();
			em.remove(p);
			em.getTransaction().commit();
		}
	}
}
