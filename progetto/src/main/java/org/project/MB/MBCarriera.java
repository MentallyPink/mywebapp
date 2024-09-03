package org.project.MB;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.project.Entities.Carboncar;
import org.project.SQL.NativeQueryBuilder;
import org.project.SQL.NativeQueryExecutor;
import org.project.Storage.Interfaccia;

@ManagedBean

public class MBCarriera implements Serializable, Interfaccia {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Carboncar selectedCar;
	private String cssClass;
	private String carName;

	public void choose(int value) {
		this.cssClass = "overlay-Show";
		
		NativeQueryBuilder sql = new NativeQueryBuilder();
		sql.append("SELECT * from carboncars");
		sql.append("WHERE 1=1");
		switch(value) {
		case 1:
			sql.append("AND nome = 'Mazda Rx-8'");
			this.carName = "/media/cars/rx8.png";
			break;
		case 2:
			sql.append("AND nome = 'Chevrolet Camaro SS'");
			this.carName = "/media/cars/ccss.png";
			break;
		case 3:
			sql.append("AND nome = 'Alfa Romeo Brera'");
			this.carName = "/media/cars/arb.png";
			break;
		}
		NativeQueryExecutor nq = new NativeQueryExecutor(emf.createEntityManager(), sql.toString());
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = nq.getResultList();
		
		Number v;
		Carboncar car = new Carboncar();
		for (Object[] record : resultList) {
			v = (Number) record[0];
			car.setId(v.intValue());
			car.setClass_((String) record[1]);
			car.setNome((String) record[2]);
			v = (Number) record[3];
			car.setPrice(v.intValue());
			v = (Number) record[4];
			car.setTier(v.intValue());
			v = (Number) record[5];
			car.setTopSpeed(v.floatValue());
			v = (Number) record[6];
			car.setAcceleration(v.floatValue());
			v = (Number) record[7];
			car.setHandling(v.floatValue());
		}
		selectedCar = car;
			System.out.println(selectedCar.getNome());
			
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
				externalContext.redirect(localHost + "Downtown.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;

		case "Alfa Romeo Brera":

			externalContext = FacesContext.getCurrentInstance().getExternalContext();

			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("id", selectedCar.getId());
			try {
				externalContext.redirect(localHost + "Fortuna.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case "Chevrolet Camaro SS":

			externalContext = FacesContext.getCurrentInstance().getExternalContext();

			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("id", selectedCar.getId());
			try {
				externalContext.redirect(localHost + "Kempton.xhtml");
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
