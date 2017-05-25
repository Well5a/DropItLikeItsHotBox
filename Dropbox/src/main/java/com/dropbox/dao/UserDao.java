package com.dropbox.dao;

import javax.persistence.EntityManager;
import com.dropbox.dao.DaoManager;
import model.User;


public class UserDao {
	private static EntityManager em;
	private static UserDao singleton;
	
	private UserDao() {
		em = DaoManager.getInstance().getEntityManager();
	}

	public static UserDao getInstance() {
		if (singleton == null) {
			singleton = new UserDao();
		}
		return singleton;
	}
	
	public User getUser(Integer id) {
		return em.find(User.class, id);
	}
	
	public void insertUser(User u)
	{
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
	}	
	
	public void deleteUser(Integer id) {

		User u = em.find(User.class, id);
		if (u != null) {
			em.getTransaction().begin();
			em.remove(u);
			em.getTransaction().commit();
		}
	}
	
}
