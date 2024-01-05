package org.project.MB;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.project.Entities.Carbonboss;
import org.project.Entities.Carboncar;

@ManagedBean

public class MBCarriera implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("progetto");
	private EntityManager em = entityManagerFactory.createEntityManager();

	private Carboncar selectedCar;
	private String cssClass;
	private String carName;

	public void choose(int value) {
		this.cssClass = "overlay-Show";
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Carboncar> criteria = cb.createQuery(Carboncar.class);
		Root<Carboncar> root = criteria.from(Carboncar.class);
		List<Carboncar> listaMacchine;
		switch (value) {
		case 1:
			criteria.select(root);
			listaMacchine = em.createQuery(criteria).getResultList();
			for (Carboncar car : listaMacchine) {
				if (car.getNome().equals("Mazda Rx-8")) {
					selectedCar = em.find(Carboncar.class, car.getId());
					this.carName = "/media/cars/rx8.png";
				}
			}
			System.out.println(selectedCar.getNome());
			break;
		case 2:
			criteria.select(root);
			listaMacchine = em.createQuery(criteria).getResultList();
			for (Carboncar car : listaMacchine) {
				if (car.getNome().equals("Chevrolet Camaro SS")) {
					selectedCar = em.find(Carboncar.class, car.getId());
					this.carName = "/media/cars/ccss.png";
				}
			}
			System.out.println(selectedCar.getNome());
			break;
		case 3:
			criteria.select(root);
			listaMacchine = em.createQuery(criteria).getResultList();
			for (Carboncar car : listaMacchine) {
				if (car.getNome().equals("Alfa Romeo Brera")) {
					selectedCar = em.find(Carboncar.class, car.getId());
					this.carName = "/media/cars/arb.png";
				}
				
			}
			System.out.println(selectedCar.getNome());
			break;
		}
	}
	public void noCarShow() {
		this.cssClass = "overlay-noShow";
	}

	public Carboncar getSelectedCar() {
		return selectedCar;
	}

	public void setSelectedCar(Carboncar selectedCar) {
		this.selectedCar = selectedCar;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
}
