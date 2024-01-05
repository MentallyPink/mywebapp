package org.project.MB;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.project.Entities.Carbonboss;
import org.project.Entities.Carboncar;
import org.project.Storage.BossCar;

@ManagedBean

public class MBDuel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4134813339541327242L;

	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("progetto");
	private EntityManager em = entityManagerFactory.createEntityManager();

	private int id;

	private boolean visible;
	private String cssClass;
	private String image_info;
	private String carToView;
	private String raceCss;
	private String score;
	private String race;
	private String win;

	private Carbonboss boss;
	private Carboncar macchinaScelta;
	private Carboncar enemyCar;
	
	public void showPopUp(String number) {
		this.cssClass = "overlay-Show";

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Carbonboss> criteria = cb.createQuery(Carbonboss.class);
		Root<Carbonboss> root = criteria.from(Carbonboss.class);

		switch (number) {
		case "1":
			System.out.println("ci arriva  1 wooo");
			this.visible = true;
			this.image_info = "/media/introBoss/kenji.gif";
			criteria.select(root).where(cb.equal(root.get("nome"), "Kenji"));
			boss = em.createQuery(criteria).getSingleResult();
			break;
		case "2":
			System.out.println("ci arriva  2 wooo");
			this.visible = true;
			this.image_info = "/media/introBoss/angie.gif";
			criteria.select(root).where(cb.equal(root.get("nome"), "Angie"));
			boss = em.createQuery(criteria).getSingleResult();
			break;
		case "3":
			System.out.println("ci arriva  3 wooo");
			this.visible = true;
			this.image_info = "/media/introBoss/wolf.gif";
			criteria.select(root).where(cb.equal(root.get("nome"), "Wolf"));
			boss = em.createQuery(criteria).getSingleResult();
			break;
		case "4":
			System.out.println("ci arriva  4 wooo");
			this.visible = true;
			this.image_info = "/media/introBoss/darius.gif";
			criteria.select(root).where(cb.equal(root.get("nome"), "Darius"));
			boss = em.createQuery(criteria).getSingleResult();
			break;
		}
	}

	public void invisible() {
		this.cssClass = "overlay-noShow";
	}
	public void exitScore() {
		this.raceCss = "overlay-noShow";
	}

	public void duel() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Carboncar> criteria = cb.createQuery(Carboncar.class);
		Root<Carboncar> root = criteria.from(Carboncar.class);
		criteria.select(root).where(cb.like(root.get("nome"), "%" + boss.getMacchina() + "%"));
		this.enemyCar = em.createQuery(criteria).getSingleResult();
		BossCar bosscar = new BossCar();
		bosscar.setBoss(this.boss);
		bosscar.setMacchina(this.enemyCar);
		invisible();
		this.raceCss = "overlay-Show";
		System.out.println("La mia auto: " + macchinaScelta.toString());
		System.out.println("La loro auto: " + bosscar.getMacchina().toString());
		double winProb = _duel(macchinaScelta, bosscar.getMacchina());
		System.out.println(" Percentuale vittoria: " + winProb);

		if (winProb >= 65.0) {
			this.win = "Vittoria!";
			System.out.println("hai vinto");
		} else
			this.win = "Sconfitta..";
			System.out.println("hai perso");

	}
	
	@PostConstruct
	private void init() {
		System.out.println("qui ci arriva");
		int id = 0;
		try {
			id = (int) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("id");
			macchinaScelta = em.find(Carboncar.class, id);
			carToView = macchinaScelta.toString();
		}catch(Exception e) {
			id = 42;
			macchinaScelta = em.find(Carboncar.class, id);
			carToView = macchinaScelta.toString();
		}
	}

	public void randomDuel() {
		// voglio fare drift, drag race, circuito
		// rispettivamente tuner, muscle, exotic hanno vantaggio
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Carboncar> criteria = cb.createQuery(Carboncar.class);
		Root<Carboncar> root = criteria.from(Carboncar.class);
		List<Integer> listaIdCars = new ArrayList<>();
		List<Carboncar> listaCars;
		criteria.select(root);
		listaCars = em.createQuery(criteria).getResultList();
		this.raceCss = "overlay-Show";
		for (Carboncar macchina : listaCars) {
			listaIdCars.add(macchina.getId());
		}
		Random random = new Random();
		int randomindex = random.nextInt(listaIdCars.size());
		int randomcarId = listaIdCars.get(randomindex);
		this.enemyCar = em.find(Carboncar.class, randomcarId);
		System.out.println("La mia auto" + macchinaScelta.toString());
		System.out.println("La loro auto" + enemyCar.toString());
		double winProb = _duel(macchinaScelta, enemyCar);
		
		System.out.println(" Percentuale vittoria: " + winProb);

		if (winProb >= 65.0) {
			this.win = "Vittoria!";
			System.out.println("hai vinto");
		} else
			this.win = "Sconfitta..";
			System.out.println("hai perso");
	}

	private double _duel(Carboncar mycar, Carboncar bosscar) {

		double probVittoria;
		int differenza;
//		prima controllo se le macchina sono identiche
		if (mycar == bosscar) {
//			sono identiche
			Random random = new Random();
			int win = random.nextInt(2);
			int gara = random.nextInt(3);
			this.race = null;
			if (gara == 0)
				this.race = "drift";
			if (gara == 1)
				this.race = "drag";
			if (gara == 2)
				this.race = "circuit";
			
			if (win == 0) {
				probVittoria = 0;
				this.score = String.format("%.2f", probVittoria);
			}
				
			else {
				probVittoria = 100;
				this.score = String.format("%.2f", probVittoria);
			}
				return probVittoria;
		} else if (Integer.parseInt(mycar.getTier()) != Integer.parseInt(bosscar.getTier())) {
			// qui abbiamo tier diversi, va calcolata prob vittoria
			if (Integer.parseInt(mycar.getTier()) > Integer.parseInt(bosscar.getTier())) {
				differenza = Integer.parseInt(mycar.getTier()) - Integer.parseInt(bosscar.getTier());
				if (differenza == 1) {
					probVittoria = _calcolaProbWin(differenza, true, true, mycar, bosscar);
					this.score = String.format("%.2f", probVittoria);
					return probVittoria;
				} else if (differenza == 2) {
					probVittoria = _calcolaProbWin(differenza, true, true, mycar, bosscar);
					this.score = String.format("%.2f", probVittoria);
					return probVittoria;
				} else {
					// se arrivi qui, qualcosa andato storto in calcolo diff
					return 0;
				}

			} else {
				differenza = Integer.parseInt(bosscar.getTier()) - Integer.parseInt(mycar.getTier());
				if (differenza == 1) {
					probVittoria = _calcolaProbWin(differenza, true, false, mycar, bosscar);
					this.score = String.format("%.2f", probVittoria);
					return probVittoria;
				} else if (differenza == 2) {
					probVittoria = _calcolaProbWin(differenza, true, false, mycar, bosscar);
					this.score = String.format("%.2f", probVittoria);
					return probVittoria;
				} else {
					// se arrivi qui, qualcosa andato storto in calcolo diff
					return 0;
				}
			}

		} else {
			// qui tier uguale, niente di diverso
			differenza = 0;
			probVittoria = _calcolaProbWin(differenza, false, false, mycar, bosscar);
			this.score = String.format("%.2f", probVittoria);
			return probVittoria;
		}

	}

	private double _calcolaProbWin(int differenza, boolean diverse, boolean vantaggio, Carboncar mycar,
			Carboncar enemyCar) {
		double[] moltiplicatori;
		Random random = new Random();
		int gara = random.nextInt(3);
		this.race = null;
		if (gara == 0)
			this.race = "drift";
		if (gara == 1)
			this.race = "drag";
		if (gara == 2)
			this.race = "circuit";
		System.out.println("Tipo di tracciato: " + this.race);
		if (diverse) {
			// qui sono diverse
			if (vantaggio) {
				moltiplicatori = _calcolaMoltiplicatori(this.race);
				double myScore = (mycar.getTopSpeed() * moltiplicatori[0] + mycar.getAcceleration() * moltiplicatori[1]
						+ mycar.getHandling() * moltiplicatori[2]) + differenza * 10;
				double enemyScore = (enemyCar.getTopSpeed() * moltiplicatori[0]
						+ enemyCar.getAcceleration() * moltiplicatori[1] + enemyCar.getHandling() * moltiplicatori[2]);
				double winProb = (myScore / (myScore + enemyScore)) *100;
				int enemyschianto = random.nextInt(10);
				if (enemyschianto == 9) {
					winProb += 20;
				}
				int myschianto = random.nextInt(10);
				if (myschianto == 9) {
					winProb -= 20;
				}
				if (winProb > 100)
					winProb = 100;
				return winProb;
			} else {
					moltiplicatori = _calcolaMoltiplicatori(this.race);
					double myScore = (mycar.getTopSpeed() * moltiplicatori[0] + mycar.getAcceleration() * moltiplicatori[1]
							+ mycar.getHandling() * moltiplicatori[2]);
					double enemyScore = (enemyCar.getTopSpeed() * moltiplicatori[0]
							+ enemyCar.getAcceleration() * moltiplicatori[1] + enemyCar.getHandling() * moltiplicatori[2])+ differenza * 10;
					double winProb = (myScore / (myScore + enemyScore)) *100;
					int enemyschianto = random.nextInt(10);
					if (enemyschianto == 9) {
						winProb += 20;
					}
					int myschianto = random.nextInt(10);
					if (myschianto == 9) {
						winProb -= 20;
					}
					if (winProb > 100)
						winProb = 100;
					return winProb;
			}
		} else {
			double myScore = (mycar.getTopSpeed() * 0.3 + mycar.getAcceleration() * 0.4 + mycar.getHandling() * 0.3);
			double bossScore = (enemyCar.getTopSpeed() * 0.3 + enemyCar.getAcceleration() * 0.4
					+ enemyCar.getHandling() * 0.3);
			double winProb = (myScore / (myScore + bossScore)) * 100;
			int enemyschianto = random.nextInt(10);
			if (enemyschianto == 9) {
				winProb += 20;
			}
			int myschianto = random.nextInt(10);
			if (myschianto == 9) {
				winProb -= 20;
			}
			if (winProb > 100)
				winProb = 100;
			return winProb;
		}
	}

	private double[] _calcolaMoltiplicatori(String gara) {
		double topSpeedMolt = 0;
		double accelMolt = 0;
		double handlingdMolt = 0;
		double[] moltiplicatori = new double[3];
		switch (gara) {
		case "drift":
			topSpeedMolt = 0.1;
			accelMolt = 0.3;
			handlingdMolt = 0.6;
			moltiplicatori[0] = topSpeedMolt;
			moltiplicatori[1] = accelMolt;
			moltiplicatori[2] = handlingdMolt;
			break;
		case "drag":
			topSpeedMolt = 0.4;
			accelMolt = 0.6;
			handlingdMolt = 0;
			moltiplicatori[0] = topSpeedMolt;
			moltiplicatori[1] = accelMolt;
			moltiplicatori[2] = handlingdMolt;
			break;
		case "circuit":
			topSpeedMolt = 0.3;
			accelMolt = 0.4;
			handlingdMolt = 0.3;
			moltiplicatori[0] = topSpeedMolt;
			moltiplicatori[1] = accelMolt;
			moltiplicatori[2] = handlingdMolt;
			break;
		}

		return moltiplicatori;
	}
	
	public void redirectIndex() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		try {
			externalContext.redirect("http://localhost:8080/progetto/faces/index.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// redirect alla mia pagina
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getImage_info() {
		return image_info;
	}

	public void setImage_info(String image_info) {
		this.image_info = image_info;
	}

	public Carbonboss getBoss() {
		return boss;
	}

	public void setBoss(Carbonboss boss) {
		this.boss = boss;
	}

	public Carboncar getMacchinaScelta() {
		return macchinaScelta;
	}

	public void setMacchinaScelta(Carboncar macchina) {
		this.macchinaScelta = macchina;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCarToView() {
		return carToView;
	}

	public void setCarToView(String carToView) {
		this.carToView = carToView;
	}

	public String getRaceCss() {
		return raceCss;
	}

	public void setRaceCss(String raceCss) {
		this.raceCss = raceCss;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getWin() {
		return win;
	}

	public void setWin(String win) {
		this.win = win;
	}

	public Carboncar getEnemyCar() {
		return enemyCar;
	}

	public void setEnemyCar(Carboncar enemyCar) {
		this.enemyCar = enemyCar;
	}

}
