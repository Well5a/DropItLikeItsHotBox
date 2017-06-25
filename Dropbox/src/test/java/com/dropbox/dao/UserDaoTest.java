package com.dropbox.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.User;

public class UserDaoTest {
	
	private UserDao dao;
	private User u; 
	
	/*
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		dao = UserDao.getInstance();
	}*/
	
	@Test
	public void authenticateTest()
	{
		assertTrue(UserDao.getInstance().authenticate("RandyRanderson", "guest"));
		java.io.File file = new java.io.File(".\\");
		System.out.println(file.getAbsolutePath());
	}
/*
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
	}*/
}
