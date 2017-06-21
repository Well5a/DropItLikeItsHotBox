package com.dropbox.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.dropbox.dao.DaoManager;

import model.File;
import model.User;


public class FileDao {
	private static EntityManager em;
	private static FileDao singleton;
	private static final String rootPath = "C:\\Users\\David\\workspaceEE\\Dropbox\\DropItLikeItsHotBox\\Dropbox\\files\\";
	
	private FileDao() {
		em = DaoManager.getInstance().getEntityManager();
	}

	public static FileDao getInstance() {
		if (singleton == null) {
			singleton = new FileDao();
		}
		return singleton;
	}
	
	public void createFile(String path, User user)
	{
		File f = new File();
		f.setPath(path);
		f.setUser(user);
		insertFile(f);
	}
	
	public void createDirectory(String path, User user)
	{
		File f = new File();
		f.setPath(path);
		f.setUser(user);
		insertDirectory(f);
	}
	
	public void createFile(String path, User user, byte [] data)
	{
		File f = new File();
		f.setPath(path);
		f.setUser(user);
		insertFile(f, data);
	}
	
	public void persistFileDescriptor(File f)
	{
		em.getTransaction().begin();
		em.persist(f);
		em.getTransaction().commit();
	}
	
	public void insertDirectory(File f)
	{
		java.io.File file = createIoFile(f.getPath());
		if (file.exists())
			return;
		try {
			addDirectoryToFilesystem(file);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		persistFileDescriptor(f);
	}
	
	public void insertFile(File f)
	{
		java.io.File file = createIoFile(f.getPath());
		if (file.exists())
			return;
		try {
			makeDirectories(file);
			addFileToFilesystem(file);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		persistFileDescriptor(f);
	}
	
	public void insertFile(File f, byte [] bytes)
	{
		java.io.File file = createIoFile(f.getPath());
		if (file.exists())
			return;
		try {
			makeDirectories(file);
			addFileToFilesystem(file, bytes);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		persistFileDescriptor(f);
	}
	
	public File getFile(Integer id) {
		return em.find(File.class, id);
	}
	
	public java.io.File getFileFromFilesystem(File f)
	{
		if (em.contains(f))
		{
			return new java.io.File(rootPath + f.getPath());
		}
		else return null;
	}
	
	public void removeFileFromFilesystem(File f)
	{
		if (em.contains(f))
		{
			java.io.File toDelete = new java.io.File(rootPath + f.getPath());
			if(toDelete.exists())
			{
				toDelete.delete();
			}
		}
	}
	
	public File getFileByPath(String path)
	{
		Query q = em.createQuery("SELECT f FROM File f WHERE f.path LIKE :path");
		q.setParameter("path", path);
		return q.getResultList().isEmpty() ? null : (File)q.getResultList().get(0);
	}
	
	
	
	public List<File> getFiles() {
		Query q = em.createQuery("select u from user u");
		List<File> files = q.getResultList();
		return files;
	}
	
	public boolean belongsToUser(String path, model.User user)
	{
		java.io.File homeDir = createIoFile(path);
		
		java.io.File requested = new java.io.File(path);
		while(requested.getParent() != null)
		{
			if (requested.equals(homeDir))
				return true;
			requested = requested.getParentFile();
		}
		return false;
	}
	
	String getHomeDir(String user)
	{
		return rootPath + user;
	}
	
	public void deleteFile(Integer id) {
		
		File f = em.find(File.class, id);
		if (f != null) {
			removeFileFromFilesystem(f);
			em.getTransaction().begin();
			em.remove(f);
			em.getTransaction().commit();
		}
	}
	
	public void deleteFile(File f) {
		
		if (f != null) {
			removeFileFromFilesystem(f);
			em.getTransaction().begin();
			em.remove(f);
			em.getTransaction().commit();
		}
	}
	
	public Integer getMaxId()
	{
		Query q = em.createNativeQuery("SELECT * FROM dropbox.file ORDER BY oId DESC");
		return ((File)(q.getResultList().get(0))).getOId();
	}
	
	private java.io.File createIoFile(String path)
	{
		return new java.io.File(rootPath + path);
	}
	
	private boolean addDirectoryToFilesystem(java.io.File f)
	{
		makeDirectories(f);
		try{
			if (!f.mkdir())
			{
				throw new IllegalStateException("Couldn't create dir: " + f);
			}
		}
		catch( IllegalStateException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean addFileToFilesystem(java.io.File f)
	{
		makeDirectories(f);
		try{
			if (f.isDirectory() && !f.mkdir())
			{
				throw new IllegalStateException("Couldn't create dir: " + f);
			}
			else if(!f.createNewFile())
			{
				throw new IllegalStateException("Couldn't create file: " + f);
			}
		}
		catch( IllegalStateException | IOException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String getFileRoot()
	{
		return rootPath;
	}
	
	private boolean addFileToFilesystem(java.io.File f, byte [] bytes)
	{
		if (!f.exists() && !f.isDirectory())
		{
			try{
				FileOutputStream fos = new FileOutputStream(f);
				makeDirectories(f);
				f.createNewFile();
				fos.write(bytes);
				fos.close();
			}catch(IOException e){
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}
	
	private void makeDirectories(java.io.File f) throws IllegalStateException
	{
		java.io.File parent = f.getParentFile();
		if (!parent.exists() && !parent.mkdirs()) {
		    throw new IllegalStateException("Couldn't create dir: " + parent);
		}
	}
	
}

	