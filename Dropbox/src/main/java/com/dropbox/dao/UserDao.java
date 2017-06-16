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
		Query q = em.createQuery("SELECT u FROM User u WHERE u.username LIKE :username");
		q.setParameter("username", username);
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
	
	public boolean authenticate(String username, String password)
	{
		User u = getUserByUsername(username);
		return PasswordHasher.checkPassword(u.getPasswd(), password);
	}
}
