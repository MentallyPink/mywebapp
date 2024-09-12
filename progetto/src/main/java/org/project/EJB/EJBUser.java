package org.project.EJB;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.project.Entities.User;
import org.project.SQL.NativeQueryBuilder;
import org.project.SQL.NativeQueryExecutor;
import org.project.Storage.CompositeUser;
import org.project.Storage.FilterUser;
import org.project.Storage.Interfaccia;
import org.project.Utilities.StringUtilities;

public class EJBUser implements Serializable, Interfaccia {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("progetto");
	private EntityManager entityManager = emf.createEntityManager();

	public void createUser(CompositeUser user) {
		entityManager.getTransaction().begin();
		entityManager.persist(user.getEntity());
		entityManager.getTransaction().commit();
	}

	public CompositeUser findUser(FilterUser filter) {
		NativeQueryBuilder sql = new NativeQueryBuilder();
		sql.append("SELECT * FROM users");
		sql.append("WHERE 1=1");
		if (!StringUtilities.isEmpty(filter.getEmail()))
			sql.append("AND email = ? ", filter.getEmail());
		if (!StringUtilities.isEmpty(filter.getNome()))
			sql.append("AND nome = ? ", filter.getNome());
		if (!StringUtilities.isEmpty(filter.getCognome()))
			sql.append("AND cognome = ? ", filter.getCognome());
		if (!StringUtilities.isEmpty(filter.getCognome()))
			sql.append("AND password = ? ", filter.getPassword());

		try {
			NativeQueryExecutor nq = new NativeQueryExecutor(emf.createEntityManager(), sql.toString());
			User user = (User) nq.getSingleResult(User.class);;
			return new CompositeUser(user);
		} catch (NoResultException e) {
			System.out.println("credenziali errate");
			return null;
		}

	}
}
