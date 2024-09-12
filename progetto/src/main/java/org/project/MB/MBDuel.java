package org.project.MB;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.project.EJB.EJBCars;
import org.project.Storage.CompositeBoss;
import org.project.Storage.CompositeCar;
import org.project.Storage.FilterCar;
import org.project.Storage.Interfaccia;

@ManagedBean

public class MBDuel implements Serializable, Interfaccia {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4134813339541327242L;
	
	private EJBCars ejbCars;

	private int id;

	private boolean visible;
	private String cssClass;
	private String image_info;
	private String carToView;
	private String raceCss;
	private String score;
	private String race;
	private String win;

	private CompositeBoss boss;
	private CompositeCar macchinaScelta;
	private CompositeCar enemyCar;

	public void showPopUp(String number) {
		this.cssClass = "overlay-Show";
		String nome = "";

		this.visible = true;
		switch (number) {
		case "1":
			this.image_info = "/media/introBoss/kenji.gif";
			nome = "Kenji";
			break;
		case "2":
			this.image_info = "/media/introBoss/angie.gif";
			nome = "Angie";
			break;
		case "3":
			this.image_info = "/media/introBoss/wolf.gif";
			nome = "Wolf";
			break;
		case "4":
			this.image_info = "/media/introBoss/darius.gif";
			nome = "Darius";
			break;
		}
		
		boss = ejbCars.findBoss(nome);

	}

	public void invisible() {
		this.cssClass = "overlay-noShow";
	}

	public void exitScore() {
		this.raceCss = "overlay-noShow";
	}

	public void duel() {

		invisible();
		this.raceCss = "overlay-Show";
		double winProb = _duel(macchinaScelta, boss.getCar());

		if (winProb >= 65.0) {
			this.win = "Vittoria!";
		} else
			this.win = "Sconfitta..";

	}

	public void init() {
		int id = 0;
		ejbCars = new EJBCars();
		try {
			id = (int) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("id");
			macchinaScelta = ejbCars.findCarById(id);
			carToView = macchinaScelta.getNome();
		} catch (Exception e) {
			id = 42;
			macchinaScelta = ejbCars.findCarById(id);
			carToView = macchinaScelta.getNome();
		}
	}

	public void randomDuel() {
		List<CompositeCar> listaCars = ejbCars.getCarList(new FilterCar());
		this.raceCss = "overlay-Show";
		Random random = new Random();
		enemyCar = listaCars.get(random.nextInt(listaCars.size()));
		double winProb = _duel(macchinaScelta, enemyCar);
		if (winProb >= 65.0) {
			this.win = "Vittoria!";
		} else
			this.win = "Sconfitta..";
	}

	private double _duel(CompositeCar mycar, CompositeCar bosscar) {

		double probVittoria;
		int differenza;
//		prima controllo se le macchina sono identiche
		if (mycar == bosscar) { //fai classe GameUtils, all'interno fai metodo di controllo macchina vs macchina
//			sono identiche
			Random random = new Random();
			int win = random.nextInt(2);
			int gara = random.nextInt(3);
			this.race = null;
			if (gara == 0)
				this.race = "drift";
			if (gara == 1)
				this.race = "sprint";
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
		} else if (mycar.getTier() != bosscar.getTier()) {
			// qui abbiamo tier diversi, va calcolata prob vittoria
			if (mycar.getTier() > bosscar.getTier()) {
				differenza = mycar.getTier() - bosscar.getTier();
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
				differenza = bosscar.getTier() - mycar.getTier();
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

	private double _calcolaProbWin(int differenza, boolean diverse, boolean vantaggio, CompositeCar mycar,
			CompositeCar bosscar) {
		double[] moltiplicatori;
		Random random = new Random();
		int gara = random.nextInt(3);
		this.race = null;
		if (gara == 0)
			this.race = "drift";
		if (gara == 1)
			this.race = "sprint";
		if (gara == 2)
			this.race = "circuit";
		System.out.println("Tipo di tracciato: " + this.race);
		if (diverse) {
			// qui sono diverse
			if (vantaggio) {
				moltiplicatori = _calcolaMoltiplicatori(this.race);
				double myScore = (mycar.getTopSpeed() * moltiplicatori[0] + mycar.getAcceleration() * moltiplicatori[1]
						+ mycar.getHandling() * moltiplicatori[2]) + differenza * 10;
				double enemyScore = (bosscar.getTopSpeed() * moltiplicatori[0]
						+ bosscar.getAcceleration() * moltiplicatori[1] + bosscar.getHandling() * moltiplicatori[2]);
				double winProb = (myScore / (myScore + enemyScore)) * 100;
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
				double enemyScore = (bosscar.getTopSpeed() * moltiplicatori[0]
						+ bosscar.getAcceleration() * moltiplicatori[1] + bosscar.getHandling() * moltiplicatori[2])
						+ differenza * 10;
				double winProb = (myScore / (myScore + enemyScore)) * 100;
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
			double bossScore = (bosscar.getTopSpeed() * 0.3 + bosscar.getAcceleration() * 0.4
					+ bosscar.getHandling() * 0.3);
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
		case "sprint":
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
			externalContext.redirect(localHost + "index.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // redirect alla mia pagina
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

	public CompositeBoss getBoss() {
		return boss;
	}

	public void setBoss(CompositeBoss boss) {
		this.boss = boss;
	}

	public CompositeCar getMacchinaScelta() {
		return macchinaScelta;
	}

	public void setMacchinaScelta(CompositeCar macchina) {
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

	public CompositeCar getEnemyCar() {
		return enemyCar;
	}

	public void setEnemyCar(CompositeCar enemyCar) {
		this.enemyCar = enemyCar;
	}

}
