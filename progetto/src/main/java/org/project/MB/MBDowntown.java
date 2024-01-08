package org.project.MB;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.project.Entities.Carboncar;

@ManagedBean
public class MBDowntown implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("progetto");
	private EntityManager em = entityManagerFactory.createEntityManager();

	private Carboncar selectedCar;
	private String cssClass;
	private String[] races = new String[5];
	
	@ManagedProperty(value="#{mbduel}")
	private MBDuel mbduel;

	@PostConstruct
	private void init() {
		int id = 0;
		List<String> words = Arrays.asList("drift", "sprint", "circuit");
		for (int i = 0; i < races.length; i++) {
			races[i] = ""; //init array
		}
		int[] counts = new int[words.size()];
		for (int i = 0; i < 5; i++) {
			int index;
			do {
				index = (int) (Math.random() * words.size());
			} while (counts[index] >= 2);

			counts[index]++;
			int emptyIndex = findEmptyIndex(races);
			races[emptyIndex] = words.get(index);
		}

		try {
			id = (int) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("id");
			selectedCar = em.find(Carboncar.class, id);
		} catch (Exception e) {
			id = 42;
			selectedCar = em.find(Carboncar.class, id);
		}
	}

	private static int findEmptyIndex(String[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals("")) {
				return i;
			}
		}
		return -1;
	}

	public void exit() {
		this.cssClass = "overlay-noShow";
	}

	public void show() {
		this.cssClass = "overlay-Show";
	}
	public void bossDuel() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Carboncar> criteria = cb.createQuery(Carboncar.class);
		Root<Carboncar> root = criteria.from(Carboncar.class);
		Carboncar enemyCar = new Carboncar();
		criteria.select(root).where(cb.equal(root.get("nome"), "Mazda RX-7"));
		enemyCar = em.createQuery(criteria).getSingleResult();
		mbduel._duel(selectedCar, enemyCar);
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

	public String[] getRaces() {
		return races;
	}

	public void setRaces(String[] races) {
		this.races = races;
	}

	public MBDuel getMbduel() {
		return mbduel;
	}

	public void setMbduel(MBDuel mbduel) {
		this.mbduel = mbduel;
	}

}
