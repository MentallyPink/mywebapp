package org.project.MB;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.project.Entities.Carboncar;

@ManagedBean
public class MBTerritorio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("progetto");
	private EntityManager em = entityManagerFactory.createEntityManager();
	
	private Carboncar selectedCar;

	@PostConstruct
	private void init() {
		int id = 0;
		try {
			id = (int) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("id");
			selectedCar = em.find(Carboncar.class, id);
		}catch(Exception e) {
			id = 42;
			selectedCar = em.find(Carboncar.class, id);
		}
	}

	public Carboncar getSelectedCar() {
		return selectedCar;
	}

	public void setSelectedCar(Carboncar selectedCar) {
		this.selectedCar = selectedCar;
	}
	
}
