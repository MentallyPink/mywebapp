package org.project.SQL;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.query.NativeQuery;

public class NativeQueryExecutor {
	
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("progetto");
	private EntityManager entityManager = entityManagerFactory.createEntityManager();
	
	private String sql;
	
	public NativeQueryExecutor(EntityManager em, String sql) {
		this.entityManager = em;
		this.sql = sql;
	}
	
	@SuppressWarnings("rawtypes")
	public List getResultList() {
		NativeQuery nq = (NativeQuery) entityManager.createNativeQuery(sql);
		return nq.getResultList();
	}
	
	@SuppressWarnings("rawtypes")
	public List getResultList(Class classe) {
		NativeQuery nq = (NativeQuery) entityManager.createNativeQuery(sql, classe);
		return nq.getResultList();
	}
	
	@SuppressWarnings("rawtypes")
	public Object getSingleResult(Class classe) {
		NativeQuery nq = (NativeQuery) entityManager.createNativeQuery(sql, classe);
		return nq.getSingleResult();
	}

}
