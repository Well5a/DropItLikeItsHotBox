package com.dropbox.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import model.User;
import com.dropbox.PrepareTests;

public class UserDaoTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PrepareTests.initDatabase();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
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
		
		User u = new User();
		u.setEmail("test@whatever.com");
		u.setOId(1);
		u.setPasswd("blblabaldasisteinhash");
		
		dao.insertUser(u);
		User newOldUser = dao.getUser(u.getOId());
		assertNotNull(newOldUser);
	}
	
	@Test
	public void testDeleteUser() {
		UserDao dao = UserDao.getInstance();
		
		User u = new User();
		u.setEmail("test@whatever.com");
		u.setOId(1);
		u.setPasswd("blblabaldasisteinhash");
		
		dao.insertUser(u);
		dao.deleteUser(u.getOId());
		User newOldUser = dao.getUser(u.getOId());
		assertNull(newOldUser);
	}
}
