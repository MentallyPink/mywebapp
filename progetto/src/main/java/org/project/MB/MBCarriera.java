package org.project.MB;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.project.EJB.EJBCars;
import org.project.Storage.CompositeCar;
import org.project.Storage.Interfaccia;

@ManagedBean

public class MBCarriera implements Serializable, Interfaccia {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EJBCars ejbCars;

	private CompositeCar selectedCar;
	private String cssClass;
	private String carName;

	public void init() {
		ejbCars = new EJBCars();
	}

	public void choose(int value) {
		this.cssClass = "overlay-Show";
		String nome = "";
		switch (value) {
		case 1:
			nome = "Mazda Rx-8";
			this.carName = "/media/cars/rx8.png";
			break;
		case 2:
			nome = "Chevrolet Camaro SS";
			this.carName = "/media/cars/ccss.png";
			break;
		case 3:
			nome = "Alfa Romeo Brera";
			this.carName = "/media/cars/arb.png";
			break;
		}
		selectedCar = ejbCars.findCarByName(nome);
	}

	public void noCarShow() {
		this.cssClass = "overlay-noShow";
	}

	public void startCarriera() {
		ExternalContext externalContext;
		switch (selectedCar.getNome()) {
		case "Mazda Rx-8":

			externalContext = FacesContext.getCurrentInstance().getExternalContext();

			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("id",
					selectedCar.getEntity().getId());
			try {
				externalContext.redirect(localHost + "Downtown.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;

		case "Alfa Romeo Brera":

			externalContext = FacesContext.getCurrentInstance().getExternalContext();

			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("id",
					selectedCar.getEntity().getId());
			try {
				externalContext.redirect(localHost + "Fortuna.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case "Chevrolet Camaro SS":

			externalContext = FacesContext.getCurrentInstance().getExternalContext();

			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("id",
					selectedCar.getEntity().getId());
			try {
				externalContext.redirect(localHost + "Kempton.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		}
	}

	public CompositeCar getSelectedCar() {
		return selectedCar;
	}

	public void setSelectedCar(CompositeCar selectedCar) {
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
