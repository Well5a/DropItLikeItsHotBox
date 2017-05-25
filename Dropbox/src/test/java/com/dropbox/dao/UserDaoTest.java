package com.dropbox.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.User;

public class UserDaoTest {
	
	private static User u = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		u = new User();
		u.setEmail("test@whatever.com");
		u.setOId(666);
		u.setPasswd("blblabaldasisteinhash");
		u.setUsername("testUser");
		
		UserDao.getInstance().insertUser(u);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		UserDao dao = UserDao.getInstance();
		dao.deleteUser(u.getOId());
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetInstance() {
		UserDao c = UserDao.getInstance();
		assertNotNull(c);
	}
	
	@Test
	public void testGetUser() {
		UserDao dao = UserDao.getInstance();
		
		User newOldUser = dao.getUser(u.getOId());
		assertNotNull(newOldUser);
	}
	
	@Test
	public void testDeleteUser() {
		UserDao dao = UserDao.getInstance();
		
		dao.deleteUser(u.getOId());
		User newOldUser = dao.getUser(u.getOId());
		assertNull(newOldUser);
	}
}
