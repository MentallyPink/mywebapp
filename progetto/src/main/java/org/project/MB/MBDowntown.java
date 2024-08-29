package org.project.MB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.project.Entities.Carboncar;
import org.project.Enum.EnumGenerico;
import org.project.Storage.Gara;


@ManagedBean
public class MBDowntown implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("progetto");
	private EntityManager em = entityManagerFactory.createEntityManager();

	private Carboncar selectedCar;
	private String cssClass;
	private String[] races = new String[5];
	private String bossCss;
	private String raceState;

	private int countWin;
	private String race;
	private String score;
	private String win;
	private boolean fromWin;
	private String[] racers = new String[5];
	
	private Gara[] racess = {new Gara(), new Gara(), new Gara(), new Gara(), new Gara()};
 
	Map<Double, String> carMap = new LinkedHashMap<>();

	
	private void init() {
		int id = 0;
		List<String> words = Arrays.asList("drift", "sprint", "circuit");

		int[] counts = new int[words.size()];
		for (int i = 0; i < 5; i++) {
			int index;
			do {
				index = (int) (Math.random() * words.size());
			} while (counts[index] >= 2);

			counts[index]++;
			int emptyIndex = findEmptyIndex(racess);
			racess[emptyIndex].setTracciato(words.get(index));
		}

		try {
			id = (int) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("id");
			selectedCar = em.find(Carboncar.class, id);
		} catch (Exception e) {
			id = 42;
			selectedCar = em.find(Carboncar.class, id);
		}
	}

	private static int findEmptyIndex(Gara[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].getTracciato().equals("")) {
				return i;
			}
		}
		return -1;
	}

	public void exit() {
		this.cssClass = "overlay-noShow";
		this.bossCss = "boss-noShow";
	}

	public void show() {
		this.cssClass = "overlay-Show";
		carMap.clear();
	}

	public void bossDuel() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Carboncar> criteria = cb.createQuery(Carboncar.class);
		Root<Carboncar> root = criteria.from(Carboncar.class);
		Carboncar enemyCar = new Carboncar();
		criteria.select(root).where(cb.equal(root.get("nome"), "Mazda RX-7"));
		enemyCar = em.createQuery(criteria).getSingleResult();
		double winProb = _duel(selectedCar, enemyCar);
		if (winProb >= 50.0) {
			this.win = "Vittoria!";
			System.out.println("hai vinto");
		} else
			this.win = "Sconfitta..";
		System.out.println("hai perso");
	}

	public void race() {
		boolean vittoria = _race(this.fromWin);
		if (vittoria)
			this.countWin++;
	}

	private boolean _race(boolean fromWin) {
		// logica di duel, ma calcola punteggi per piu macchine
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Carboncar> criteria = cb.createQuery(Carboncar.class);
		Root<Carboncar> root = criteria.from(Carboncar.class);
		Predicate condizioni = cb.conjunction();
		boolean win;

		EnumSet<EnumGenerico> allNames = EnumSet.allOf(EnumGenerico.class);
		List<EnumGenerico> racerName = new ArrayList<>(allNames);
		Collections.shuffle(racerName);
		if (fromWin == false) {
			Predicate tier = cb.equal(root.get("tier"), "1");
			Predicate class_ = cb.equal(root.get("class_"), "tuner");
			condizioni = cb.and(tier, class_);
			criteria.select(root).where(condizioni);
			List<Carboncar> listaMacchine = em.createQuery(criteria).getResultList();
			List<Carboncar> macchineRace = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				Random random = new Random();
				int randomindex = random.nextInt(listaMacchine.size());
				Carboncar car = listaMacchine.get(randomindex);
				if(i != 4) {
					macchineRace.add(car);
				}
				
				this.racers[i] = racerName.get(i).toString();

			}
			this.racers[racers.length - 1] = "You";
			macchineRace.add(selectedCar);
			win = _calcRaceWinRate(fromWin, macchineRace, carMap);
			return win; 

		} else {
			Predicate tier = cb.equal(root.get("tier"), "2");
			Predicate class_ = cb.equal(root.get("Class"), "tuner");
			condizioni = cb.and(tier, class_);
			criteria.select(root).where(condizioni);
			List<Carboncar> listaMacchine = em.createQuery(criteria).getResultList();
			List<Carboncar> macchineRace = new ArrayList<>();
			for (int i = 0; i < 4; i++) {
				Random random = new Random();
				int randomindex = random.nextInt(listaMacchine.size());
				Carboncar car = listaMacchine.get(randomindex);
				macchineRace.add(car);
				this.racers[i] = racerName.get(i).toString();

			}
			win = _calcRaceWinRate(fromWin, macchineRace, carMap);
			macchineRace.add(selectedCar);
			this.racers[racers.length - 1] = "You";
			return win;
		}

	}

	private boolean _calcRaceWinRate(boolean fromWin, List<Carboncar> macchineRace, Map<Double, String> carMap) {
		double[] moltiplicatori;
		int schianto;
		List<Double> punteggi = new ArrayList<>();
		Random random = new Random();
		_randomRace();
		moltiplicatori = _calcolaMoltiplicatori(this.race);
		int index = 0;
		double massimo = Double.MIN_VALUE;
		carMap.clear();
		for (Carboncar macchina : macchineRace) {
			if(index != 4) {
				double enemyScore = (macchina.getTopSpeed() * moltiplicatori[0]
						+ macchina.getAcceleration() * moltiplicatori[1] + macchina.getHandling() * moltiplicatori[2]);
				schianto = random.nextInt(101);
				schianto++;
				enemyScore = enemyScore + schianto;
				punteggi.add(enemyScore);
				carMap.put(enemyScore, this.racers[index]);
				index++;
			}
			
		}
		double myScore = (selectedCar.getTopSpeed() * moltiplicatori[0]
				+ selectedCar.getAcceleration() * moltiplicatori[1] + selectedCar.getHandling() * moltiplicatori[2]);
		schianto = random.nextInt(121);
		schianto++; 
		myScore = myScore + schianto; 
		punteggi.add(myScore);  
		carMap.put(myScore, this.racers[racers.length-1]);
		Collections.sort(punteggi);
		Map<Double, String> clone = new HashMap<>();
		clone.putAll(carMap);
		carMap.clear();  
		for (double valore : punteggi) {
			carMap.put(valore, clone.get(valore));
		}
		for(Double punteggio : carMap.keySet() ) {
			if(punteggio > massimo) massimo  = punteggio;
		}
		if(massimo == myScore) {
			return true;
		}else {
			return false;
		}
		
		// intendo fare if myscore > enemyscore cont++, se mio score li batte tutti then
		// win
	}

	private double _duel(Carboncar mycar, Carboncar bosscar) {

		double probVittoria;
		int differenza;
//		prima controllo se le macchina sono identiche
		if (mycar == bosscar) {
//			sono identiche
			Random random = new Random();
			int win = random.nextInt(2);
			_randomRace();

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
		_randomRace();
		System.out.println("Tipo di tracciato: " + this.race);
		if (diverse) {
			// qui sono diverse
			if (vantaggio) {
				moltiplicatori = _calcolaMoltiplicatori(this.race);
				double myScore = (mycar.getTopSpeed() * moltiplicatori[0] + mycar.getAcceleration() * moltiplicatori[1]
						+ mycar.getHandling() * moltiplicatori[2]) + differenza * 2;
				double enemyScore = (enemyCar.getTopSpeed() * moltiplicatori[0]
						+ enemyCar.getAcceleration() * moltiplicatori[1] + enemyCar.getHandling() * moltiplicatori[2]);
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
				double enemyScore = (enemyCar.getTopSpeed() * moltiplicatori[0]
						+ enemyCar.getAcceleration() * moltiplicatori[1] + enemyCar.getHandling() * moltiplicatori[2])
						+ differenza * 2;
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

	private void _randomRace() {
		Random random = new Random();
		int gara = random.nextInt(3);
		this.race = null;
		if (gara == 0)
			this.race = "drift";
		if (gara == 1)
			this.race = "sprint";
		if (gara == 2)
			this.race = "circuit";
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

	public String[] getRaces() {
		return races;
	}

	public void setRaces(String[] races) {
		this.races = races;
	}

	public String getBossCss() {
		return bossCss;
	}

	public void setBossCss(String bossCss) {
		this.bossCss = bossCss;
	}

	public String getRaceState() {
		return raceState;
	}

	public void setRaceState(String raceState) {
		this.raceState = raceState;
	}

	public int getCountWin() {
		return countWin;
	}

	public void setCountWin(int countWin) {
		this.countWin = countWin;
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

	public boolean isFromWin() {
		return fromWin;
	}

	public void setFromWin(boolean fromWin) {
		this.fromWin = fromWin;
	}

	public String[] getRacers() {
		return racers;
	}

	public void setRacers(String[] racers) {
		this.racers = racers;
	}

	public Map<Double, String> getCarMap() {
		return carMap;
	}

	public void setCarMap(Map<Double, String> carMap) {
		this.carMap = carMap;
	}

	public Gara[] getRacess() {
		return racess;
	}

	public void setRacess(Gara[] racess) {
		this.racess = racess;
	}

}
