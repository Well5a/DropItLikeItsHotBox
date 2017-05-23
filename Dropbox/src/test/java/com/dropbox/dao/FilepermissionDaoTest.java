package com.dropbox.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import model.File;
import model.Filepermission;
import model.User;

public class FilepermissionDaoTest {

	@Test
	public void testGetFilepermission() {
		FilepermissionDao dao = FilepermissionDao.getInstance();
		Filepermission fp = new Filepermission();
		
		fp.setOid(1);
		fp.setAllowRead((byte)1);
		fp.setAllowWrite((byte)0);
		
		User u = new User();
		u.setEmail("test@whatever.com");
		u.setOId(1);
		u.setPasswd("blblabaldasisteinhash");
		
		File f = new File();
		f.setOId(1);
		f.setPath("/home/test/blubb");
		f.setUser(u);
		fp.setUser(u);
		fp.setFile(f);
		
		dao.insertFilepermission(fp);
		
		Filepermission newOldFp = dao.getFilepermission(fp.getOid());
		assertNotNull(newOldFp);
	}
}
