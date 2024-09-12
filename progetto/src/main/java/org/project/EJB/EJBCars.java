package org.project.EJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.project.Entities.Carbonboss;
import org.project.Entities.Carboncar;
import org.project.Enum.EnumGenerico.CarClass;
import org.project.SQL.NativeQueryBuilder;
import org.project.SQL.NativeQueryExecutor;
import org.project.Storage.CompositeBoss;
import org.project.Storage.CompositeCar;
import org.project.Storage.FilterCar;
import org.project.Storage.Interfaccia;

public class EJBCars implements Serializable, Interfaccia{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EntityManagerFactory emf = Persistence.createEntityManagerFactory("progetto");
	private EntityManager entityManager = emf.createEntityManager();

	public void deleteCar(Carboncar car) {
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(Carboncar.class, car.getId()));
		entityManager.getTransaction().commit();
	}
	
	public void updateCar(Carboncar car) {
		entityManager.getTransaction().begin();
		entityManager.merge(car);
		entityManager.getTransaction().commit();
	}
	
	public void addCar(Carboncar car) {
		entityManager.getTransaction().begin();
		entityManager.persist(car);
		entityManager.getTransaction().commit();
	}
	
	public List<CompositeCar> getCarList(FilterCar filter){
		List<CompositeCar> toReturn = new ArrayList<CompositeCar>();
		NativeQueryBuilder sql = new NativeQueryBuilder();
		sql.append("SELECT * FROM carboncars ");
		sql.append("WHERE 1=1");
		if (filter.getNome() != null && !filter.getNome().equals("") && !filter.getNome().isEmpty())
			sql.append("AND nome like ?", "%" + filter.getNome() + "%");
		if (!CarClass.X.equals(filter.getClasse()) && !filter.getClasse().getCodice().isEmpty())
			sql.append("AND Class like ?", "%" + filter.getClasse().getCodice() + "%");
		if (filter.getTier() != 0)
			sql.append("AND tier = ?", filter.getTier());
		if (filter.getPrezzo() != 0)
			sql.append("AND price >= ?", filter.getPrezzo());
		sql.append("order by tier asc, price asc ");

		NativeQueryExecutor nq = new NativeQueryExecutor(emf.createEntityManager(), sql.toString());

		@SuppressWarnings("unchecked")
		List<Carboncar> resultList = nq.getResultList(Carboncar.class);

		for (Carboncar car : resultList) {
			CompositeCar c = new CompositeCar(car);
			toReturn.add(c);
		}

		return toReturn;
	}
	
	public CompositeCar findCarById(int id) {
		Carboncar car = entityManager.find(Carboncar.class, id);
		return new CompositeCar(car);
	}
	
	public CompositeCar findCarByName(String name) {
		NativeQueryBuilder sql = new NativeQueryBuilder();
		sql.append("SELECT * FROM carboncars");
		sql.append("WHERE 1=1");
		sql.append("AND nome like ?", "%" + name + "%");
		
		NativeQueryExecutor nq = new NativeQueryExecutor(emf.createEntityManager(), sql.toString());
		Carboncar car = (Carboncar) nq.getSingleResult(Carboncar.class);
		return new CompositeCar(car);
	}
	
	public CompositeBoss findBoss(String name) {
		NativeQueryBuilder sql = new NativeQueryBuilder();
		sql.append("SELECT * FROM carbonboss");
		sql.append("WHERE 1=1");
		sql.append("AND nome like %?%", name);
		
		NativeQueryExecutor nq = new NativeQueryExecutor(emf.createEntityManager(), sql.toString());
		Carbonboss boss = (Carbonboss) nq.getSingleResult(Carbonboss.class);
		
		CompositeCar car = findCarByName(boss.getMacchina());
		
		return new CompositeBoss(boss, car);
	}
}
