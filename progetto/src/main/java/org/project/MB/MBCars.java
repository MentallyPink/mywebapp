package org.project.MB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.project.Entities.Carboncar;
import org.project.Entities.Money;
import org.project.SQL.SQLNativeBuilder;
import org.project.SQL.SQLNativeExecutor;
import org.project.Storage.Interfaccia;

@ManagedBean
public class MBCars implements Serializable, Interfaccia {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5412524206250151760L;

	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("progetto");
	private EntityManager em = entityManagerFactory.createEntityManager();
	private EntityTransaction transaction = em.getTransaction();

	private List<Carboncar> lista;
	private String nome;
	private String classe;
	private int id;
	private int price;
	private int tier;

	private String nomeFiltro;
	private String classeFiltro;
	private int priceFiltro;
	private int tierFiltro;

	private boolean singleCar;
	private boolean editCar;
	private boolean addCar;

	private Money money;
	private String soldi;

	private Carboncar macchina;

	@ManagedProperty(value = "#{MBDuel}")
	private MBDuel mbduel;

	public List<Carboncar> getAllCars(boolean cercaId) {

		try {
			lista = new ArrayList<Carboncar>();
			SQLNativeBuilder sql = new SQLNativeBuilder();
			sql.append("SELECT * FROM carboncars");
			sql.append("WHERE 1=1");

			SQLNativeExecutor nq = new SQLNativeExecutor(emf.createEntityManager(), sql.toString());

			@SuppressWarnings("unchecked")
			List<Object[]> resultList = nq.getResultList();

			Number v;
			for (Object[] record : resultList) {
				Carboncar car = new Carboncar();
				car.setClass_((String) record[1]);
				car.setNome((String) record[2]);
				car.setPrice((String) record[3]);
				car.setTier((String) record[4]);
				v = (Number) record[5];
				car.setTopSpeed(v.floatValue());
				v = (Number) record[6];
				car.setAcceleration(v.floatValue());
				v = (Number) record[7];
				car.setHandling(v.floatValue());
				lista.add(car);
			}
//			lista.addAll(resultList);
			_setRefresh();

//			CriteriaBuilder cb = em.getCriteriaBuilder();
//			CriteriaQuery<Carboncar> criteria = cb.createQuery(Carboncar.class);
//			Root<Carboncar> root = criteria.from(Carboncar.class);
//			_setRefresh();
//			criteria.select(root);
//			lista = em.createQuery(criteria).getResultList();

			return lista;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<Carboncar> ricercaConFiltri() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Carboncar> criteria = cb.createQuery(Carboncar.class);
		Root<Carboncar> root = criteria.from(Carboncar.class);
		Predicate condizioni = cb.conjunction();
		if (!(this.nomeFiltro.equals("") && this.nomeFiltro.isEmpty())) {
			Predicate byName = cb.like(root.get("nome"), "%" + nomeFiltro + "%");
			condizioni = cb.and(condizioni, byName);
		}
		if (!(this.classeFiltro.equals("") && this.classeFiltro.isEmpty())) {
			Predicate byClass = cb.equal(root.get("class_"), classeFiltro);
			condizioni = cb.and(condizioni, byClass);
		}
		if (this.tierFiltro > 0 && this.tierFiltro < 4) {
			Predicate byTier = cb.equal(root.get("tier"), tierFiltro);
			condizioni = cb.and(condizioni, byTier);
		}
		if (this.priceFiltro != 0) {
			Predicate byPrice = cb.ge(root.get("price").as(Number.class), priceFiltro);
			condizioni = cb.and(condizioni, byPrice);
			criteria.select(root).where(condizioni);
			criteria.orderBy(cb.desc(root.get("price")));
		} else {
			criteria.select(root).where(condizioni);
			criteria.orderBy(cb.asc(root.get("id")));
		}
		_onRefresh();
		lista = em.createQuery(criteria).getResultList();
		if (lista.size() == 1) {
			this.singleCar = true;
		} else {
			this.singleCar = false;
		}

		return lista;
	}

	public void addCar() {
		em.getTransaction().begin();
		Carboncar cu = new Carboncar();
		cu.setNome(nome);
		cu.setClass_(classe);
		cu.setPrice(String.valueOf(price));
		cu.setTier(String.valueOf(tier));
		em.persist(cu);
		transaction.commit();
	}

	public void showEditCar() {
		this.editCar = !this.editCar;
		this.addCar = false;
		this.id = lista.get(0).getId();
		this.nome = lista.get(0).getNome();
		this.price = Integer.parseInt(lista.get(0).getPrice());
		this.tier = Integer.parseInt(lista.get(0).getTier());
		this.classe = lista.get(0).getClass_();
	}

	public void updateCar() {
		this.singleCar = false;
		em.getTransaction().begin();
		Carboncar c1 = new Carboncar();
		c1 = em.find(Carboncar.class, this.id);
		c1.setNome(this.nome);
		c1.setClass_(this.classe);
		c1.setPrice(String.valueOf(this.price));
		c1.setTier(String.valueOf(this.tier));
		em.persist(c1);
		transaction.commit();
		this.editCar = false;
	}

	public void deleteCar() {
		em.getTransaction().begin();
		this.id = lista.get(0).getId();
		Carboncar c = em.find(Carboncar.class, this.id);
		em.remove(c);
		transaction.commit();
	}

	public void duel() {
		this.id = lista.get(0).getId();
		if (lista == null) {
			this.id = 42;
		}
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("id", id);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		try {
			externalContext.redirect(localHost + "duel.xhtml");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void _setRefresh() {
		this.nome = null;
		this.classe = null;
		this.price = 0;
		this.id = 0;
		this.tier = 0;
		this.editCar = false;
		this.singleCar = false;
		this.nomeFiltro = "";
		this.classeFiltro = "";
		this.tierFiltro = 0;
		this.priceFiltro = 0;
		this.addCar = false;
	}

	private void _onRefresh() {
		setLista(null);
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
			// TODO Auto-generated catch block
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

	public Money moneyAmount() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Money> criteria = cb.createQuery(Money.class);
		Root<Money> root = criteria.from(Money.class);
		money = em.createQuery(criteria).getSingleResult();
		return money;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
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

	public String getNomeFiltro() {
		return nomeFiltro;
	}

	public void setNomeFiltro(String nomeFiltro) {
		this.nomeFiltro = nomeFiltro;
	}

	public String getClasseFiltro() {
		return classeFiltro;
	}

	public void setClasseFiltro(String classeFiltro) {
		this.classeFiltro = classeFiltro;
	}

	public int getPriceFiltro() {
		return priceFiltro;
	}

	public void setPriceFiltro(int priceFiltro) {
		this.priceFiltro = priceFiltro;
	}

	public int getTierFiltro() {
		return tierFiltro;
	}

	public void setTierFiltro(int tierFiltro) {
		this.tierFiltro = tierFiltro;
	}

	public Money getMoney() {
		return money;
	}

	public void setMoney(Money money) {
		this.money = money;
	}

	public String getSoldi() {
		return soldi;
	}

	public void setSoldi(String soldi) {
		this.soldi = soldi;
	}

	public void moneyForView() {
		soldi = String.valueOf(this.money.getMoney());
	}

	public Carboncar getMacchina() {
		return macchina;
	}

	public void setMacchina(Carboncar macchina) {
		this.macchina = macchina;
	}

	public boolean isAddCar() {
		return addCar;
	}

	public void setAddCar(boolean addCar) {
		this.addCar = addCar;
	}
}
