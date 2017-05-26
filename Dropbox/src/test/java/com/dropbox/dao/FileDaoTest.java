package com.dropbox.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.File;
import model.User;

public class FileDaoTest {

	private static User u = null;
	private static File f = null;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		u = new User();
		u.setEmail("test@whatever.com");
		u.setOId(666);
		u.setPasswd("blblabaldasisteinhash");
		u.setUsername("testUser");
		UserDao.getInstance().insertUser(u);
		
		f = new File();
		f.setOId(666);
		f.setPath("/home/test/blubb");
		f.setUser(u);
		FileDao.getInstance().insertFile(f);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		UserDao daoU = UserDao.getInstance();
		daoU.deleteUser(u.getOId());
		
		FileDao daoF = FileDao.getInstance();
		daoF.deleteFile(f.getOId());
		
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetInstance() {
		FileDao c = FileDao.getInstance();
		assertNotNull(c);
	}
	
	@Test
	public void testGetFile() {
		FileDao dao = FileDao.getInstance();
		
		File newOldFile = dao.getFile(f.getOId());
		assertNotNull(newOldFile);
	}	
	
	@Test
	public void testDeleteFile() 
	{
		FileDao dao = FileDao.getInstance();
		
		dao.deleteFile(f.getOId());
		assertNull(dao.getFile(f.getOId()));
	}
}
