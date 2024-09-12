package org.project.Storage;

import java.io.Serializable;

import org.project.Entities.Carbonboss;
import org.project.Utilities.StringUtilities;

public class CompositeBoss implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2846743753186206227L;
	
	private Carbonboss entity;

	private CompositeCar car;
	private String nomeMacchina;
	private String nomeBoss;
	private String gang;
	
	public CompositeBoss() {
		
	}
	public CompositeBoss(Carbonboss entity) {
		this.entity = entity;
	}
	
	public CompositeBoss(Carbonboss entity, CompositeCar car) {
		this.entity = entity;
		this.car = car;
	}
	public void refreshComposite() {
		this.nomeBoss = entity.getNome();
		this.gang = entity.getGang();
		this.nomeMacchina = entity.getMacchina();
	}
	
	public void refreshEntities() {
		entity.setGang(StringUtilities.isEmpty(gang) ? "" : gang);
		entity.setMacchina(StringUtilities.isEmpty(nomeMacchina)? "" : nomeMacchina);
		entity.setNome(StringUtilities.isEmpty(nomeBoss)? "" : nomeBoss);
	}


	public Carbonboss getBoss() {
		return entity;
	}

	public void setBoss(Carbonboss boss) {
		this.entity = boss;
	}

	public CompositeCar getCar() {
		return car;
	}

	public void setCar(CompositeCar car) {
		this.car = car;
	}

	public String getNomeBoss() {
		return nomeBoss;
	}

	public void setNomeBoss(String nomeBoss) {
		this.nomeBoss = nomeBoss;
	}

	public String getGang() {
		return gang;
	}

	public void setGang(String gang) {
		this.gang = gang;
	}

	public String getNomeMacchina() {
		return nomeMacchina;
	}

	public void setNomeMacchina(String nomeMacchina) {
		this.nomeMacchina = nomeMacchina;
	}
}
