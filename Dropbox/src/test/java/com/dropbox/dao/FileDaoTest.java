package com.dropbox.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.File;
import model.User;
import com.dropbox.PrepareTests;

public class FileDaoTest {

	private static User user = null;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//PrepareTests.initDatabase();
		User user = new User();
		user.setOId(5);
		user.setEmail("testmail@test.de");
		user.setPasswd("test");
		user.setUsername("testUser5");
		UserDao.getInstance().insertUser(user);
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
		FileDao c = FileDao.getInstance();
		assertNotNull(c);
	}
	
	@Test
	public void testGetFile() {
		FileDao dao = FileDao.getInstance();
		File file = new File();
		
		file.setOId(1);
		file.setPath("/home/test/blubb");
		file.setUser(user);
		
		dao.insertFile(file);
		File newOldFile = dao.getFile(file.getOId());
		assertNotNull(newOldFile);
	}
	
	@Test
	public void testInsertFile() {
		FileDao dao = FileDao.getInstance();
		File file = new File();
		file.setOId(1);
		file.setPath("/home/test/blubb");
		
		dao.insertFile(file);
		File newOldFile = dao.getFile(file.getOId());
		assertNotNull(newOldFile);
	}
	
	@Test
	public void testDeleteFile() 
	{
		File file = new File();
		FileDao dao = FileDao.getInstance();
		
		file.setOId(1);
		file.setPath("/home/test/blubb");
		
		dao.insertFile(file);
		dao.deleteFile(file.getOId());
		assertNull(dao.getFile(file.getOId()));
	}
}
