package org.project.MB;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.project.Entities.Carboncar;

@ManagedBean

public class MBKempton implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3351641577859266968L;
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("progetto");
	private EntityManager em = entityManagerFactory.createEntityManager();

	private String[] races = new String[5];
	private Carboncar selectedCar;
	
	
	private void init() {
		int id = 0;
		List<String> words = Arrays.asList("drift", "sprint", "circuit");
		for (int i = 0; i < races.length; i++) {
			races[i] = ""; // init array
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
	
	
	

	public String[] getRaces() {
		return races;
	}

	public void setRaces(String[] races) {
		this.races = races;
	}

	public Carboncar getSelectedCar() {
		return selectedCar;
	}

	public void setSelectedCar(Carboncar selectedCar) {
		this.selectedCar = selectedCar;
	}

}
