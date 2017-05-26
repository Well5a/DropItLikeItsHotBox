package com.dropbox.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.File;
import model.Filepermission;
import model.User;

public class FilepermissionDaoTest {
	
	private static User u = null;
	private static File f = null;
	private static Filepermission p = null;

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
		
		p = new Filepermission();
		p.setOId(666);
		p.setAllowRead((byte) 1);
		p.setAllowWrite((byte) 1);
		p.setFile(f);
		p.setUser(u);
		FilepermissionDao.getInstance().insertFilepermission(p);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		FilepermissionDao daoP = FilepermissionDao.getInstance();
		daoP.deleteFilepermission(p.getOId());
		
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
		FilepermissionDao c = FilepermissionDao.getInstance();
		assertNotNull(c);
	}
	
	@Test
	public void testGetFilepermission() {
		FilepermissionDao dao = FilepermissionDao.getInstance();		
			
		Filepermission newOldFp = dao.getFilepermission(p.getOId());
		assertNotNull(newOldFp);
	}
	
	@Test
	public void testDeleteFilepermission() 
	{
		FilepermissionDao dao = FilepermissionDao.getInstance();		
		
		dao.deleteFilepermission(p.getOId());
		assertNull(dao.getFilepermission(p.getOId()));
	}
}
