package com.dropbox.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.dropbox.dao.DaoManager;

import model.File;
import model.Group;
import model.User;
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
	
	public void addUserToGroup(User user, Group group) {
		Usergroup newug = new Usergroup();
		newug.setUser(user);
		newug.setGroup(group);
		insertUsergroup(newug);
	}
	
	public void removeUserFromGroup (Integer id) {
		/*TODO*/
	}
	
	public void insertUsergroup(Usergroup uG) {
		em.getTransaction().begin();
		em.persist(uG);
		em.getTransaction().commit();
	}
	
	public void deleteUsergroup(Integer id) {
		
		Usergroup uG = em.find(Usergroup.class, id);
		if (uG != null) {
			em.getTransaction().begin();
			em.remove(uG);
			em.getTransaction().commit();
		}
	}
}
