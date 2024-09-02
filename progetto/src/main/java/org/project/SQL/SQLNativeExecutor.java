package org.project.SQL;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.query.NativeQuery;

public class SQLNativeExecutor {
	
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("progetto");
	private EntityManager entityManager = entityManagerFactory.createEntityManager();
	
	private String sql;
	
	public SQLNativeExecutor(EntityManager em, String sql) {
		this.entityManager = em;
		this.sql = sql;
	}
	
	@SuppressWarnings("rawtypes")
	public List getResultList() {
		NativeQuery nq = (NativeQuery) entityManager.createNativeQuery(sql);
		return nq.getResultList();
	}
}
