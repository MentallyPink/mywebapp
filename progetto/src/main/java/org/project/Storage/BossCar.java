package org.project.Storage;

import java.io.Serializable;

import org.project.Entities.Carbonboss;
import org.project.Entities.Carboncar;

public class BossCar implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2846743753186206227L;

	private Carboncar macchina;
	
	private Carbonboss boss;

	public Carboncar getMacchina() {
		return macchina;
	}

	public void setMacchina(Carboncar macchina) {
		this.macchina = macchina;
	}

	public Carbonboss getBoss() {
		return boss;
	}

	public void setBoss(Carbonboss boss) {
		this.boss = boss;
	}
	
	@Override
	public String toString() {
		return "nome: " + boss.getNome() + " \n gang: " + boss.getGang() + "\n macchina: " + macchina.getNome();
	}
}
