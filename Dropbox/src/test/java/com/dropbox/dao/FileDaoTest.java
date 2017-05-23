package com.dropbox.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import model.File;

public class FileDaoTest {

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
