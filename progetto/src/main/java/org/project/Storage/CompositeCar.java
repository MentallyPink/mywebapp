package org.project.Storage;

import java.io.Serializable;

import org.project.Entities.Carboncar;
import org.project.Utilities.StringUtilities;

public class CompositeCar implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1081742795226370290L;
	
	
	private Carboncar entity;
	
	
	private String nome;
	private String classe;
	private int tier;
	private double handling;
	private double topSpeed;
	private double acceleration;
	private int price;
	
	public CompositeCar() {
		this.entity = new Carboncar();
	}
	
	public CompositeCar(Carboncar entity) {
		this.entity = entity;
		refreshComposite();
	}
	
	public void refreshEntities() {
		this.entity.setNome(StringUtilities.isEmpty(nome) ? ""  : nome);
		this.entity.setClass_(StringUtilities.isEmpty(classe) ? ""  : classe);
		this.entity.setTier(tier);
		this.entity.setHandling(handling);
		this.entity.setAcceleration(acceleration);
		this.entity.setTopSpeed(topSpeed);
		this.entity.setPrice(price);
	}
	
	public void refreshComposite() {
		this.nome = entity.getNome();
		this.classe = entity.getClass_();
		this.tier = entity.getTier();
		this.handling = entity.getHandling();
		this.topSpeed = entity.getTopSpeed();
		this.acceleration = entity.getAcceleration();
		this.price = entity.getPrice();
	}
	
	public Carboncar getEntity() {
		return entity;
	}
	public void setEntity(Carboncar entity) {
		this.entity = entity;
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
	public int getTier() {
		return tier;
	}
	public void setTier(int tier) {
		this.tier = tier;
	}
	public double getHandling() {
		return handling;
	}
	public void setHandling(double handling) {
		this.handling = handling;
	}
	public double getTopSpeed() {
		return topSpeed;
	}
	public void setTopSpeed(double topSpeed) {
		this.topSpeed = topSpeed;
	}
	public double getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}
