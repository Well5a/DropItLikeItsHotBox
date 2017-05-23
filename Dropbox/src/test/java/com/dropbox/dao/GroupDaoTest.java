package com.dropbox.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Group;

public class GroupDaoTest {

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
