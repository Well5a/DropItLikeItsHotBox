package com.dropbox.dao;

import java.security.SecureRandom;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.dropbox.dao.DaoManager;

import model.File;
import model.User;
import com.dropbox.util.PasswordHasher;

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
	
	public User getUserByUsername(String username)
	{
		if (username == null)
			return null;
		Query q = em.createQuery("SELECT u FROM User u WHERE u.username LIKE :username");
		q.setParameter("username", username);
		return q.getResultList().isEmpty() ? 
				null : (User)q.getResultList().get(0);
	}
	
	public User getUserByEmail(String email)
	{
		Query q = em.createQuery("SELECT u FROM User u WHERE u.email LIKE :email");
		q.setParameter("email", email);
		return q.getResultList().isEmpty() ? 
				null : (User)q.getResultList().get(0);
	}
	
	public void insertUser(User u)
	{
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
	}
	
	public void saveUser(User user) {
		em.getTransaction().begin();
		em.persist(user);
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
	
	public Integer getMaxId()
	{
		Query q = em.createNativeQuery("SELECT oId FROM dropbox.user ORDER BY oId DESC LIMIT 1");
		
		if (!q.getResultList().isEmpty())
		{
			int ret = (Integer)q.getResultList().get(0);
			return ret;
		}
		return 0;
	}
	
	public boolean authenticate(String username, String password)
	{
		User u;
		if ((u = getUserByUsername(username))== null)
			return false;
		return PasswordHasher.checkPassword(u.getPasswd(), password);
	}
}
