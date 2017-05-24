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
	public void testAddGroup() {
		GroupDao dao = GroupDao.getInstance();
		
		Group g = new Group();
		g.setGroupName("Testgroup");
		g.setOId(1);
		
		dao.addGroup(g);
		Group newOldGroup = dao.getGroup(g.getOId());
		assertNotNull(newOldGroup);
	}
	
	@Test
	public void deleteGroup() {
		GroupDao dao = GroupDao.getInstance();
		
		Group g = new Group();
		g.setGroupName("Testgroup");
		g.setOId(1);
		
		dao.addGroup(g);
		dao.deleteGroup(g.getOId());
		Group newOldGroup = dao.getGroup(g.getOId());
		assertNull(newOldGroup);
	}
}
