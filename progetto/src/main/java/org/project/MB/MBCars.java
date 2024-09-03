package org.project.MB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.project.Entities.Carboncar;
import org.project.Enum.EnumGenerico.CarClass;
import org.project.SQL.NativeQueryBuilder;
import org.project.SQL.NativeQueryExecutor;
import org.project.Storage.FilterCar;
import org.project.Storage.Interfaccia;

@ManagedBean
public class MBCars implements Serializable, Interfaccia {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5412524206250151760L;

	private EntityManager em = emf.createEntityManager();
	private EntityTransaction transaction = em.getTransaction();

	private List<Carboncar> lista;

	private FilterCar filter;

	private boolean singleCar;
	private boolean editCar;
	private boolean addCar;

	private Carboncar macchina;

	public void init() {
		filter = new FilterCar();
		lista = new ArrayList<Carboncar>();
	}

	public void getFilteredCars() {
		lista = _getCars();
	}

	public void getUnfilteredCars() {
		_setRefresh();
		lista = _getCars();
	}

	private List<Carboncar> _getCars() {

		NativeQueryBuilder sql = new NativeQueryBuilder();
		sql.append("SELECT * FROM carboncars ");
		sql.append("WHERE 1=1");
		if (filter.getNome() != null && !filter.getNome().equals("") && !filter.getNome().isEmpty())
			sql.append("AND nome like ?", "%" + filter.getNome() + "%");
		if (!CarClass.X.equals(filter.getClasse()) && !filter.getClasse().getCodice().isEmpty())
			sql.append("AND Class like ?", "%" + filter.getClasse().getCodice() + "%");
		if (filter.getTier() > 0 && filter.getTier() < 4)
			sql.append("AND tier = ?", filter.getTier());
		if (filter.getPrezzo() != 0)
			sql.append("AND price >= ?", filter.getPrezzo());
		sql.append("order by tier asc, price asc ");

		_onRefresh();
		NativeQueryExecutor nq = new NativeQueryExecutor(emf.createEntityManager(), sql.toString());

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = nq.getResultList();

		Number v;
		for (Object[] record : resultList) {
			Carboncar car = new Carboncar();
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
			lista.add(car);
		}

		if (lista.size() == 1) {
			this.singleCar = true;
			this.macchina = lista.get(0);
		} else {
			this.singleCar = false;
		}

		return lista;
	}

	public void addCar() {
		em.getTransaction().begin();
		em.persist(macchina);
		transaction.commit();
	}

	public void showEditCar() {
		this.editCar = !this.editCar;
		this.addCar = false;
	}

	public void updateCar() {
		em.getTransaction().begin();
		em.merge(macchina);
		transaction.commit();
		getUnfilteredCars();
		this.editCar = false;
	}

	public void deleteCar() {
		em.getTransaction().begin();
		em.remove(em.find(Carboncar.class, macchina.getId()));
		transaction.commit();
		getUnfilteredCars();
	}

	public void duel() {

		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("id", macchina.getId());

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
		setLista(new ArrayList<Carboncar>());
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
		this.addCar = !this.addCar;
	}

	public List<Carboncar> getLista() {
		return lista;
	}

	public void setLista(List<Carboncar> lista) {
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

	public Carboncar getMacchina() {
		return macchina;
	}

	public void setMacchina(Carboncar macchina) {
		this.macchina = macchina;
		lista.clear();
		lista.add(this.macchina);
		this.singleCar = true;
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
