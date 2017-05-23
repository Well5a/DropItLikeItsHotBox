package com.dropbox.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Group;
import model.User;
import model.Usergroup;

public class UsergroupDaoTest {

	@Test
	public void testInsertUsergroup() {
		UsergroupDao dao = UsergroupDao.getInstance();
		
		Usergroup ug = new Usergroup();
		
		Group g = new Group();
		g.setGroupName("Testgroup");
		g.setOId(1);
		

		User u = new User();
		u.setEmail("test@whatever.com");
		u.setOId(1);
		u.setPasswd("blblabaldasisteinhash");
		
		ug.setOId(1);
		ug.setGroup(g);
		ug.setUser(u);
	
		dao.insertUsergroup(ug);
		Usergroup newOldUg = dao.getUsergroup(ug.getOId());
		assertNotNull(newOldUg);
	}
	
	@Test
	public void testDeleteUsergroup() {
		UsergroupDao dao = UsergroupDao.getInstance();
		
		Usergroup ug = new Usergroup();
		
		Group g = new Group();
		g.setGroupName("Testgroup");
		g.setOId(1);
		

		User u = new User();
		u.setEmail("test@whatever.com");
		u.setOId(1);
		u.setPasswd("blblabaldasisteinhash");
		
		ug.setOId(1);
		ug.setGroup(g);
		ug.setUser(u);
	
		dao.insertUsergroup(ug);
		dao.deleteUsergroup(ug.getOId());
		Usergroup newOldUg = dao.getUsergroup(ug.getOId());
		
		assertNull(newOldUg);
	}
}
