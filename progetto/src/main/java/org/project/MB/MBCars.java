package org.project.MB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.project.EJB.EJBCars;
import org.project.Enum.EnumGenerico.CarClass;
import org.project.Storage.CompositeCar;
import org.project.Storage.FilterCar;
import org.project.Storage.Interfaccia;

@ManagedBean
public class MBCars implements Serializable, Interfaccia {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5412524206250151760L;

	private List<CompositeCar> lista;

	private FilterCar filter;

	private boolean singleCar;
	private boolean editCar;
	private boolean addCar;

	private CompositeCar selectedCar;

	private EJBCars ejbCars;

	public void init() {
		filter = new FilterCar();
		lista = new ArrayList<CompositeCar>();
		ejbCars = new EJBCars();
	}

	public void getFilteredCars() {
		_getCars();
	}

	public void getUnfilteredCars() {
		_setRefresh();
		_getCars();
	}

	private void _getCars() {
		lista = ejbCars.getCarList(filter);
	}

	public void addCar() {
		selectedCar.refreshEntities();
		ejbCars.addCar(selectedCar.getEntity());
		this.addCar = false;
	}

	public void showEditCar() {
		this.editCar = !this.editCar;
	}

	public void updateCar() {
		selectedCar.refreshEntities();
		ejbCars.updateCar(selectedCar.getEntity());
		getUnfilteredCars();
		this.editCar = false;
	}

	public void deleteCar() {
		selectedCar.refreshEntities();
		ejbCars.deleteCar(selectedCar.getEntity());
		getUnfilteredCars();
	}

	public void duel() {

		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("id", selectedCar.getEntity().getId());

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		try {
			externalContext.redirect(localHost + "duel.xhtml");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void _setRefresh() {
		this.editCar = false;
		this.singleCar = false;
		this.addCar = false;
		filter = new FilterCar();
	}

	private void _onRefresh() {
		setLista(new ArrayList<CompositeCar>());
	}

	public void redirect() {
		_onRefresh();
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext(); // server per la

		// navigazione tra
		// le pagine
		try {
			externalContext.redirect(localHost + "login.xhtml");// redirect alla mia pagina di
																// lista
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void carriera() {
		_onRefresh();
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		try {
			externalContext.redirect(localHost + "carriera.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showInsert() {
		if (this.singleCar) {
			_getCars();
			singleCar = false;
		}

		this.addCar = !this.addCar;
		selectedCar = new CompositeCar();
		this.editCar = false;
	}

	public List<CompositeCar> getLista() {
		return lista;
	}

	public void setLista(List<CompositeCar> lista) {
		this.lista = lista;
	}

	public boolean isSingleCar() {
		return singleCar;
	}

	public void setSingleCar(boolean singleCar) {
		this.singleCar = singleCar;
	}

	public boolean isEditCar() {
		return editCar;
	}

	public void setEditCar(boolean editCar) {
		this.editCar = editCar;
	}

	public CompositeCar getMacchina() {
		return selectedCar;
	}

	public void setMacchina(CompositeCar macchina) {
		this.selectedCar = macchina;
		lista.clear();
		lista.add(this.selectedCar);
		this.singleCar = true;
		this.addCar = false;
	}

	public boolean isAddCar() {
		return addCar;
	}

	public void setAddCar(boolean addCar) {
		this.addCar = addCar;
	}

	public FilterCar getFilter() {
		return filter;
	}

	public void setFilter(FilterCar filter) {
		this.filter = filter;
	}

	public CarClass[] getCarClasses() {
		return CarClass.values();
	}
}
