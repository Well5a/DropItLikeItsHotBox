package com.dropbox.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DaoManager {
	private static DaoManager dm;
	private static EntityManager em;
	
	private static final String PERSISTENCE_UNIT_NAME = "DropBox";
	private static EntityManagerFactory factory;

	private DaoManager() {
	    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    em = factory.createEntityManager();
	}

	public static DaoManager getInstance() {
		if (dm == null) {
			dm = new DaoManager();
		}
		return dm;
	}
	
	public EntityManager getEntityManager() {
		return em;
	}
}
