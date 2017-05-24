package com.dropbox.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Group;
import com.dropbox.PrepareTests;

public class GroupDaoTest {
	
	public static Group g = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//PrepareTests.initDatabase();
		g = new Group();
		g.setGroupName("Testgroup");
		g.setOId(666);
		
		GroupDao.getInstance().addGroup(g);
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		GroupDao dao = GroupDao.getInstance();
		dao.deleteGroup(g.getOId());
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetGroup() {
		GroupDao dao = GroupDao.getInstance();
		
		Group newOldGroup = dao.getGroup(g.getOId());
		assertNotNull(newOldGroup);
	}
	
	@Test
	public void testDeleteGroup() {
		GroupDao dao = GroupDao.getInstance();
				
		dao.deleteGroup(g.getOId());
		Group newOldGroup = dao.getGroup(g.getOId());
		assertNull(newOldGroup);
	}
}
