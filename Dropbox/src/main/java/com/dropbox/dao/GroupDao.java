package com.dropbox.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.dropbox.dao.DaoManager;

import model.File;
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
	
	public Group getGroup(Integer id) {
		return em.find(Group.class, id);
	}
	
	public void addGroup(Integer groupId, String name)
	{
		Group g = new Group();
		g.setGroupName(name);
		g.setOId(groupId);
		em.getTransaction().begin();
		em.persist(g);
		em.getTransaction().commit();
	}
	
	public void addGroup(Group g)
	{
		em.getTransaction().begin();
		em.persist(g);
		em.getTransaction().commit();
	}
	
	public void deleteGroup(Integer id) 
	{
		Group g = em.find(Group.class, id);
		if (g != null) 
		{
			em.getTransaction().begin();
			em.remove(g);
			em.getTransaction().commit();
		}
	}
}
