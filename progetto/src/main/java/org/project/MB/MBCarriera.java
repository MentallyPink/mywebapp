package org.project.MB;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
					this.selectedCar = em.find(Carboncar.class, car.getId());
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
					this.selectedCar = em.find(Carboncar.class, car.getId());
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
					this.selectedCar = em.find(Carboncar.class, car.getId());
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

	public void startCarriera() {
		ExternalContext externalContext;
		switch (selectedCar.getNome()) {
		case "Mazda Rx-8":

			externalContext = FacesContext.getCurrentInstance().getExternalContext();

			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("id", selectedCar.getId());
			try {
				externalContext.redirect("http://localhost:8080/progetto/faces/Downtown.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;

		case "Alfa Romeo Brera":

			externalContext = FacesContext.getCurrentInstance().getExternalContext();

			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("id", selectedCar.getId());
			try {
				externalContext.redirect("http://localhost:8080/progetto/faces/Fortuna.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case "Chevrolet Camaro SS":

			externalContext = FacesContext.getCurrentInstance().getExternalContext();

			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("id", selectedCar.getId());
			try {
				externalContext.redirect("http://localhost:8080/progetto/faces/Kempton.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		}
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
